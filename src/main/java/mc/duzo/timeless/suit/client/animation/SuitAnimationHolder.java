package mc.duzo.timeless.suit.client.animation;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

import mc.duzo.timeless.client.animation.AnimationHolder;
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.render.SuitModel;

public class SuitAnimationHolder extends AnimationHolder {
    private final PlayerAnimationHolder.RenderType playerRender;
    private final boolean playerMimics;
    private SuitModel model;

    public SuitAnimationHolder(Animation anim, PlayerAnimationHolder.RenderType playerRender, boolean playerMimics, boolean thirdPerson) {
        super(anim, thirdPerson);
        this.playerRender = playerRender;
        this.playerMimics = playerMimics;
    }

    @Override
    public void update(EntityModel<?> model, float progress, AbstractClientPlayerEntity player) {
        SuitAnimationHelper.updateAnimation(this.state, this.animation, progress, 1.0f, (SuitModel) model);
        this.model = (SuitModel) model;

        super.update(model, progress, player);
    }

    public PlayerAnimationHolder.RenderType getPlayerRenderType() {
        return this.playerRender;
    }
    public boolean shouldPlayerMimic() {
        return this.playerMimics;
    }
    public SuitModel getModel() {
        return this.model;
    }
}
