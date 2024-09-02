package mc.duzo.timeless.suit.client.animation.impl.ironman.mk5;

import mc.duzo.animation.generic.AnimationInfo;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;

public class MarkFiveMaskAnimation extends SuitAnimationHolder {
    public MarkFiveMaskAnimation(boolean isPuttingOn) {
        super(new Identifier(Timeless.MOD_ID, "ironman_mk5_mask_" + (isPuttingOn ? "open" : "close")), (isPuttingOn) ? MarkFiveAnimations.MASK_CLOSE : MarkFiveAnimations.MASK_OPEN, new AnimationInfo(AnimationInfo.RenderType.TORSO_HEAD, null, AnimationInfo.Movement.ALLOW, AnimationInfo.Transform.TARGETED), false);
    }

    @Override
    protected void onFinished(AbstractClientPlayerEntity player) {
        super.onFinished(player);

        if (this.getModel() == null) return;

    }
}
