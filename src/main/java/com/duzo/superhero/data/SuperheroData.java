package com.duzo.superhero.data;

import com.duzo.superhero.client.renderers.animations.IronManAnimations;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.sync.SyncSuperheroData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SuperheroData {

    @NotNull
    private final Player player;

    //Iron-Man Animations
    public AnimationState maskOpenAnimation = new AnimationState();
    public AnimationState maskCloseAnimation = new AnimationState();

    private boolean isOpen = false;

    private float timer = 0.0F;

    public SuperheroData(@NotNull Player player) {
        this.player = player;
    }

    public void tick(LivingEntity livingEntity) {
        openAndCloseLogic(livingEntity);
        //SuperheroUtil.onTickPlayerGlide(livingEntity.level, livingEntity);

        if (livingEntity.level.isClientSide) return;

        if (livingEntity.tickCount % 40 == 0) {
            sync();
        }
        if(livingEntity instanceof Player player)
            if(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem)
                setIsOpen(player.isHurt());
    }

    //public static Optional<SuperheroData> get(LivingEntity player) {
    //    throw new AssertionError();
    //}

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isopen) {
        isOpen = isopen;
    }

    private void openAndCloseLogic(LivingEntity livingEntity) {
        if (isOpen()) {
            if (!maskOpenAnimation.isStarted()) {
                if(timer < IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN.lengthInSeconds()) {
                    maskOpenAnimation.start(livingEntity.tickCount);
                    timer = timer + 0.1F;
                } else if (timer >= IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN.lengthInSeconds()){
                    timer = IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN.lengthInSeconds();
                    maskOpenAnimation.stop();
                }
            }
            if(!maskOpenAnimation.isStarted() && timer >= IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds()) {
                timer = 0.0F;
            }
        } else {
            if (!maskCloseAnimation.isStarted()) {
                if(timer < IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds()) {
                    maskCloseAnimation.start(livingEntity.tickCount);
                    timer = timer + 0.1F;
                } else if (timer >= IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds()){
                    timer = IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds();
                    maskCloseAnimation.stop();
                }
            }
        }
    }

    public void sync() {
        if (this.player.level.isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        Network.sendToTracking(new SyncSuperheroData(this.player.getId(), serializeNBT()),(ServerPlayer) this.player);
    }

    public void syncTo(ServerPlayer receiver) {
        if (this.player.level.isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        Network.sendToTracking(new SyncSuperheroData(this.player.getId(), serializeNBT()),(ServerPlayer) receiver);
    }

    public AnimationState getAnimation(AnimationStates animationStates) {
        return switch (animationStates) {
            case MASK_OPEN -> maskOpenAnimation;
            case MASK_CLOSE -> maskCloseAnimation;
        };
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("is_open", isOpen);
        compoundTag.putFloat("timer", timer);
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        setIsOpen(nbt.getBoolean("is_open"));
        timer = nbt.getFloat("timer");
    }

    public enum AnimationStates {
        MASK_OPEN, MASK_CLOSE
    }

}