package com.duzo.superhero.sounds;

import com.duzo.superhero.Superhero;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Superhero.MODID);

    public static final RegistryObject<SoundEvent> IRONMAN_POWERUP = SOUNDS.register("ironman_powerup", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(Superhero.MODID, "ironman_powerup"),8f));
    public static final RegistryObject<SoundEvent> IRONMAN_POWERDOWN = SOUNDS.register("ironman_powerdown", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(Superhero.MODID, "ironman_powerdown"),8f));
    public static final RegistryObject<SoundEvent> IRONMAN_STEP = SOUNDS.register("ironman_step", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(Superhero.MODID, "ironman_step"),8f));
    public static final RegistryObject<SoundEvent> SPIDERMAN_SHOOT = SOUNDS.register("spiderman_shoot", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(Superhero.MODID, "spiderman_shoot"),8f));
}
