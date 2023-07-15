package com.duzo.superhero.util;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SuperheroUtil {

    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Superhero.MODID,"textures/heroes/generic/invisible.png");

    public static boolean isInvisibleTexture(ResourceLocation location) {
        return location.equals(DEFAULT_TEXTURE);
    }

    public static ResourceLocation getTextureFromID(SuperheroIdentifier id) {
        String s = "textures/heroes/" + id.getSerializedName() + ".png";
        if (!doesResourceLocationExist(s)) {
            return DEFAULT_TEXTURE;
        }
        return new ResourceLocation(Superhero.MODID, s);
    }

    public static ResourceLocation getLightMapFromID(SuperheroIdentifier id) {
        String s = "textures/heroes/" + id.getSerializedName() + "_l.png";
        if (!doesResourceLocationExist(s)) {
//            return getTextureFromID(id);
            return DEFAULT_TEXTURE;
        }
        return new ResourceLocation(Superhero.MODID,s);
    }
    public static boolean doesResourceLocationExist(String location) {
        return doesResourceLocationExist(new ResourceLocation(Superhero.MODID,location));
    }
    public static boolean doesResourceLocationExist(ResourceLocation location) {
        try {
            Minecraft.getInstance().getResourceManager().getResourceOrThrow(location);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
    public static boolean playerHasEffect(Player player, MobEffect effect) {
        for (MobEffectInstance instance : player.getActiveEffects()) {
            if (instance.getEffect() == effect) return true;
        }
        return false;
    }
    public static boolean isEquipped(ItemStack stack, Player player) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;

            if (player.getItemBySlot(equipmentSlot) == stack) {
                return true;
            }
        }
        return false;
    }
    public static boolean keyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
    }
    public static SuperheroIdentifier getIDFromStack(ItemStack stack) {
        if (stack.getItem() instanceof SuperheroArmourItem item) {
            return item.getIdentifier();
        }
//        LogUtils.getLogger().error("Identifier from stack " + stack + " was null! Returning default.");
//        return DEFAULT_ID;
        return null;
    }

    public static SuperheroIdentifier getIDFromPlayer(Player player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            if (getIDFromStack(player.getItemBySlot(slot)) != null) {
                return getIDFromStack(player.getItemBySlot(slot));
            }
        }
        return null;
    }
    public static boolean putSuitOntoPlayer(SuperheroIdentifier id, Player player, boolean excludeNanotech) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            if (!player.getItemBySlot(slot).isEmpty()) {
                if (excludeNanotech && slot == EquipmentSlot.CHEST) {
                    if (player.getItemBySlot(slot).getItem() instanceof SuperheroArmourItem) {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        }

        List<ArmorItem> armour = new ArrayList<>();
        for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
            if (item.getId().toString().contains(id.getSerializedName())) {
                armour.add((ArmorItem) item.get());
            }
        }

        if (armour.isEmpty()) return false;

        for (ArmorItem item : armour) {
            player.setItemSlot(item.getEquipmentSlot(), new ItemStack(item));
        }

        return true;
    }
}
