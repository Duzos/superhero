package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.data.client.ModelProviderItem;
import com.duzo.superhero.data.client.SuperheroEnglish;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Superhero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerationEvent {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModelProviderItem(packOutput,existingFileHelper));
        generator.addProvider(true, new SoundDataGeneration(packOutput,existingFileHelper));
        generator.addProvider(true, new SuperheroEnglish(packOutput));
        generator.addProvider(event.includeServer(), new SuperheroWorldGenProvider(packOutput, lookupProvider));
    }
}