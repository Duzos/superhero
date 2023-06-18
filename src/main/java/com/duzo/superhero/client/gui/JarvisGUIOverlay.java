package com.duzo.superhero.client.gui;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Quaternionf;

import static net.minecraft.client.gui.GuiComponent.blit;

public class JarvisGUIOverlay {
    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_overlay.png");
    private static final ResourceLocation JARVIS_CIRCLE = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_circle.png");
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

        boolean flag = mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem || mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManEdithGlasses;

        if (!flag) return;

        // Blue overlay
        poseStack.pushPose();
        blit(poseStack, 0, 0, 0, 0, screenWidth, screenHeight,screenWidth,screenHeight);
        poseStack.popPose();

        // Coordinates
        String i = Math.round(mc.player.getX()) + ", " + Math.round(mc.player.getY()) + ", " + Math.round(mc.player.getZ());
        int coordsWidth = mc.font.width(i);
        mc.font.draw(poseStack,i,x - coordsWidth / 2,y / 12.5f,0x59d7e3);

        // Direction
        String j = mc.player.getDirection().name();
        int dirWdth = mc.font.width(j);
        mc.font.draw(poseStack,j,x - dirWdth / 2,y / 8.5f,0x78FF);

        // Speed
        var deltaX = mc.player.getX() - mc.player.xOld;
        var deltaY = mc.player.getY() - mc.player.yOld;
        var deltaZ = mc.player.getZ() - mc.player.zOld;
        var speed = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        String l = "" + Math.round(speed * 20) + " b/s";
        int spdWdth = mc.font.width(l);
        mc.font.draw(poseStack,l,(x - spdWdth / 2.25f) * 1.35f,y / 1.125f,0x59d7e3);

        // Looked at entity details
        Entity crosshair = mc.crosshairPickEntity;

        if (crosshair != null) {
            // Very compact because i couldnt be bothered creating tons of variables

            // Name
            mc.font.draw(poseStack,crosshair.getName(),(x/* - mc.font.width(crosshair.getName())*/) * 0.3f, y * 0.68f,0x59d7e3);
            if (crosshair instanceof LivingEntity living) {
                // Health
                mc.font.draw(poseStack,"" + (int) living.getHealth() + "/" + (int)living.getMaxHealth() + "❤", x * 0.3f,y * 0.7f,0x59d7e3);
                // Damage
                if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                    mc.font.draw(poseStack, "" + living.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + "⚔❤", x * 0.3f, y * 0.72f, 0x59d7e3);
                }
            }
            // Speed
            var crossSpeed = Math.sqrt(Math.pow(crosshair.getX() - crosshair.xOld, 2) + Math.pow(crosshair.getY() - crosshair.yOld, 2) + Math.pow(crosshair.getZ() - crosshair.zOld, 2));
            String s = "" + Math.round(crossSpeed * 20) + " b/s";
            mc.font.draw(poseStack,s,(x/* - mc.font.width(s)*/) * 0.3f, y * 0.74f,0x59d7e3);
            // Distance away
            mc.font.draw(poseStack,"" + Math.round(mc.player.distanceTo(crosshair)) + " blocks",x * 0.3f, y * 0.76f,0x59d7e3);

            // Rotating the circle
            drawJARVISCircle(poseStack,x * 0.2f,y * 0.6f, screenWidth / 8, screenHeight / 4,mc.player.distanceTo(crosshair) * 10);
        }
    }));

    private static void drawJARVISCircle(PoseStack stack,float x,float y, int sizeX,int sizeY, float rotation) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, JARVIS_CIRCLE);

        stack.pushPose();
        stack.translate(x,y,0);
//        System.out.println(y-sizeY); 177
//        System.out.println(x-sizeX); -24
//        System.out.println(Minecraft.getInstance().mouseHandler.ypos()); 737
//        System.out.println(Minecraft.getInstance().mouseHandler.xpos());  322
        // @TODO rotation as its too annoying for me rn
        stack.rotateAround(Axis.ZN.rotationDegrees(rotation),(-x/8) + (sizeX/4f),(-y/4) + (sizeY / 4f),0);

        blit(stack,0, 0, 0, 0, sizeX, sizeY,sizeX,sizeY);
        stack.popPose();
    }
}
