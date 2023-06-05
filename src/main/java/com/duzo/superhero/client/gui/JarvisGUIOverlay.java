package com.duzo.superhero.client.gui;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.items.IronManArmourItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class JarvisGUIOverlay {
    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_overlay.png");
//    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/item/mark_5_chestplate.png");
    public static final IGuiOverlay HUD_JARVIS = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;
        int imageWidth = screenWidth / 2;
        int imageHeight = screenHeight * 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BLUE_HUD);

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
            GuiComponent.blit(poseStack, x - imageHeight / 2, y - imageWidth, 0, 0, imageHeight, imageWidth, imageHeight, imageWidth);

            String i = Math.round(mc.player.getX()) + ", " + Math.round(mc.player.getY()) + ", " + Math.round(mc.player.getZ());
            int coordsWidth = mc.font.width(i);
            mc.font.draw(poseStack,i,x - coordsWidth / 2,y / 8,0x59d7e3);

            String j = mc.player.getDirection().name();
            int dirWdth = mc.font.width(j);
            mc.font.draw(poseStack,j,x - dirWdth / 2,y / 6,0x59d7e3);
        }
    }));
}
