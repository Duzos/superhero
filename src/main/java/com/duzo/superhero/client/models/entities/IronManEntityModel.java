package com.duzo.superhero.client.models.entities;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.entities.HumanoidEntity;
import com.duzo.superhero.entities.IronManEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class IronManEntityModel extends PlayerModel<IronManEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "iron_man_entity"),"main");
    public IronManEntityModel(ModelPart p_170821_) {
        super(p_170821_, true);
    }
}