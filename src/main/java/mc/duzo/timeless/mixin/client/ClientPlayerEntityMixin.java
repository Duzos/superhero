package mc.duzo.timeless.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.client.animation.player.PlayerAnimationTracker;
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void timeless$tickMovement(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) return;

        SuitAnimationHolder animSuit = SuitAnimationTracker.getAnimation(player);
        PlayerAnimationHolder animPlayer = PlayerAnimationTracker.getAnimation(player);

        AnimationInfo.Movement movement = null;

        if (animSuit != null) movement = animSuit.getInfo().movement();
        else if (animPlayer != null) movement = animPlayer.getInfo().movement();

        if (movement == null || movement == AnimationInfo.Movement.ALLOW) return;

        ci.cancel();
    }

}
