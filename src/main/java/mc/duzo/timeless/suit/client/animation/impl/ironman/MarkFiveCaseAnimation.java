package mc.duzo.timeless.suit.client.animation.impl.ironman;

import net.minecraft.client.network.AbstractClientPlayerEntity;

import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;

public class MarkFiveCaseAnimation extends SuitAnimationHolder {
    public MarkFiveCaseAnimation(boolean isPuttingOn) {
        super((isPuttingOn) ? IronManAnimations.MK5_CASE_OPEN : IronManAnimations.MK5_CASE_CLOSE, PlayerAnimationHolder.RenderType.FIRST_LAYER, false, true);
    }

    @Override
    protected void onStart(AbstractClientPlayerEntity player) {
        super.onStart(player);

        if (this.getModel() == null) return;
        this.getModel().getChild("case").orElseThrow().visible = true;
    }

    @Override
    protected void onFinished(AbstractClientPlayerEntity player) {
        super.onFinished(player);

        if (this.getModel() == null) return;
        this.getModel().getChild("case").orElseThrow().visible = false;
    }
}
