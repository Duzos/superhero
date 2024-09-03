package mc.duzo.timeless.suit.ironman.client;

import mc.duzo.animation.generic.AnimationInfo;

import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import mc.duzo.timeless.power.impl.FlightPower;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.render.SuitModel;

public abstract class GenericIronManModel extends SuitModel {
    private final ModelPart root;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart body;
    private final ModelPart head;
    protected GenericIronManModel(ModelPart root) {
        this.root = root;

        this.leftLeg = this.getChild("LeftLeg").orElseThrow();
        this.rightLeg = this.getChild("RightLeg").orElseThrow();
        this.rightArm = this.getChild("RightArm").orElseThrow();
        this.leftArm = this.getChild("LeftArm").orElseThrow();
        this.body = this.getChild("Body").orElseThrow();
        this.head = this.getChild("Head").orElseThrow();
    }
    protected GenericIronManModel() {
        this(getTexturedModelData().createModel());
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData RightLeg = bone.addChild("RightLeg", ModelPartBuilder.create().uv(58, 0).cuboid(-2.0F, -0.25F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
                .uv(50, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(-1.9F, -12.0F, 0.0F));

        ModelPartData RightFoot = RightLeg.addChild("RightFoot", ModelPartBuilder.create().uv(75, 0).cuboid(-3.9F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.25F))
                .uv(54, 34).cuboid(-3.9F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(1.9F, 11.75F, 0.0F));

        ModelPartData foot_right_beam = RightFoot.addChild("foot_right_beam", ModelPartBuilder.create().uv(13, 47).cuboid(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(-1.9F, 0.25F, 0.0F));

        ModelPartData LeftLeg = bone.addChild("LeftLeg", ModelPartBuilder.create().uv(58, 0).mirrored().cuboid(-2.0F, -0.25F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(50, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(1.9F, -12.0F, 0.0F));

        ModelPartData LeftFoot = LeftLeg.addChild("LeftFoot", ModelPartBuilder.create().uv(75, 0).mirrored().cuboid(-0.1F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.25F)).mirrored(false)
                .uv(54, 34).mirrored().cuboid(-0.1F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(-1.9F, 11.75F, 0.0F));

        ModelPartData foot_left_beam = LeftFoot.addChild("foot_left_beam", ModelPartBuilder.create().uv(13, 47).mirrored().cuboid(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(1.9F, 0.25F, 0.0F));

        ModelPartData RightArm = bone.addChild("RightArm", ModelPartBuilder.create().uv(47, 47).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(30, 47).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)).mirrored(false)
                .uv(33, 17).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.75F)).mirrored(false), ModelTransform.pivot(-5.0F, -22.0F, 0.0F));

        ModelPartData LeftArmLayer_r1 = RightArm.addChild("LeftArmLayer_r1", ModelPartBuilder.create().uv(75, 20).mirrored().cuboid(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.of(-0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData right_beam = RightArm.addChild("right_beam", ModelPartBuilder.create().uv(13, 47).cuboid(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(-1.0F, 10.25F, 0.0F));

        ModelPartData rocket = RightArm.addChild("rocket", ModelPartBuilder.create().uv(25, 65).mirrored().cuboid(-1.5F, 0.0F, -4.025F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-0.85F, -2.725F, 2.25F));

        ModelPartData LeftArm = bone.addChild("LeftArm", ModelPartBuilder.create().uv(47, 47).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(30, 47).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F))
                .uv(33, 17).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.75F)), ModelTransform.pivot(5.0F, -22.0F, 0.0F));

        ModelPartData LeftArmLayer_r2 = LeftArm.addChild("LeftArmLayer_r2", ModelPartBuilder.create().uv(75, 20).cuboid(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.25F)), ModelTransform.of(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData left_beam = LeftArm.addChild("left_beam", ModelPartBuilder.create().uv(13, 47).cuboid(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(1.0F, 10.25F, 0.0F));

        ModelPartData rocket2 = LeftArm.addChild("rocket2", ModelPartBuilder.create().uv(25, 65).cuboid(-1.5F, 0.0F, -4.025F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.85F, -2.725F, 2.25F));

        ModelPartData Body = bone.addChild("Body", ModelPartBuilder.create().uv(33, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(29, 30).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.275F))
                .uv(60, 60).cuboid(-3.5F, 0.5F, -2.0F, 7.0F, 5.0F, 4.0F, new Dilation(0.975F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

        ModelPartData BodyLayer_r1 = Body.addChild("BodyLayer_r1", ModelPartBuilder.create().uv(40, 65).cuboid(-6.0F, 0.0F, -2.0F, 7.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 0.25F, 3.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData BodyLayer_r2 = Body.addChild("BodyLayer_r2", ModelPartBuilder.create().uv(60, 70).cuboid(-4.5F, 0.0F, 0.0F, 9.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.25F, -3.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData powerthing = Body.addChild("powerthing", ModelPartBuilder.create().uv(18, 34).cuboid(-2.0F, -2.0F, 0.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.275F)), ModelTransform.pivot(0.0F, 2.8F, -2.35F));

        ModelPartData Head = bone.addChild("Head", ModelPartBuilder.create().uv(0, 17).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(36, 79).cuboid(-4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

        ModelPartData bone2 = Head.addChild("bone2", ModelPartBuilder.create().uv(6, 95).cuboid(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.75F, -2.5F));

        ModelPartData helmet = Head.addChild("helmet", ModelPartBuilder.create().uv(7, 86).cuboid(-4.0F, -3.0F, -3.25F, 8.0F, 8.0F, 1.0F, new Dilation(0.5F))
                .uv(75, 29).cuboid(-3.0F, -2.0F, -3.5F, 6.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, -0.5F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void render(LivingEntity entity, float tickDelta, MatrixStack matrices, VertexConsumer vertexConsumers, int light, float r, float g, float b, float alpha) {
        matrices.push();

        SuitAnimationHolder anim = this.getAnimation((AbstractClientPlayerEntity) entity).orElse(null);
        if (anim == null || anim.getInfo().transform() == AnimationInfo.Transform.TARGETED) {
            this.rotateParts(entity);
            matrices.translate(0f, -1.5125f, 0f);
        }

        matrices.scale(1.01f, 1.01f, 1.01f);

        this.getPart().render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, r, g, b, alpha);

        matrices.pop();
    }

    private void rotateParts(LivingEntity entity) {
        if (!FlightPower.isFlying((PlayerEntity) entity)) return;

        Vec3d velocity = entity.getVelocity().rotateY(((float) Math.toRadians(entity.getYaw())));

        float velocityY = Math.min((float) velocity.y / 2f, 0);
        float velocityX = (float) velocity.x / 2f;
        float velocityZ = (float) velocity.z / 2f;

        this.rightArm.pitch = velocityZ;
        this.leftArm.pitch = velocityZ;
        this.rightArm.roll += velocityX + 0.1f - velocityY;
        this.leftArm.roll += velocityX - 0.1f + velocityY;

        this.rightLeg.pitch = velocityZ / 1.25f - velocityY;
        this.leftLeg.pitch = velocityZ / 1.25f - velocityY;
        this.rightLeg.roll = this.rightArm.roll / 2f;
        this.leftLeg.roll = this.leftArm.roll / 2f;

        this.rightLeg.pitch = Math.min(this.rightLeg.pitch, 1.25f);
        this.leftLeg.pitch = Math.min(this.leftLeg.pitch, 1.25f);

        this.body.pitch = velocityZ / 3f - (velocityY / 3f);
        this.body.roll = velocityX / 3f;

        this.rightLeg.pivotY -= this.body.pitch * 3f * 1.4f;
        this.rightLeg.pivotZ += this.body.pitch * 3f * 3.2f;
        this.rightLeg.pivotX -= this.body.roll * 3f * 3.2f;

        this.leftLeg.pivotY -= this.body.pitch * 3f * 1.4f;
        this.leftLeg.pivotZ += this.body.pitch * 3f * 3.2f;
        this.leftLeg.pivotX -= this.body.roll * 3f * 3.2f;
    }
    @Override
    public void renderArm(boolean isRight, AbstractClientPlayerEntity player, int i, MatrixStack matrices, VertexConsumer buffer, int light, int i1, int i2, int i3, int i4) {
        if (isRight) this.renderRightArm(player, i, matrices, buffer, light, i1, i2, i3, i4);
        else this.renderLeftArm(player, i, matrices, buffer, light, i1, i2, i3, i4);
    }
    private void renderRightArm(AbstractClientPlayerEntity player, int i, MatrixStack matrices, VertexConsumer buffer, int light, int i1, int i2, int i3, int i4) {
        matrices.push();

        this.rightArm.resetTransform();
        matrices.translate(0f, 1.5f, 0f);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-0.5f));
        this.rightArm.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);

        matrices.pop();
    }
    private void renderLeftArm(AbstractClientPlayerEntity player, int i, MatrixStack matrices, VertexConsumer buffer, int light, int i1, int i2, int i3, int i4) {
        matrices.push();

        this.leftArm.resetTransform();
        matrices.translate(0f, 1.5f, 0f);
        this.leftArm.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);

        matrices.pop();
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void copyFrom(BipedEntityModel<?> model) {
        super.copyFrom(model);

        this.head.copyTransform(model.head);
        this.body.copyTransform(model.body);
        this.leftArm.copyTransform(model.leftArm);
        this.leftLeg.copyTransform(model.leftLeg);
        this.rightArm.copyTransform(model.rightArm);
        this.rightLeg.copyTransform(model.rightLeg);
    }

    @Override
    public void copyTo(BipedEntityModel<?> model) {
        super.copyTo(model);

        model.head.copyTransform(this.head);
        model.body.copyTransform(this.body);
        model.leftArm.copyTransform(this.leftArm);
        model.leftLeg.copyTransform(this.leftLeg);
        model.rightArm.copyTransform(this.rightArm);
        model.rightLeg.copyTransform(this.rightLeg);
    }
}
