package com.duzo.superhero.items;

import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.util.SuperheroUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.duzo.superhero.util.SuperheroUtil.isEquipped;
import static com.duzo.superhero.util.SuperheroUtil.putSuitOntoPlayer;

public class SuperheroNanotechItem extends SuperheroArmourItem {
    public SuperheroNanotechItem(ArmorMaterial material, Type type, Item.Properties properties, AbstractIdentifier identifier) {
        super(material, type, properties,identifier);
    }

    /**
     * Do not use for creating instances, is just for registering the item!!
     * @param material
     * @param type
     * @param properties
     */
    public SuperheroNanotechItem(ArmorMaterial material,Type type,Item.Properties properties) {
        super(material,type,properties);
        // Do nothing, is just for registering
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/heroes/generic/" + "nanotech" + ".png";
    }

    @Override
    public void runAbility(Player player, int number) {
        SuperheroCapability.NANOTECH.runAbility(number,player);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (entity instanceof Player player) {
            if (!isEquipped(stack,player)) return;

            SuperheroCapability.NANOTECH.tick(stack, level,player);
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (getID(stack) != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(this.getIdentifier().getHoverTextName()).withStyle(ChatFormatting.GOLD));
        }
    }

    public static AbstractIdentifier getID(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("id").equals("")) return null;

            return SuperheroUtil.getIDFromStack(stack);
        }
        return null;
    }
    public static void setID(ItemStack stack, AbstractIdentifier id) {
        if (stack.hasTag()) {
            stack.getTag().putString("id",id.getSerializedName());
        }
    }


    public static void convertArmourToNanotech(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SuperheroArmourItem item)) return;

        ItemStack stack = new ItemStack(SuperheroItems.NANOTECH.get());
        setID(stack,item.getIdentifier());

        player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,stack);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    public static void convertNanotechToArmour(ItemStack stack, Player player) {
        AbstractIdentifier id = getID(stack);
        if (id == null) return;
        putSuitOntoPlayer(id,player,true);
    }
}
