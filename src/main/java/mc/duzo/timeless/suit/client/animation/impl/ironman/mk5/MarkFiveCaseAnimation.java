package mc.duzo.timeless.suit.client.animation.impl.ironman.mk5;

import net.minecraft.client.network.AbstractClientPlayerEntity;

import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;

public class MarkFiveCaseAnimation extends SuitAnimationHolder {
    public MarkFiveCaseAnimation(boolean isPuttingOn) {
        super((isPuttingOn) ? MarkFiveAnimations.CASE_OPEN : MarkFiveAnimations.CASE_CLOSE, new AnimationInfo(AnimationInfo.RenderType.FIRST_LAYER, AnimationInfo.Perspective.THIRD_PERSON_FRONT, AnimationInfo.Movement.DISABLE, AnimationInfo.Transform.ALL), false);
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
