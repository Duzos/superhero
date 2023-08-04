package com.duzo.superhero.client.gui;

import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;

public class SpiderManGUIOverlay {
    public static final IGuiOverlay HUD_SPIDERMAN = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        AbstractIdentifier id = getIDFromStack(mc.player.getItemBySlot(EquipmentSlot.HEAD));

        if (id == null) return;

        boolean flag = id.getCapabilities().has(SuperheroCapability.SPIDERMAN_HUD);

        if (!flag) return;

//        String i = KeyBinds.ABILITY_ONE.getKey().toString().toUpperCase() + " - Web Swinging";
//        int iWdth = mc.font.width(i);
//        mc.font.drawShadow(poseStack,i,(screenWidth / 2) - 50 - iWdth,screenHeight - 150,0xffffff);
//
//        String i1 = KeyBinds.ABILITY_TWO.getKey().toString().toUpperCase() + " - Web Zip";
//        int i1Wdth = mc.font.width(i1);
//        mc.font.drawShadow(poseStack,i1,(screenWidth / 2) - 50 - i1Wdth,screenHeight - 140,0xffffff);
//
//        String i2 = KeyBinds.ABILITY_THREE.getKey().toString().toUpperCase() + " - invalid";
//        int i2Wdth = mc.font.width(i2);
//        mc.font.drawShadow(poseStack,i2,(screenWidth / 2) - 50 - i2Wdth,screenHeight - 130,0xffffff);

        // BPS Speed
        //var deltaX = mc.player.getX() - mc.player.xOld;
        //var deltaY = mc.player.getY() - mc.player.yOld;
        //var deltaZ = mc.player.getZ() - mc.player.zOld;
        //var speed = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
//
        //String l = "" + Math.round(speed * 20) + " b/s";
        //int spdWdth = mc.font.width(l);
//      //  mc.font.draw(poseStack,l,(screenWidth / 2) - 96 - spdWdth,screenHeight - 11,0xfad543);
        //mc.font.drawShadow(poseStack,l,(screenWidth / 2) - 96 - spdWdth,screenHeight - 11,0xfaab43);
    }));
}
