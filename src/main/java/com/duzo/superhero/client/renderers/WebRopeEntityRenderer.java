package com.duzo.superhero.client.renderers;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class WebRopeEntityRenderer extends EntityRenderer<WebRopeEntity> {
    public WebRopeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WebRopeEntity entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_) {
        if (entity.getPlayer() == null) return;

        // Fishing line method @TODO fix x and z values
//        stack.pushPose();
//        int i = entity.getPlayer().getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
//
//        float f = entity.getPlayer().getAttackAnim(p_114487_);
//        float f1 = Mth.sin(Mth.sqrt(f) * (float)Math.PI);
//        float f2 = Mth.lerp(p_114487_, entity.getPlayer().yBodyRotO, entity.getPlayer().yBodyRot) * ((float)Math.PI / 180F);
//        double d0 = (double)Mth.sin(f2);
//        double d1 = (double)Mth.cos(f2);
//        double d2 = (double)i * 0.35D;
//        double d3 = 0.8D;
//        double d4;
//        double d5;
//        double d6;
//        float f3;
//        if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) && entity.getPlayer() == Minecraft.getInstance().player) {
//            double d7 = 960.0D / (double)this.entityRenderDispatcher.options.fov().get().intValue();
//            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.1F);
//            vec3 = vec3.scale(d7);
//            vec3 = vec3.yRot(f1 * 0.5F);
//            vec3 = vec3.xRot(-f1 * 0.7F);
//            d4 = Mth.lerp((double)p_114487_, entity.getPlayer().xo, entity.getPlayer().getX()) + vec3.x;
//            d5 = Mth.lerp((double)p_114487_, entity.getPlayer().yo, entity.getPlayer().getY()) + vec3.y;
//            d6 = Mth.lerp((double)p_114487_, entity.getPlayer().zo, entity.getPlayer().getZ()) + vec3.z;
//            f3 = entity.getPlayer().getEyeHeight();
//        } else {
//            d4 = Mth.lerp((double)p_114487_, entity.getPlayer().xo, entity.getPlayer().getX()) - d1 * d2 - d0 * 0.8D;
//            d5 = entity.getPlayer().yo + (double)entity.getPlayer().getEyeHeight() + (entity.getPlayer().getY() - entity.getPlayer().yo) * (double)p_114487_;
//            d6 = Mth.lerp((double)p_114487_, entity.getPlayer().zo, entity.getPlayer().getZ()) - d0 * d2 + d1 * 0.8D;
//            f3 = entity.getPlayer().isCrouching() ? -0.1875F : 0.0F;
//        }
//
//        f3 = f3 - 0.2875f;
//
//        double d9 = Mth.lerp((double)p_114487_, entity.xo, entity.getX());
//        double d10 = Mth.lerp((double)p_114487_, entity.yo, entity.getY()) + 0.25D;
//        double d8 = Mth.lerp((double)p_114487_, entity.zo, entity.getZ());
//        float f4 = (float)(d4 - d9);
//        float f5 = (float)(d5 - d10) + f3;
//        float f6 = (float)(d6 - d8);
//        VertexConsumer vertexconsumer1 = source.getBuffer(RenderType.lineStrip());
//        PoseStack.Pose posestack$pose1 = stack.last();
//        int j = 16;
//
//        for(int k = 0; k <= 16; ++k) {
//            stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 16), fraction(k + 1, 16),entity.getAlpha());
//        }
//
//        stack.popPose();
//        super.render(entity, p_114486_, p_114487_, stack, source, p_114490_);

        // Leash method, uncommment for a leash-like render
        stack.pushPose();
        Vec3 vec3 = entity.getPlayer().getRopeHoldPosition(p_114487_);
        double d0 = (double)(Mth.lerp(p_114487_, entity.yRotO, entity.getYRot()) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = entity.getLeashOffset(p_114487_);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp((double)p_114487_, entity.xo, entity.getX()) + d1;
        double d4 = Mth.lerp((double)p_114487_, entity.yo, entity.getY()) + vec31.y;
        double d5 = Mth.lerp((double)p_114487_, entity.zo, entity.getZ()) + d2;
        stack.translate(d1, vec31.y, d2);
        float f = (float)(vec3.x - d3);
        float f1 = (float)(vec3.y - d4);
        float f2 = (float)(vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.lineStrip());
        Matrix4f matrix4f = stack.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(entity.getEyePosition(p_114487_));
        BlockPos blockpos1 = BlockPos.containing(entity.getPlayer().getEyePosition(p_114487_));
        int i = this.getBlockLightLevel(entity, blockpos);
        int j = i;
//        int j = this.entityRenderDispatcher.getRenderer(entity.getPlayer()).getBlockLightLevel(entity.getPlayer(), blockpos1);
        int k = entity.level().getBrightness(LightLayer.SKY, blockpos);
        int l = entity.level().getBrightness(LightLayer.SKY, blockpos1);
        PoseStack.Pose pose = stack.last();

        for(int i1 = 0; i1 <= 24; ++i1) {
            mergedVertex(vertexconsumer,pose, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false,entity.getAlpha());
        }

        for(int j1 = 24; j1 >= 0; --j1) {
            mergedVertex(vertexconsumer,pose, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true,entity.getAlpha());
        }

        stack.popPose();
    }

    private static void quad(Matrix4f p_253966_, VertexConsumer p_115274_, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float p_115280_, float p_115281_, float p_115282_, float p_115283_, float p_115284_,int length,float alpha, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
        p_115274_.vertex(p_253966_, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
    }


    private static float fraction(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

    private static void vertex(VertexConsumer p_254464_, Matrix4f p_254085_, Matrix3f p_253962_, int p_254296_, float p_253632_, int p_254132_, int p_254171_, int p_254026_) {
        p_254464_.vertex(p_254085_, p_253632_ - 0.5F, (float)p_254132_ - 0.5F, 0.0F).color(255, 255, 255, 255).uv((float)p_254171_, (float)p_254026_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_254296_).normal(p_253962_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float p_174119_, float p_174120_, float p_174121_, VertexConsumer p_174122_, PoseStack.Pose p_174123_, float p_174124_, float p_174125_, float alpha) {
        float f = p_174119_ * p_174124_;
        float f1 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5F + 0.25F;
        float f2 = p_174121_ * p_174124_;
        float f3 = p_174119_ * p_174125_ - f;
        float f4 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5F + 0.25F - f1;
        float f5 = p_174121_ * p_174125_ - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        f3 /= f6;
        f4 /= f6;
        f5 /= f6;
        p_174122_.vertex(p_174123_.pose(), f, f1, f2).color(1f, 1f, 1f, alpha).normal(p_174123_.normal(), f3, f4, f5).endVertex();
    }

    private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_, float alpha) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(1f, 1f, 1f, alpha).uv2(k).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(1f, 1f, 1f, alpha).uv2(k).endVertex();
    }

    private static void mergedVertex(VertexConsumer p_174308_,PoseStack.Pose pose, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_, float alpha) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(1f, 1f, 1f, alpha).normal(pose.normal(), f3, f4, f5).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(1f, 1f, 1f, alpha).normal(pose.normal(), f3, f4, f5).endVertex();
//        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 + p_174320_).color(1f, 1f, 1f, alpha).normal(pose.normal(), f3, f4, f5).endVertex();
//        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(1f, 1f, 1f, alpha).normal(pose.normal(), f3, f4, f5).endVertex();
//        p_174308_.vertex(p_254405_, f5, f6, f7).color(1f, 1f, 1f, alpha).normal(pose.normal(), f3, f4, f5).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(WebRopeEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(WebRopeEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
