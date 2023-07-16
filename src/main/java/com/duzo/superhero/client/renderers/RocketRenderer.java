package com.duzo.superhero.client.renderers;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.SuperheroModels;
import com.duzo.superhero.client.models.entities.RocketModel;
import com.duzo.superhero.entities.ironman.RocketEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import static com.duzo.superhero.client.models.SuperheroModels.getRoot;

public class RocketRenderer extends EntityRenderer<RocketEntity> {
    public RocketRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private RocketModel<RocketEntity> model;

    @Override
    public void render(RocketEntity entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_) {
        if (this.model == null) {
            this.model = new RocketModel<RocketEntity>(getRoot(SuperheroModels.ROCKET));
        }
        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(180f));
        stack.translate(0,-1.5f,0);

        this.model.renderToBuffer(stack,source.getBuffer(RenderType.entitySmoothCutout(getTextureLocation(entity))),p_114490_, OverlayTexture.NO_OVERLAY,1,1,1,1);
        stack.popPose();
    }

    @Override
    public boolean shouldRender(RocketEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntity entity) {
        return new ResourceLocation(Superhero.MODID,"textures/entities/rocket.png");
    }
}
