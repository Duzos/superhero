package mc.duzo.timeless.client.animation;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public record AnimationInfo(RenderType render, Perspective perspective, Movement movement, Transform transform) {

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

    public enum Movement {
        ALLOW,
        DISABLE;
    }

    public enum Perspective {
        FIRST_PERSON(true, false),
        THIRD_PERSON_BACK(false, false),
        THIRD_PERSON_FRONT(false, true);
        private final boolean firstPerson;
        private final boolean frontView;

        Perspective(boolean firstPerson, boolean frontView) {
            this.firstPerson = firstPerson;
            this.frontView = frontView;
        }

        public boolean isFirstPerson() {
            return this.firstPerson;
        }

        public boolean isFrontView() {
            return this.frontView;
        }
    }

    public enum Transform {
        ALL,
        TARGETED;
    }
}
