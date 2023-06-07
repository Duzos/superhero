package com.duzo.superhero.mixin;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract void playSound(SoundEvent p_19938_, float p_19939_, float p_19940_);

    @Inject(at = @At("HEAD"),method = "Lnet/minecraft/world/entity/Entity;playStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", cancellable = true)
    private void playCustomStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (!state.getMaterial().isLiquid()) {
            Entity entity = (Entity) (Object) this;
            if (!(entity instanceof Player player)) return;

            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
                this.playSound(SuperheroSounds.IRONMAN_STEP.get(), 1f, 1f);
            }
        }
    }
}