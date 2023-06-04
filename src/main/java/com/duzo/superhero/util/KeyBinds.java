package com.duzo.superhero.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static final String KEY_CATEGORY_SUPERHERO = "key.category.duzo.superher";
    public static final String KEY_TAKE_OFF_IRON_MAN_SUIT = "key.duzo.take_off_iron_man_suit";

    public static final KeyMapping TAKE_OFF_IRON_MAN_SUIT = new KeyMapping(
            KEY_TAKE_OFF_IRON_MAN_SUIT,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            KEY_CATEGORY_SUPERHERO
    );
}
