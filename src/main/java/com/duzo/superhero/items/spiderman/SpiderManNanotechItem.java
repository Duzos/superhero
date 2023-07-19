package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
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

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.util.spiderman.SpiderManUtil.equipSpiderSuitForID;

@Deprecated
public class SpiderManNanotechItem extends SuperheroArmourItem {
    public SpiderManNanotechItem(ArmorMaterial material, Type type, Item.Properties properties, AbstractIdentifier id) {
        super(material, type, properties,id);
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
//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
//                return new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));
//            }
//        });
//        super.initializeClient(consumer);
//    }

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

    public static AbstractIdentifier getID(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("id").equals("")) return null;

            return SuperheroUtil.getIDFromStack(stack);
        }
        return null;
    };
    public static void setID(ItemStack stack, AbstractIdentifier id) {
        if (stack.hasTag()) {
            stack.getTag().putString("id",id.getSerializedName());
        }
    };


    public static void convertArmourToNanotech(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SuperheroArmourItem item)) return;

        if (!item.getIdentifier().getCapabilities().has(SuperheroCapability.NANOTECH)) return;

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
        equipSpiderSuitForID(id,player,true);
    }
}
