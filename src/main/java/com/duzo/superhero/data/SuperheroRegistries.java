package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.util.SuperheroIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid= Superhero.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class SuperheroRegistries {
    public static Supplier<IForgeRegistry<SuperheroIdentifier>> HEROES_REGISTRY;
    @SubscribeEvent
    public static void register(NewRegistryEvent event) {
        HEROES_REGISTRY = event.create(new RegistryBuilder<SuperheroIdentifier>().setName(new ResourceLocation(Superhero.MODID,"heroes")));
    }
    @SubscribeEvent
    public static void datapack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(HEROES_REGISTRY.get().getRegistryKey(),SuperheroIdentifier.CODEC);
    }
}
