package mc.duzo.timeless.suit.client.animation.impl.ironman.mk5;

import net.minecraft.client.network.AbstractClientPlayerEntity;

import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;

public class MarkFiveMaskAnimation extends SuitAnimationHolder {
    public MarkFiveMaskAnimation(boolean isPuttingOn) {
        super((isPuttingOn) ? MarkFiveAnimations.MASK_CLOSE : MarkFiveAnimations.MASK_OPEN, new AnimationInfo(AnimationInfo.RenderType.EXCLUDE_LEGS, null, AnimationInfo.Movement.ALLOW, AnimationInfo.Transform.TARGETED), false);
    }

    @Override
    protected void onFinished(AbstractClientPlayerEntity player) {
        super.onFinished(player);

        if (this.getModel() == null) return;

    }
}
