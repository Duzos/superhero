package com.duzo.superhero.client.renderers.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class IronManAnimations {
    public static final AnimationDefinition SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN = AnimationDefinition.Builder.withLength(1.48f).addAnimation("helmet", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.2f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.96f, KeyframeAnimations.posVec(0f, -0.1f, -1.7f), AnimationChannel.Interpolations.LINEAR))).addAnimation("helmet", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.56f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1f, KeyframeAnimations.degreeVec(-82.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE = AnimationDefinition.Builder.withLength(1.48f).addAnimation("helmet", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.04f, KeyframeAnimations.posVec(0f, -0.1f, -1.7f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.8f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).addAnimation("helmet", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(-82.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(0.44f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR), new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR))).build();
}
