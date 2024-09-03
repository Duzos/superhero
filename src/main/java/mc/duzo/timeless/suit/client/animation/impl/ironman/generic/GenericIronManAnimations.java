package mc.duzo.timeless.suit.client.animation.impl.ironman.generic;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class GenericIronManAnimations {

    public static final Animation MASK_CLOSE = Animation.Builder.create(1.48f)
            .addBoneAnimation("helmet",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.96f, AnimationHelper.createTranslationalVector(0f, -0.1f, -1.7f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("helmet",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.56f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MASK_OPEN = Animation.Builder.create(1.48f)
            .addBoneAnimation("helmet",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.04f, AnimationHelper.createTranslationalVector(0f, -0.1f, -1.7f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.8f, AnimationHelper.createTranslationalVector(0f, 0f, -1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("helmet",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-82.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.44f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
}
