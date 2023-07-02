package com.duzo.superhero.items.ironman;

import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.equipArmourForMark;

@Deprecated
public class IronManNanotechItem extends SuperheroArmourItem {
    private SuperheroIdentifier mark;
    public IronManNanotechItem(ArmorMaterial material, Type type, Item.Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void runAbility(Player player, int number) {
        Level level = player.getLevel();
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (number == 1) {
            if (chest.getItem() instanceof IronManNanotechItem) {
                convertNanotechToArmour(chest,player);
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERUP.get(), SoundSource.PLAYERS,1f,1f);
            }
        }
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
        if (getMark(stack) != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(this.getIdentifier().getHoverTextName()).withStyle(ChatFormatting.GOLD));
        }
    }

    public static SuperheroIdentifier getMark(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().getString("mark") == "") return null;

            return SuperheroIdentifier.valueOf(stack.getTag().getString("mark").toUpperCase());
        }
        return null;
    };
    public static void setMark(ItemStack stack,SuperheroIdentifier mark) {
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
        SuperheroIdentifier mark = getMark(stack);
        if (mark == null) return;

        equipArmourForMark(mark,player,true);
    }
}
