package mc.duzo.timeless.suit.client.render;

import java.util.Optional;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;

public abstract class SuitModel extends EntityModel<LivingEntity> {
    /**
     * This will be called to render the model, perform all adjustments here and render the model using the proper method.
     */
    public abstract void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha);

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(LivingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (!(entity instanceof AbstractClientPlayerEntity player)) return;
        this.runAnimations(player, animationProgress);
    }

    protected void runAnimations(AbstractClientPlayerEntity player, float animationProgress) {
        SuitAnimationHolder anim = this.getAnimation(player).orElse(null);
        if (anim == null) return;

        this.runAnimations(player, animationProgress, anim);
    }
    protected void runAnimations(AbstractClientPlayerEntity player, float animationProgress, SuitAnimationHolder anim) {
        if (anim.getInfo().transform() == AnimationInfo.Transform.ALL)
            this.resetTransforms();
        anim.update(this, animationProgress, player);
    }

    public Optional<SuitAnimationHolder> getAnimation(AbstractClientPlayerEntity player) {
        return Optional.ofNullable(SuitAnimationTracker.getAnimation(player));
    }
    public boolean isAnimating(AbstractClientPlayerEntity player) {
        return this.getAnimation(player).isPresent();
    }

    public Identifier texture() {
        return this.getSuit().texture();
    }
    public Optional<Identifier> emission() {
        return this.getSuit().emission();
    }
    public abstract ClientSuit getSuit();

    /**
     * @return The root model part
     */
    public abstract ModelPart getPart();

    /**
     * @return the name of the root model part
     */
    protected String getPartName() {
        return "root";
    }
    public Optional<ModelPart> getChild(String name) {
        if (name.equals(this.getPartName())) {
            return Optional.of(this.getPart());
        }
        return this.getPart().traverse().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
    }
    protected void resetTransforms() {
        this.getPart().resetTransform();
        this.getPart().traverse().forEach(ModelPart::resetTransform);
    }
    public void copyFrom(BipedEntityModel<?> model) {

    }
    public void copyTo(BipedEntityModel<?> model) {

    }
}
