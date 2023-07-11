package com.duzo.superhero.items.ironman;

import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.KeyBinds;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IronManArmourItem extends SuperheroArmourItem {
    private SuperheroIdentifier mark;

    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties, SuperheroIdentifier id) {
        super(material, type, properties, id);
    }

    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public void setMark(SuperheroIdentifier mark) {
        this.mark = mark;
    }

    @Deprecated
    /**
     * Use {@link SuperheroArmourItem#getIdentifier()}
     */
    public SuperheroIdentifier getMark() {
        return this.mark;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                IronManArmourModel<?> model = new IronManArmourModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(IronManArmourModel.LAYER_LOCATION));
                model.setAllVisible(false);

                if (equipmentSlot == EquipmentSlot.HEAD) {
                    model.hat.visible = true;
                    model.head.visible = true;
                } else if (equipmentSlot == EquipmentSlot.CHEST) {
                    model.body.visible = true;
                    model.leftArm.visible = true;
                    model.rightArm.visible = true;
                    model.right_arm.getChild("rightArmFlame").visible = false;
                    model.left_arm.getChild("leftArmFlame").visible = false;
                } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                    model.leftLeg.visible = true;
                    model.rightLeg.visible = true;
                    model.right_leg.getChild("rightLegFlame").visible = false;
                    model.left_leg.getChild("leftLegFlame").visible = false;
                }

                ForgeHooksClient.copyModelProperties(original,model);

                return model;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/entities/iron_man/" + this.getIdentifier().getSerializedName().toLowerCase() + ".png";
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
