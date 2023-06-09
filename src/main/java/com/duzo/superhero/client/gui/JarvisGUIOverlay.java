package com.duzo.superhero.client.gui;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static net.minecraft.client.gui.GuiComponent.blit;

public class JarvisGUIOverlay {
    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_overlay.png");
//    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/item/mark_5_chestplate.png");
    public static final IGuiOverlay HUD_JARVIS = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BLUE_HUD);

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        if (mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem || mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManEdithGlasses) {
//            GuiComponent.blit(poseStack, x - imageHeight, y - imageWidth, 0, 0, imageHeight, imageWidth, imageHeight, imageWidth);
            poseStack.pushPose();
//            poseStack.translate(0.0F, 0.0F, 90.0F);
            blit(poseStack, 0, 0, 0, 0, screenWidth, screenHeight,screenWidth,screenHeight);
            poseStack.popPose();

            String i = Math.round(mc.player.getX()) + ", " + Math.round(mc.player.getY()) + ", " + Math.round(mc.player.getZ());
            int coordsWidth = mc.font.width(i);
            mc.font.draw(poseStack,i,x - coordsWidth / 2,y / 12.5f,0x59d7e3);

            String j = mc.player.getDirection().name();
            int dirWdth = mc.font.width(j);
            mc.font.draw(poseStack,j,x - dirWdth / 2,y / 8.5f,0x78FF);

            var deltaX = mc.player.getX() - mc.player.xOld;
            var deltaY = mc.player.getY() - mc.player.yOld;
            var deltaZ = mc.player.getZ() - mc.player.zOld;
            var speed = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

            String l = "" + Math.round(speed * 20) + " b/s";
            int spdWdth = mc.font.width(l);
            mc.font.draw(poseStack,l,(x - spdWdth / 2.25f) * 1.35f,y / 1.125f,0x59d7e3);
        }
    }));
}
