package mc.duzo.timeless.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import mc.duzo.timeless.suit.item.SuitItem;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method="hasBindingCurse", at=@At("HEAD"), cancellable = true)
    private static void timeless$hasBindingCurse(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!(stack.getItem() instanceof SuitItem suit)) return;

        cir.setReturnValue(suit.isBinding());
    }
}
