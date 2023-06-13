package com.duzo.superhero.mixin;

import com.duzo.superhero.items.ironman.IronManArmourItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class ClientPlayerMixin {

    @Inject(at = @At("HEAD"),method = "Lnet/minecraft/client/player/AbstractClientPlayer;getSkinTextureLocation()Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private void setSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        // does it gloabablyb and not jus tof rhte t hinad
//        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
//        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem chest) {
//            cir.setReturnValue(chest.getTexture());
//        }
    }
}