package mc.duzo.timeless.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import mc.duzo.timeless.suit.item.SuitItem;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method="equipStack", at=@At("HEAD"), cancellable = true)
    private void timeless$equipStack(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
        if (!(stack.getItem() instanceof SuitItem suit)) return;
        if (!suit.isBinding()) return;

        ci.cancel();
    }
}
