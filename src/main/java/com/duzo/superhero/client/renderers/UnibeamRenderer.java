package com.duzo.superhero.client.renderers;

import com.duzo.superhero.entities.UnibeamEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.Rotation;
import org.joml.Matrix4f;
import org.joml.Random;

public class UnibeamRenderer extends EntityRenderer<UnibeamEntity> {
    public UnibeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(UnibeamEntity entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_) {
        VertexConsumer vertexconsumer = source.getBuffer(RenderType.lightning());

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        stack.mulPose(Axis.XP.rotationDegrees(90f));
        Matrix4f matrix4f = stack.last().pose();
        stack.popPose();

        //Outside beam
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.10f, 0.10f, false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.10f, 0.10f, true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.10f, 0.10f, true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.10f, 0.10f, false, true, false, false);

        //Inside beam
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, false, true, false, false);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 0.94901960784f, 0.63921568627f, 0.14117647058f, 0.05f, 0.05f, false, true, false, false);

        //Inside beam 2 Electric Boogaloo
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f, false, false, true, false);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f, true, false, true, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f, true, true, false, true);
        quad(matrix4f, vertexconsumer, 0, -1.25f, 0, 0, -1.25f, 1f, 1f, 1f, 0.025f, 0.025f, false, true, false, false);
    }

    // Copied from lightning bolt renderer
    private static void quad(Matrix4f p_253966_, VertexConsumer p_115274_, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float p_115280_, float p_115281_, float p_115282_, float p_115283_, float p_115284_, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
        p_115274_.vertex(p_253966_, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float)(p_115277_ * 16), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, 0.3F).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * 16), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, 0.3F).endVertex();
        p_115274_.vertex(p_253966_, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * 16), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(p_115280_, p_115281_, p_115282_, 0.3F).endVertex();
        p_115274_.vertex(p_253966_, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float)(p_115277_ * 16), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(p_115280_, p_115281_, p_115282_, 0.3F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(UnibeamEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
