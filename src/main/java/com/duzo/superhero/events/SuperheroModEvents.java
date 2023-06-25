package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.entities.ironman.IronManEntity;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.particles.SuperheroParticles;
import com.duzo.superhero.particles.spiderman.WebParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SuperheroModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(SuperheroEntities.IRON_MAN_ENTITY.get(), IronManEntity.getIronManAttributes().build());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(SuperheroParticles.WEB_PARTICLES.get(), WebParticle.Provider::new);
    }
}