package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public abstract class IronManArmourItem extends ArmorItem {
    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public abstract String getMark();
    public ResourceLocation getTexture() {
        return new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark() + ".png");
    }


}
