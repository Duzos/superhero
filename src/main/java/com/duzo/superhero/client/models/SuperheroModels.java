package com.duzo.superhero.client.models;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.entities.RocketModel;
import com.duzo.superhero.client.models.heroes.iron_man.IronManMagicModel;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class SuperheroModels {
    // Entities
    public static ModelLayerLocation HUMANOID = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "humanoid_entity"),"main");
    public static ModelLayerLocation ROCKET = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "rocket_entity"),"main");
    public static ModelLayerLocation IRONMAN_ENTITY = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "ironman_entity"),"main");


    // Generic
    public static ModelLayerLocation STEVE = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "steve_skin"), "main");
    public static ModelLayerLocation ALEX = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "alex_skin"),"main");

    // Armours
    public static ModelLayerLocation IRONMAN_ARMOUR_OLD = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "ironman_armour_old"),"main");
    public static ModelLayerLocation IRONMAN_ARMOUR = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "ironman_armour"),"main");


    public static ModelPart getRoot(ModelLayerLocation layer) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layer);
    }

    public static void addModels(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(HUMANOID,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
//        event.registerLayerDefinition(SteveSkinModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        e.registerLayerDefinition(STEVE,SteveSkinModel::createBodyLayer);
        e.registerLayerDefinition(ALEX,AlexSkinModel::createBodyLayer);
        e.registerLayerDefinition(ROCKET,RocketModel::createBodyLayer);
        e.registerLayerDefinition(IRONMAN_ENTITY,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        e.registerLayerDefinition(IRONMAN_ARMOUR_OLD,IronManArmourModel::createBodyLayer);
        e.registerLayerDefinition(IRONMAN_ARMOUR, IronManMagicModel::createBodyLayer);
    }
}
