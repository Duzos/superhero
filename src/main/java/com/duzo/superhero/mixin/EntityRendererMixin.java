package com.duzo.superhero.mixin;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import com.duzo.superhero.util.ironman.IronManUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Shadow protected EntityRenderDispatcher entityRenderDispatcher;

    @Shadow public abstract Font getFont();
    private static final ResourceLocation JARVIS_CIRCLE = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_circle.png");

    protected void renderJARVISOverlays(T entity, PoseStack stack, MultiBufferSource source, int p_114502_) {
        if (entity instanceof ItemEntity) return;

        EntityRenderer renderer = (EntityRenderer) (Object) this;
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        boolean flag = IronManUtil.isIronManSuit(mc.player.getItemBySlot(EquipmentSlot.CHEST)) || mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManEdithGlasses;
        if (!flag) return;
        // Everything past here is only shown with JARVIS hud up

        // WIP code of the things looking like there physically there in space, issues with locations @TODO because its cooler

//        renderJARVISCircle(entity,stack);
//        renderJARVISEntityText(entity,stack,source,p_114502_);
    }

    private void renderJARVISEntityText(T entity, PoseStack stack, MultiBufferSource source, int p_114502_) {
        stack.pushPose();
        stack.rotateAround(this.entityRenderDispatcher.cameraOrientation(), -entity.getBbWidth(), entity.getBbHeight() / 1.5f,0);
        stack.scale(-0.01F, -0.01F, 0.01F);

        Matrix4f matrix4f = stack.last().pose();
        Font font = this.getFont();
        float f2 = (float)(-font.width(entity.getName().getString()) / 2);
        font.drawInBatch(entity.getName().getString(),f2, 0, 0x78FF, false, matrix4f, source, Font.DisplayMode.NORMAL, 0, p_114502_);
        stack.popPose();
    }

    private void renderJARVISCircle(T entity, PoseStack stack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, JARVIS_CIRCLE);

        stack.pushPose();
//        stack.translate(-entity.getBbWidth(),0,-entity.getBbWidth());
        stack.rotateAround(this.entityRenderDispatcher.cameraOrientation(), entity.getBbWidth(), 0,entity.getBbWidth());
//        blit(stack, 0, 0, 0, 0, 1,1,1,1);
        stack.popPose();
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(T entity, float p_114486_, float p_114487_, PoseStack stack, MultiBufferSource source, int p_114490_, CallbackInfo ci) {
//        this.renderJARVISOverlays(entity,stack,source,p_114490_);
    }
}
