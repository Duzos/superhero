package com.duzo.superhero.client.gui;

import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.util.SuperheroIdentifier;
import com.duzo.superhero.util.flash.FlashUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;

public class FlashGUIOverlay {
    public static final IGuiOverlay HUD_FLASH = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (!mc.options.getCameraType().isFirstPerson()) return;

        SuperheroIdentifier id = getIDFromStack(mc.player.getItemBySlot(EquipmentSlot.HEAD));

        if (id == null) return;

        boolean flag = id.getCapabilities().has(SuperheroCapability.FLASH_HUD);

        if (!flag) return;

        // Speed modifier
        double mod = FlashUtil.getSpeed(mc.player);

        if (mod == 1) return;

        String i = "Speed: " + mod;
        int iWdth = mc.font.width(i);
//        mc.font.draw(poseStack,i,(screenWidth / 2) - 96 - iWdth,screenHeight - 22,0xfad543);
        mc.font.drawShadow(poseStack,i,(screenWidth / 2) - 96 - iWdth,screenHeight - 22,0xfaab43);

        // BPS Speed
        var deltaX = mc.player.getX() - mc.player.xOld;
        var deltaY = mc.player.getY() - mc.player.yOld;
        var deltaZ = mc.player.getZ() - mc.player.zOld;
        var speed = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        String l = "" + Math.round(speed * 20) + " b/s";
        int spdWdth = mc.font.width(l);
//        mc.font.draw(poseStack,l,(screenWidth / 2) - 96 - spdWdth,screenHeight - 11,0xfad543);
        mc.font.drawShadow(poseStack,l,(screenWidth / 2) - 96 - spdWdth,screenHeight - 11,0xfaab43);
    }));
}
