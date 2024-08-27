package mc.duzo.timeless.mixin.client;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;

import mc.duzo.timeless.client.animation.player.PlayerAnimationTracker;
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;

@Mixin(Perspective.class)
public class PerspectiveMixin {
    @Inject(method = "isFirstPerson", at = @At("HEAD"), cancellable = true)
    private void ouroborus$isFirstPerson(CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) return;

        SuitAnimationHolder animSuit = SuitAnimationTracker.getAnimation(player);
        PlayerAnimationHolder animPlayer = PlayerAnimationTracker.getAnimation(player);

        boolean thirdPerson = (animSuit != null && animSuit.isThirdPerson()) || (animPlayer != null && animPlayer.isThirdPerson());

        if (thirdPerson) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "isFrontView", at = @At("HEAD"), cancellable = true)
    private void ouroborus$isFirstView(CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) return;

        SuitAnimationHolder animSuit = SuitAnimationTracker.getAnimation(player);
        PlayerAnimationHolder animPlayer = PlayerAnimationTracker.getAnimation(player);

        boolean thirdPerson = (animSuit != null && animSuit.isThirdPerson()) || (animPlayer != null && animPlayer.isThirdPerson());

        if (thirdPerson) {
            cir.setReturnValue(true);
        }
    }
}