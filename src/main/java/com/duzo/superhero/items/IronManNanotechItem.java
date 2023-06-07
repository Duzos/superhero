package com.duzo.superhero.items;

import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.NoteBlockEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.equipArmourForMark;
import static com.duzo.superhero.entities.IronManEntity.fileNameToUsable;

public class IronManNanotechItem extends ArmorItem {
    private IronManMark mark;
    public IronManNanotechItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (getMark(stack) != null) {
            components.add(Component.translatable(fileNameToUsable(getMark(stack).getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public static IronManMark getMark(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("mark") == "") return null;

            return IronManMark.valueOf(stack.getTag().getString("mark").toUpperCase());
        }
        return null;
    };
    public static void setMark(ItemStack stack,IronManMark mark) {
        if (stack.hasTag()) {
            stack.getTag().putString("mark",mark.getSerializedName());
        }
    };


    public static void convertArmourToNanotech(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem item)) return;

        ItemStack stack = new ItemStack(SuperheroItems.NANOTECH.get());
        setMark(stack,item.getMark());

        player.setItemSlot(EquipmentSlot.HEAD,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,stack);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    public static void convertNanotechToArmour(ItemStack stack, Player player) {
        IronManMark mark = getMark(stack);
        if (mark == null) return;

        equipArmourForMark(mark,player,true);
    }
}
