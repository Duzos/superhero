package com.duzo.superhero.items;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    // OR you can override this and add your custom hover text in the item class, this is just if you want to have /n in your translatable string and converted into newlines automatically.
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            String[] split = splitStringByNewLines(Component.translatable("text." + stack.getItem().getDescriptionId() + ".shifting").getString());
            if (split.length > 0) {
                for (String line : split) {
                    components.add(Component.literal(line).withStyle(ChatFormatting.GREEN));
                }
            } else {
                components.add(Component.translatable("text." + stack.getItem().getDescriptionId()).withStyle(ChatFormatting.GREEN));
            }
        } else {
            String[] split = splitStringByNewLines(Component.translatable("text." + stack.getItem().getDescriptionId()).getString());
            if (split.length > 0) {
                for (String line : split) {
                    components.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
                }
            } else {
                components.add(Component.translatable("text." + stack.getItem().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public String getHoverTextMessage() {
        return "Press <Shift> for more info";
    }
    public String getShiftingHoverTextMessage() {
        return "";
    }

    public static String[] splitStringByNewLines(String string) {
        return string.split("/n");
    }
}
