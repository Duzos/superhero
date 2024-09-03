package mc.duzo.timeless.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.ClientPlayerEntity;

import mc.duzo.timeless.power.impl.FlightPower;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "isInSneakingPose", at = @At("HEAD"), cancellable = true)
    private void timeless$isInSneakingPose(CallbackInfoReturnable<Boolean> cir) {
        if (FlightPower.isFlying((ClientPlayerEntity)(Object)this)) cir.setReturnValue(false);
    }
}
