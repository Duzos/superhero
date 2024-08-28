package mc.duzo.timeless.suit.client.animation;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;

import mc.duzo.timeless.suit.client.render.SuitModel;

/**
 * From {@link net.minecraft.client.render.entity.animation.AnimationHelper},
 * Edited for animating SuitModel
 *
 * @author duzo
 */
@Environment(value= EnvType.CLIENT)
public class SuitAnimationHelper {
    public static void animate(SuitModel model, Animation animation, long runningTime, float scale, Vector3f tempVec) {
        float f = getRunningSeconds(animation, runningTime);
        animation.boneAnimations().forEach((key, list) -> {
            Optional<ModelPart> optional = getChild(model, key);;
            optional.ifPresent(ModelPart::resetTransform);
            optional.ifPresent(part -> list.forEach(transformation -> {
                Keyframe[] keyframes = transformation.keyframes();
                int i = Math.max(0, MathHelper.binarySearch(0, keyframes.length, index -> f <= keyframes[index].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = f - keyframe.timestamp();
                float k = j != i ? MathHelper.clamp(h / (keyframe2.timestamp() - keyframe.timestamp()), 0.0f, 1.0f) : 0.0f;
                keyframe2.interpolation().apply(tempVec, k, keyframes, i, j, scale);
                transformation.target().apply(part, tempVec);
            }));
        });
    }

    public static void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, float speedMultiplier, SuitModel model) {
        animationState.update(animationProgress, speedMultiplier);
        animationState.run(state -> animate(model, animation, state.getTimeRunning(), 1.0f, new Vector3f()));
    }
    public static void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, SuitModel model) {
        updateAnimation(animationState, animation, animationProgress, 1.0f, model);
    }

    private static float getRunningSeconds(Animation animation, long runningTime) {
        float f = (float)runningTime / 1000.0f;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }

    private static Optional<ModelPart> getChild(SuitModel model, String name) {
        return model.getChild(name);
    }

    public static Vector3f getModelPartPivot(SuitModel model, String name) {
        Optional<ModelPart> optional = getChild(model, name);
        if(optional.isEmpty()) return new Vector3f(0.0F, 0.0F, 0.0F);
        Vector3f pivot = new Vector3f(optional.get().pivotX, optional.get().pivotY, optional.get().pivotZ);
        return pivot;
    }

    public static Vector3f getModelPartRotation(SuitModel model, String name) {
        Optional<ModelPart> optional = getChild(model, name);
        if(optional.isEmpty()) return new Vector3f(0.0F, 0.0F, 0.0F);
        Vector3f rotation = new Vector3f(optional.get().pivotX, optional.get().pivotY, optional.get().pivotZ);
        return rotation;
    }
}