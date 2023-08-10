package com.duzo.superhero.client.renderers;

import com.duzo.superhero.entities.batman.GrapplingHookRopeEntity;
import com.duzo.superhero.items.batman.GrapplingHookWeaponItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

public class GrapplingHookRopeEntityRenderer extends EntityRenderer<GrapplingHookRopeEntity> {
    public GrapplingHookRopeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GrapplingHookRopeEntity entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_) {
//        super.render(entity, p_114486_, p_114487_, stack, source, p_114490_);
//
        if (entity.point == null) return;


        VertexConsumer vertexconsumer = source.getBuffer(RenderType.lightning());

        stack.pushPose();
        Matrix4f matrix4f = stack.last().pose();
        stack.translate(0, entity.getEyeHeight(), 0);
        // These rotations are so annoying, someone else do them pleeeeeeeeeeeeeeeeeeeeeeeeease
        stack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        stack.mulPose(Axis.XP.rotationDegrees(entity.getXRot() + 95f));
        stack.popPose();

        int length = (int) Math.round(entity.position().distanceTo(entity.point));

        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 0.21960784313f, 0.21960784313f, 0.21960784313f, 0.025f, 0.025f,length,1f, false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 0.21960784313f, 0.21960784313f, 0.21960784313f, 0.025f, 0.025f,length,1f, true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 0.21960784313f, 0.21960784313f, 0.21960784313f, 0.025f, 0.025f,length,1f, true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 0.21960784313f, 0.21960784313f, 0.21960784313f, 0.025f, 0.025f,length,1f, false, true, false, false);

        updateEntityPosition(entity);
    }

    private static void updateEntityPosition(GrapplingHookRopeEntity entity) {
        Player player = entity.level().getNearestPlayer(entity,10d);

        if (player == null) return;

        if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GrapplingHookWeaponItem)) return;

        int i = Mth.clamp(0, 0, 64);
        float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
        entity.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3);
    }

    private static void quad(Matrix4f p_253966_, VertexConsumer p_115274_, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float p_115280_, float p_115281_, float p_115282_, float p_115283_, float p_115284_,int length,float alpha, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
        p_115274_.vertex(p_253966_, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
    }


    private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int) Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GrapplingHookRopeEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    @Override
    public boolean shouldRender(GrapplingHookRopeEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }
}
