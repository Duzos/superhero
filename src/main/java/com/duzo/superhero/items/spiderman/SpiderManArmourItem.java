package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.spiderman.SpiderManIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpiderManArmourItem extends SuperheroArmourItem {
    public static final SpiderManIdentifier DEFAULT_ID = SpiderManIdentifier.MILES;
    private SpiderManIdentifier id;
    public SpiderManArmourItem(ArmorMaterial material, Type type, Properties properties, SpiderManIdentifier id) {
        super(material, type, properties);
        this.id = id;
    }
    public SpiderManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        this(material,type,properties, DEFAULT_ID);
    }

    @Override
    public void runAbility(Player player, int number) {

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                SpiderManArmourItem item = (SpiderManArmourItem) itemStack.getItem(); // frick you craig im casting

                if (item.getIdentifier().isSlim()) {
                    AlexSkinModel<?> model = new AlexSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlexSkinModel.LAYER_LOCATION));

                    model.setAllVisible(false);

                    if (equipmentSlot == EquipmentSlot.HEAD) {
                        model.hat.visible = true;
                        model.head.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.CHEST) {
                        model.body.visible = true;
                        model.leftArm.visible = true;
                        model.rightArm.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                        model.leftLeg.visible = true;
                        model.rightLeg.visible = true;
                    }

                    return model;
                } else {
                    SteveSkinModel<?> model = new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));

                    model.setAllVisible(false);

                    if (equipmentSlot == EquipmentSlot.HEAD) {
                        model.hat.visible = true;
                        model.head.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.CHEST) {
                        model.body.visible = true;
                        model.leftArm.visible = true;
                        model.rightArm.visible = true;
                    } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                        model.leftLeg.visible = true;
                        model.rightLeg.visible = true;
                    }

                    return model;
                }
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/heroes/spider_man/" + this.getIdentifier().getSerializedName() + ".png";
    }

    public SpiderManIdentifier getIdentifier() {
        return this.id;
    }
}
