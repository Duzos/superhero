package mc.duzo.timeless.suit.client.animation;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

import mc.duzo.timeless.client.animation.AnimationHolder;
import mc.duzo.timeless.suit.client.render.SuitModel;

public class SuitAnimationHolder extends AnimationHolder {
    private final boolean renderPlayer;
    private final boolean playerMimics;
    private SuitModel model;

    public SuitAnimationHolder(Animation anim, boolean renderPlayer, boolean playerMimics, boolean thirdPerson) {
        super(anim, thirdPerson);
        this.renderPlayer = renderPlayer;
        this.playerMimics = playerMimics;
    }

    @Override
    public void update(EntityModel<?> model, float progress, AbstractClientPlayerEntity player) {
        SuitAnimationHelper.updateAnimation(this.state, this.animation, progress, 1.0f, (SuitModel) model);
        this.model = (SuitModel) model;

        super.update(model, progress, player);
    }

    public boolean shouldRenderPlayer() {
        return this.renderPlayer;
    }
    public boolean shouldPlayerMimic() {
        return this.playerMimics;
    }
    public SuitModel getModel() {
        return this.model;
    }
}
