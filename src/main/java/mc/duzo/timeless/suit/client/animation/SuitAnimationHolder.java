package mc.duzo.timeless.suit.client.animation;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

import mc.duzo.timeless.client.animation.AnimationHolder;
import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.suit.client.render.SuitModel;

public class SuitAnimationHolder extends AnimationHolder {
    private final boolean playerMimics;
    private SuitModel model;

    public SuitAnimationHolder(Animation anim, AnimationInfo info, boolean playerMimics) {
        super(anim, info);
        this.playerMimics = playerMimics;
    }

    @Override
    public void update(EntityModel<?> model, float progress, AbstractClientPlayerEntity player) {
        SuitAnimationHelper.updateAnimation(this.state, this.animation, progress, 1.0f, (SuitModel) model);
        this.model = (SuitModel) model;

        super.update(model, progress, player);
    }

    public boolean shouldPlayerMimic() {
        return this.playerMimics;
    }
    public SuitModel getModel() {
        return this.model;
    }
}
