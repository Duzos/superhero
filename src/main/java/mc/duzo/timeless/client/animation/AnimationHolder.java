package mc.duzo.timeless.client.animation;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.AnimationState;

import mc.duzo.timeless.client.animation.player.PlayerAnimationHelper;

public abstract class AnimationHolder {
    protected final AnimationState state;
    protected final Animation animation;

    protected AnimationHolder(Animation anim) {
        this.state = new AnimationState();
        this.animation = anim;
    }

    public void update(EntityModel<?> model, float progress, AbstractClientPlayerEntity player) {
        // overwritten update method goes here

        if (this.isFinished(player)) {
            this.state.stop();
            this.onFinished(player);
            return;
        }

        if (!this.state.isRunning()) {
            this.onStart(player);
        }

        this.state.startIfNotRunning(player.age);
    }

    public boolean isFinished(AbstractClientPlayerEntity entity) {
        if (this.animation.looping()) return false; // Looping animations should extend this class so they properly finish

        return this.getRunningSeconds() >= this.animation.lengthInSeconds();
    }

    protected void onFinished(AbstractClientPlayerEntity player) {

    }
    protected void onStart(AbstractClientPlayerEntity player) {

    }

    protected float getRunningSeconds() {
        return PlayerAnimationHelper.getRunningSeconds(this.animation, this.state.getTimeRunning());
    }
    public Animation getAnimation() {
        return animation;
    }
}
