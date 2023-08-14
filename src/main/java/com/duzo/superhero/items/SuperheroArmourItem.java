package com.duzo.superhero.items;

import com.duzo.superhero.capabilities.AbstractCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static com.duzo.superhero.util.SuperheroUtil.isEquipped;

public class SuperheroArmourItem extends ArmorItem {
    protected final AbstractIdentifier id;

    public SuperheroArmourItem(ArmorMaterial material, Type type, Properties properties, AbstractIdentifier id) {
        super(material, type, properties);
        this.id = id;
    }
    public SuperheroArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material,type,properties);
        this.id = null;
    }

    public AbstractIdentifier getIdentifier() {
        return this.id;
    }

//    public abstract void runAbility(Player player, int number);

    public void runAbility(Player player, int number) {
        for (Supplier<AbstractCapability> cap : this.getIdentifier().getCapabilities()) {
            cap.get().runAbility(number,player);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {

            for (Supplier<AbstractCapability> cap : this.getIdentifier().getCapabilities()) {
                if (!isEquipped(stack,player)) {
                    cap.get().unequippedTick(stack,level,player);
                } else {
                    cap.get().tick(stack, level, player);
                }
            }
        }
    }

    // OR you can override this and add your custom hover text in the item class, this is just if you want to have /n in your translatable string and converted into newlines automatically.
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getIdentifier() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(this.getIdentifier().getHoverTextName()).withStyle(ChatFormatting.GOLD));
        }

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
        StringBuilder base = new StringBuilder();

        for (Supplier<AbstractCapability> capability : this.getIdentifier().getCapabilities()) {
            base.append("/n").append(capability.get().getNameForText());
        }

        return base.toString();
    }

    public static String[] splitStringByNewLines(String string) {
        return string.split("/n");
    }
}
