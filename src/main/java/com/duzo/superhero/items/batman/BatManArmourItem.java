package com.duzo.superhero.items.batman;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.batman.BatManCapability;
import com.duzo.superhero.util.batman.BatManIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
public class BatManArmourItem extends SuperheroArmourItem {
    public static final BatManIdentifier DEFAULT_ID = BatManIdentifier.BATMAN_VS_SUPERMAN;
    private BatManIdentifier id;
    public BatManArmourItem(ArmorMaterial material, Type type, Properties properties, BatManIdentifier id) {
        super(material, type, properties);
        this.id = id;
    }
    public BatManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        this(material,type,properties, DEFAULT_ID);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getIdentifier() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(fileNameToUsable(this.getIdentifier().getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public String getShiftingHoverTextMessage() {
        StringBuilder base = new StringBuilder();

        for (BatManCapability capability : this.getIdentifier().getCapabilities()) {
            base.append("/n").append(fileNameToUsable(capability.getSerializedName()));
        }

        return base.toString();
    }

    @Override
    public void runAbility(Player player, int number) {
        // Always server-side
        if (number == 1) {

        } else if (number == 2) {

        }
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                BatManArmourItem item = (BatManArmourItem) itemStack.getItem(); // frick you craig im casting

                if (item.getIdentifier().isSlim()) {
                    return new AlexSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlexSkinModel.LAYER_LOCATION));
                } else {
                    return new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));
                }
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/heroes/batman/" + this.getIdentifier().getSerializedName() + ".png";
    }

    @Override
    public boolean isValidArmor(LivingEntity player) {
        BatManIdentifier currentMark = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof BatManArmourItem item) {
                if (currentMark == null) {
                    currentMark = item.getIdentifier();
                } else if (!currentMark.equals(item.getIdentifier())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public BatManIdentifier getIdentifier() {
        return this.id;
    }
}
