package com.duzo.superhero.items.ironman;

import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.ids.impls.IronManIdentifier;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.SuperheroUtil;
import com.duzo.superhero.util.ironman.IronManUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
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

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.equipArmourForMark;

@Deprecated
public class IronManNanotechItem extends SuperheroArmourItem {
    public IronManNanotechItem(ArmorMaterial material, Type type, Item.Properties properties, AbstractIdentifier identifier) {
        super(material, type, properties,identifier);
    }

    @Override
    public void runAbility(Player player, int number) {
        Level level = player.level();
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (number == 1) {
            if (chest.getItem() instanceof IronManNanotechItem) {
                convertNanotechToArmour(chest,player);
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERUP.get(), SoundSource.PLAYERS,1f,1f);
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
        if (getMark(stack) != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(this.getIdentifier().getHoverTextName()).withStyle(ChatFormatting.GOLD));
        }
    }

    public static AbstractIdentifier getMark(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("mark") == "") return null;

            return SuperheroUtil.getIDFromStack(stack);
        }
        return null;
    };
    public static void setMark(ItemStack stack,AbstractIdentifier mark) {
        if (stack.hasTag()) {
            stack.getTag().putString("mark",mark.getSerializedName());
        }
    };


    public static void convertArmourToNanotech(Player player) {
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        if (!(IronManUtil.isIronManSuit(chest))) return;

        SuperheroArmourItem item = (SuperheroArmourItem) chest;

        ItemStack stack = new ItemStack(SuperheroItems.NANOTECH.get());
        setMark(stack,(IronManIdentifier) item.getIdentifier());

        player.setItemSlot(EquipmentSlot.HEAD,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.CHEST,stack);
        player.setItemSlot(EquipmentSlot.LEGS,ItemStack.EMPTY);
        player.setItemSlot(EquipmentSlot.FEET,ItemStack.EMPTY);
    }
    public static void convertNanotechToArmour(ItemStack stack, Player player) {
        AbstractIdentifier mark = getMark(stack);
        if (mark == null) return;

        equipArmourForMark(mark,player,true);
    }
}
