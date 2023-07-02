package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;

@Deprecated(forRemoval = true)
public class SpiderManArmourItem extends SuperheroArmourItem {
    public SpiderManArmourItem(ArmorMaterial material, Type type, Properties properties, SuperheroIdentifier id) {
        super(material, type, properties,id);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getIdentifier() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(fileNameToUsable(this.getIdentifier().getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
