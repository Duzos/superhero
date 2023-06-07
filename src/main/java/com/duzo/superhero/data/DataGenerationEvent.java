package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.data.client.ModelProviderItem;
import com.duzo.superhero.data.client.SuperheroEnglish;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerationEvent {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new ModelProviderItem(packOutput,existingFileHelper));
        generator.addProvider(true, new SoundDataGeneration(packOutput,existingFileHelper));
        generator.addProvider(true, new SuperheroEnglish(packOutput));
    }
}