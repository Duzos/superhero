package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.world.SuperheroConfiguredFeatures;
import com.duzo.superhero.world.SuperheroPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SuperheroWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, SuperheroConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, SuperheroPlacedFeatures::bootstrap);

    public SuperheroWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Superhero.MODID));
    }
}