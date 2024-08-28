package mc.duzo.timeless.client.animation.player.holder;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

import mc.duzo.timeless.client.animation.AnimationHolder;
import mc.duzo.timeless.client.animation.player.PlayerAnimationHelper;

public class PlayerAnimationHolder extends AnimationHolder {

    public PlayerAnimationHolder(Animation anim) {
        super(anim);
    }

    @Override
    public void update(EntityModel<?> model, float progress, AbstractClientPlayerEntity player) {
        PlayerAnimationHelper.updateAnimation(this.state, this.animation, progress, 1.0f, (PlayerEntityModel<?>) model);

        super.update(model, progress, player);
    }

    public enum RenderType {
        ALL {
            @Override
            public void apply(PlayerEntityModel<AbstractClientPlayerEntity> model) {
                model.setVisible(true);
            }
        }, // render player
        FIRST_LAYER {
            @Override
            public void apply(PlayerEntityModel<AbstractClientPlayerEntity> model) {
                model.setVisible(true);

                model.jacket.visible = false;
                model.leftPants.visible = false;
                model.rightPants.visible = false;
                model.leftSleeve.visible = false;
                model.rightSleeve.visible = false;
                model.hat.visible = false;
            }
        }, // render only first layer
        EXCLUDE_LEGS {
            @Override
            public void apply(PlayerEntityModel<AbstractClientPlayerEntity> model) {
                FIRST_LAYER.apply(model);

                model.leftLeg.visible = false;
                model.rightLeg.visible = false;
            }
        }, // hide legs AND first layer
        NONE {
            @Override
            public void apply(PlayerEntityModel<AbstractClientPlayerEntity> model) {
                model.setVisible(false);
            }
        }; // render nothing

        public abstract void apply(PlayerEntityModel<AbstractClientPlayerEntity> model);
    }
}
