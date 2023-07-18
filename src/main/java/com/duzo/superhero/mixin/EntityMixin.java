package com.duzo.superhero.mixin;

import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.ironman.IronManUtil;
import com.duzo.superhero.util.player.IEntityDataSaver;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityDataSaver {
    private CompoundTag persistentData;

    @Shadow public abstract void playSound(SoundEvent p_19938_, float p_19939_, float p_19940_);

    @Shadow public abstract HitResult pick(double p_19908_, float p_19909_, boolean p_19910_);

    @Shadow public abstract void playSound(SoundEvent p_216991_);

    @Shadow public abstract void playerTouch(Player p_20081_);

    // Saving custom data to entities
    // @TODO i dont think this is necessary
    @Override
    public CompoundTag getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new CompoundTag();
        }
        return persistentData;
    }

    @Inject(method="save",at = @At("HEAD"))
    protected void SUPERHERO_injectSaveMethod(CompoundTag tag, CallbackInfoReturnable<Boolean> cir) {
        if (this.persistentData != null) {
            tag.put("superhero.custom_data",persistentData);
        }
    }

    @Inject(method="load", at = @At("HEAD"))
    protected void SUPERHERO_injectReadMethod(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("superhero.custom_data", Tag.TAG_COMPOUND)) {
            persistentData = tag.getCompound("superhero.custom_data");
        }
    }

    @Inject(at = @At("HEAD"),method = "Lnet/minecraft/world/entity/Entity;playStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", cancellable = true)
    private void playCustomStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!state.getMaterial().isLiquid()) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof Player player)) return;

            if (IronManUtil.isIronManSuit(player.getItemBySlot(EquipmentSlot.FEET))) {
                this.playSound(SuperheroSounds.IRONMAN_STEP.get(), 1f, 1f);
            }
        }
    }
}