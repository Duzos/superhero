package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.util.spiderman.SpiderManIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;

public class SpiderManNanotechItem extends SuperheroArmourItem {
    public SpiderManNanotechItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void runAbility(Player player, int number) {
        Level level = player.getLevel();
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (number == 2) {
            if (chest.getItem() instanceof SpiderManNanotechItem) {
                convertNanotechToArmour(chest,player);
            }
        }
    }

    @Override
    public boolean isValidArmor(LivingEntity player) {
        return player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpiderManNanotechItem;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/heroes/generic/" + "nanotech" + ".png";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (getID(stack) != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(fileNameToUsable(getID(stack).getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public static SpiderManIdentifier getID(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("id") == "") return null;

            return SpiderManIdentifier.valueOf(stack.getTag().getString("id").toUpperCase());
        }
        return null;
    };
    public static void setID(ItemStack stack, SpiderManIdentifier id) {
        if (stack.hasTag()) {
            stack.getTag().putString("id",id.getSerializedName());
        }
    };


    public static void convertArmourToNanotech(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpiderManArmourItem item)) return;

        ItemStack stack = new ItemStack(SuperheroItems.SPIDERMAN_NANOTECH.get());
        setID(stack,item.getIdentifier());

        player.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,stack);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    public static void convertNanotechToArmour(ItemStack stack, Player player) {
        SpiderManIdentifier id = getID(stack);
        if (id == null) return;

        equipSpiderSuitForID(id,player,true);
    }

    public static boolean equipSpiderSuitForID(SpiderManIdentifier id, Player player, boolean excludeNanotech) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            if (!player.getItemBySlot(slot).isEmpty()) {
                if (excludeNanotech && slot == EquipmentSlot.CHEST) {
                    if (player.getItemBySlot(slot).getItem() instanceof SpiderManNanotechItem) {
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
