package com.duzo.superhero.client.gui;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapabilityRegistry;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;

public class JarvisGUIOverlay {
    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_overlay.png");
    private static final ResourceLocation JARVIS_CIRCLE = new ResourceLocation(Superhero.MODID,"textures/misc/jarvis_circle.png");
    private static final ResourceLocation FROST_EFFECT = new ResourceLocation(Superhero.MODID, "textures/misc/powder_snow_outline.png");
    //    private static final ResourceLocation BLUE_HUD = new ResourceLocation(Superhero.MODID,"textures/item/mark_5_chestplate.png");
    public static final IGuiOverlay HUD_JARVIS = (((gui, graphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        AbstractIdentifier id = getIDFromStack(mc.player.getItemBySlot(EquipmentSlot.HEAD));

        if (id == null) return;

        boolean flag = id.getCapabilities().has(SuperheroCapabilityRegistry.JARVIS) || mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManEdithGlasses;

        if (!flag) return;

        // Blue overlay
        graphics.pose().pushPose();
        graphics.blit(BLUE_HUD, 0, 0, 0, 0, screenWidth, screenHeight,screenWidth,screenHeight);
        graphics.pose().popPose();

        graphics.pose().pushPose();
        if(mc.player.getY() > 185 && id.getCapabilities().has(SuperheroCapabilityRegistry.ICES_OVER)) {
            graphics.blit(FROST_EFFECT, 0, 0, 0, 0, screenWidth, screenHeight,screenWidth,screenHeight);
        }
        graphics.pose().popPose();

        // Coordinates
        String i = Math.round(mc.player.getX()) + ", " + Math.round(mc.player.getY()) + ", " + Math.round(mc.player.getZ());
        int coordsWidth = mc.font.width(i);
        graphics.drawString(mc.font,i,x - coordsWidth / 2, (int) (y / 12.5f),0x59d7e3);

        // Direction
        String j = mc.player.getDirection().name();
        int dirWdth = mc.font.width(j);
        graphics.drawString(mc.font,j,x - dirWdth / 2, (int) (y / 8.5f),0x78FF);

        // Speed
        var deltaX = mc.player.getX() - mc.player.xOld;
        var deltaY = mc.player.getY() - mc.player.yOld;
        var deltaZ = mc.player.getZ() - mc.player.zOld;
        var speed = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        String l = "" + Math.round(speed * 20) + " b/s";
        int spdWdth = mc.font.width(l);
        graphics.drawString(mc.font,l, (int) ((x - spdWdth / 2.25f) * 1.35f), (int) (y / 1.125f),0x59d7e3);

        // Looked at entity details
        Entity crosshair = mc.crosshairPickEntity;

        if (crosshair != null) {
            // Very compact because i couldnt be bothered creating tons of variables

            // Name
            graphics.drawString(mc.font,crosshair.getName().getVisualOrderText(), (int) (((float)x-mc.font.width(crosshair.getName())/2f) + ((screenWidth / 10f))), (int) (y/2f),0x59d7e3);
            if (crosshair instanceof LivingEntity living) {
                // Health
                graphics.drawString(mc.font,"" + (int) living.getHealth() + "/" + (int)living.getMaxHealth() + "❤", (int) ((float)x-mc.font.width("" + (int) living.getHealth() + "/" + (int)living.getMaxHealth() + "❤")/2f), (int) (y/2f),0x59d7e3);
                // Damage
                if (living.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                    graphics.drawString(mc.font, "" + living.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + "⚔❤", (int) ((float)x-mc.font.width("" + living.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + "⚔❤")/2f), (int) (y/2f), 0x59d7e3);
                }
            }
            // Speed
            var crossSpeed = Math.sqrt(Math.pow(crosshair.getX() - crosshair.xOld, 2) + Math.pow(crosshair.getY() - crosshair.yOld, 2) + Math.pow(crosshair.getZ() - crosshair.zOld, 2));
            String s = "" + Math.round(crossSpeed * 20) + " b/s";
            graphics.drawString(mc.font,s, (int) ((float)x-mc.font.width(s)/2f), (int) (y/2f),0x59d7e3);
            // Distance away
            graphics.drawString(mc.font,"" + Math.round(mc.player.distanceTo(crosshair)) + " blocks", (int) ((float)x-mc.font.width("" + Math.round(mc.player.distanceTo(crosshair)) + " blocks")/2f), (int) (y/2f),0x59d7e3);

            // Rotating the circle
            drawJARVISCircle(graphics,x / 2f,y / 2f, screenWidth / 8, screenHeight / 4,mc.player.distanceTo(crosshair) * 10);

        }
    }));

    private static void drawJARVISCircle(GuiGraphics graphics, float x, float y, int sizeX, int sizeY, float rotation) {
        graphics.pose().pushPose();
        graphics.pose().translate(x+(sizeX*1.5),y-(sizeY/2f),0);
//        System.out.println(y-sizeY); 177
//        System.out.println(x-sizeX); -24
//        System.out.println(Minecraft.getInstance().mouseHandler.ypos()); 737
//        System.out.println(Minecraft.getInstance().mouseHandler.xpos());  322
        // @TODO rotation as its too annoying for me rn
//        stack.rotateAround(Axis.ZN.rotationDegrees(rotation),(-x/8) + (sizeX/4f),(-y/4) + (sizeY / 4f),0);

        graphics.blit(JARVIS_CIRCLE,0, 0, 0, 0, sizeX, sizeY,sizeX,sizeY);
        graphics.pose().popPose();
    }
}
