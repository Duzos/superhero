package com.duzo.superhero.particles;

import com.duzo.superhero.Superhero;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Superhero.MODID);

    public static final RegistryObject<SimpleParticleType> WEB_PARTICLES = PARTICLE_TYPES.register("web_particles",() -> new SimpleParticleType(true));
}
