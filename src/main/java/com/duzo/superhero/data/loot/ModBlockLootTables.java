package com.duzo.superhero.data.loot;

import com.duzo.superhero.blocks.SuperheroBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F };

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (RegistryObject<Block> obj : SuperheroBlocks.BLOCKS.getEntries()) {
            this.dropSelf(obj.get());
        }
    }



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SuperheroBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
