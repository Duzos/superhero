package com.duzo.superhero.items;

import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.util.IronManMark;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public abstract class SuperheroArmourItem extends ArmorItem {
    public SuperheroArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
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

    public boolean isValidArmor(LivingEntity player) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (!(currentSlot.getItem() instanceof SuperheroArmourItem item)) {
                return false;
            }
        }
        return true;
    }

    public abstract void runAbility(Player player, int number);
}
