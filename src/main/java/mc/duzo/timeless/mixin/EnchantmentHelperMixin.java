package mc.duzo.timeless.mixin;

import mc.duzo.timeless.suit.SuitItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
	@Inject(method="hasBindingCurse", at=@At("HEAD"), cancellable = true)
	private void timeless$hasBindingCurse(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (!(stack.getItem() instanceof SuitItem suit)) return;

		cir.setReturnValue(suit.isBinding());
	}
}
