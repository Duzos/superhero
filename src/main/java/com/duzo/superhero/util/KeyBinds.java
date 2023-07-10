package com.duzo.superhero.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static final String KEY_CATEGORY_SUPERHERO = "key.category.superhero.superhero";
    public static final String KEY_ABILITY_ONE = "key.superhero.ability_one";
    public static final String KEY_ABILITY_TWO = "key.superhero.ability_two";
    public static final String KEY_ABILITY_THREE = "key.superhero.ability_three";
    public static final String KEY_ABILITY_FOUR = "key.superhero.ability_four";

    public static final KeyMapping ABILITY_ONE = new KeyMapping(
            KEY_ABILITY_ONE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            KEY_CATEGORY_SUPERHERO
    );
    public static final KeyMapping ABILITY_TWO = new KeyMapping(
            KEY_ABILITY_TWO,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            KEY_CATEGORY_SUPERHERO
    );
    public static final KeyMapping ABILITY_THREE = new KeyMapping(
            KEY_ABILITY_THREE,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            KEY_CATEGORY_SUPERHERO
    );
    public static final KeyMapping ABILITY_FOUR = new KeyMapping(
            KEY_ABILITY_FOUR,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY_SUPERHERO
    );
}
