package com.duzo.superhero.client.renderers;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import com.duzo.superhero.items.spiderman.SpiderManArmourItem;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;

public class WebRopeEntityRenderer extends EntityRenderer<WebRopeEntity> {
    public WebRopeEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WebRopeEntity entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_) {
//        super.render(entity, p_114486_, p_114487_, stack, source, p_114490_);
//
        if (entity.point == null) return;


        VertexConsumer vertexconsumer = source.getBuffer(RenderType.lightning());

        stack.pushPose();
        Matrix4f matrix4f = stack.last().pose();
//        Vector3f direction = new Vector3f(entity.point.toVector3f()).sub(entity.origin.toVector3f()).normalize();
//        Quaternionf quaternion = new Quaternionf();
//        quaternion.lookAlong(direction, new Vector3f(0,1,0));
//        quaternion.lookAlong(direction, new Vector3f(0,0,1));
//        quaternion.lookAlong(direction, new Vector3f(1,0,0));
//        stack.mulPose(quaternion);
        stack.translate(0, entity.getEyeHeight(), 0);
//        stack.translate(0,0,1.25);
        //@TODO ITS NOT SHOOTING TO THE FUCKING CETERE EHJRIUWAHTIUHESIUSHGUISDG
        // @TODO either adjust the rotations of THIS so it points to centre of the block OR move the particle to where this points to (second options probably easier)
        stack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        stack.mulPose(Axis.XP.rotationDegrees(entity.getXRot() + 95f)); // 95 is the magic number????
//        Vec3 dir = entity.point.subtract(entity.position());
        stack.popPose();

        int length = (int) Math.round(entity.position().distanceTo(entity.point));

        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f,length,entity.getAlpha(), false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f,length,entity.getAlpha(), true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f,length,entity.getAlpha(), true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -0, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f,length,entity.getAlpha(), false, true, false, false);

//        updateEntityPosition(entity); // dont like it

//        stack.pushPose();
//        Vec3 vec3 = entity.origin;
//        double d0 = (double)(Mth.lerp(p_114487_,-entity.point.x, -entity.point.x) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
//        Vec3 vec31 = entity.point;
//        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
//        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
//        double d3 = Mth.lerp((double)p_114487_, entity.xo, entity.getX()) + d1;
//        double d4 = Mth.lerp((double)p_114487_, entity.yo, entity.getY()) + vec31.y;
//        double d5 = Mth.lerp((double)p_114487_, entity.zo, entity.getZ()) + d2;
//        stack.translate(d1, vec31.y, d2);
//        float f = (float)(vec3.x - d3);
//        float f1 = (float)(vec3.y - d4);
//        float f2 = (float)(vec3.z - d5);
//        float f3 = 0.025F;
//        VertexConsumer vertexconsumer = source.getBuffer(RenderType.leash());
//        Matrix4f matrix4f = stack.last().pose();
//        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
//        float f5 = f2 * f4;
//        float f6 = f * f4;
//        BlockPos blockpos = BlockPos.containing(entity.getEyePosition(p_114487_));
//        BlockPos blockpos1 = BlockPos.containing(entity.getEyePosition(p_114487_));
//        int i = this.getBlockLightLevel(entity, blockpos);
//        int j = LightTexture.FULL_BRIGHT;
//        int k = entity.level.getBrightness(LightLayer.SKY, blockpos);
//        int l = entity.level.getBrightness(LightLayer.SKY, blockpos1);
//
//        for(int i1 = 0; i1 <= 24; ++i1) {
//            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
//        }
//
//        for(int j1 = 24; j1 >= 0; --j1) {
//            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
//        }
//
//        stack.popPose();
    }

    private static void updateEntityPosition(WebRopeEntity entity) {
        Player player = entity.level.getNearestPlayer(entity,10d);

        if (player == null) return;

        if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpiderManArmourItem)) return;

        int i = Mth.clamp(0, 0, 64);
        float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
        entity.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
    }

    private static void quad(Matrix4f p_253966_, VertexConsumer p_115274_, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float p_115280_, float p_115281_, float p_115282_, float p_115283_, float p_115284_,int length,float alpha, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
        p_115274_.vertex(p_253966_, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * length), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
        p_115274_.vertex(p_253966_, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float)(p_115277_ * length), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, alpha).endVertex();
    }


    private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
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
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
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
