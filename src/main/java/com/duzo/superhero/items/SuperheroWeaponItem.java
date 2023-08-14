package com.duzo.superhero.items;

import com.duzo.superhero.capabilities.AbstractCapability;
import com.duzo.superhero.capabilities.SuperheroCapabilityRegistry;
import com.duzo.superhero.ids.AbstractIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.duzo.superhero.util.SuperheroUtil.getIDFromStack;

public class SuperheroWeaponItem extends Item {
    public static final Supplier<AbstractCapability> DEFAULT_CAPABILITY = SuperheroCapabilityRegistry.SUPER_STRENGTH;
    /**
     * The capabilities required for this weapon to run.
     */
    private List<Supplier<AbstractCapability>> capabilities = new ArrayList<>();
    @SafeVarargs
    public SuperheroWeaponItem(Properties properties, Supplier<AbstractCapability>... requiredCapability) {
        super(properties);
        this.capabilities.addAll(List.of(requiredCapability));
    }
    public SuperheroWeaponItem(Properties properties, Supplier<AbstractCapability> requiredCapability) {
        super(properties);
        this.capabilities.add(requiredCapability);
    }
    public SuperheroWeaponItem(Properties properties) {
        this(properties, DEFAULT_CAPABILITY);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);

        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Required abilities to use:").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN));

            for (Supplier<AbstractCapability> cap : this.capabilities) {
                components.add(Component.literal(cap.get().getNameForText()).withStyle(ChatFormatting.GREEN));
            }
        } else {
            components.add(Component.literal("Press <Shift> for more info").withStyle(ChatFormatting.GRAY));
        }
    }

    public static boolean canWeaponBeUsed(ItemStack stack, Player player) {
        AbstractIdentifier id = getIDFromStack(player.getItemBySlot(EquipmentSlot.CHEST));
        if (id == null) return false;

        if (!(stack.getItem() instanceof SuperheroWeaponItem weapon)) return false;

        return id.isValidArmour(player) && id.getCapabilities().has(weapon.capabilities);
    }
}
