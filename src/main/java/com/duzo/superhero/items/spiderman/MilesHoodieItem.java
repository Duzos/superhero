package com.duzo.superhero.items.spiderman;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MilesHoodieItem extends ArmorItem {

    public MilesHoodieItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
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
        return "superhero:textures/heroes/spider_man/" + "miles_hoodie" + ".png";
    }
}
