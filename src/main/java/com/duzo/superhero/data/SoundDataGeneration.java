package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.sounds.SuperheroSounds;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.RegistryObject;

public class SoundDataGeneration extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    protected SoundDataGeneration(PackOutput output, ExistingFileHelper helper) {
        super(output, Superhero.MODID, helper);
    }

    @Override
    public void registerSounds() {
        for (RegistryObject<SoundEvent> entry : SuperheroSounds.SOUNDS.getEntries()) {
            createDefinitionAndAdd(entry.get(), SoundDefinition.SoundType.SOUND, entry.get().getLocation().getPath(), entry.get().getLocation().getPath());
        }
    }

    // Craig's code, I dont know how to do data but he told me to look at weeping angels for reference https://github.com/Suff99/Weeping-Angels/blob/architectury/1.19/forge/src/main/java/mc/craig/software/angels/data/forge/SoundProvider.java
    public void createDefinitionAndAdd(SoundEvent mainSound, SoundDefinition.SoundType soundType, String subtitle, String... soundEvent) {
        SoundDefinition def = SoundDefinition.definition().subtitle("subtitle." + Superhero.MODID + "." + subtitle);
        for (String event : soundEvent) {
            def.with(SoundDefinition.Sound.sound(new ResourceLocation(Superhero.MODID, event), soundType));
        }
        add(mainSound, def);
    }
}
