package com.duzo.superhero.entities;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.entities.ironman.IronManEntity;
import com.duzo.superhero.entities.ironman.UnibeamEntity;
import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Superhero.MODID);

    public static final RegistryObject<EntityType<IronManEntity>> IRON_MAN_ENTITY = ENTITIES.register("iron_man_entity", () ->
            EntityType.Builder.<IronManEntity>of(IronManEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(Superhero.MODID,"iron_man_entity").toString()));

    public static final RegistryObject<EntityType<UnibeamEntity>> UNIBEAM_ENTITY = ENTITIES.register("unibeam_entity", () ->
            EntityType.Builder.<UnibeamEntity>of(UnibeamEntity::new, MobCategory.MISC).sized(0.125f, 0.125f).build(new ResourceLocation(Superhero.MODID,"unibeam_entity").toString()));

    public static final RegistryObject<EntityType<WebRopeEntity>> WEB_ROPE_ENTITY = ENTITIES.register("web_rope_entity", () ->
            EntityType.Builder.<WebRopeEntity>of(WebRopeEntity::new, MobCategory.MISC).sized(0.125f, 0.125f).build(new ResourceLocation(Superhero.MODID,"web_rope_entity").toString()));
}
