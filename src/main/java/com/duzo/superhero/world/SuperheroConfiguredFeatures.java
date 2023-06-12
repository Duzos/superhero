package com.duzo.superhero.world;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.SuperheroBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class SuperheroConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_KEY = registerKey("ebony");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_PALLADIUM_ORE_KEY = registerKey("palladium_ore");
    /*public static final ResourceKey<ConfiguredFeature<?, ?>> END_PALLADIUM_ORE_KEY = registerKey("end_palladium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_PALLADIUM_ORE_KEY = registerKey("nether_palladium_ore");*/

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        /*RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchTest(Blocks.END_STONE);*/

        List<OreConfiguration.TargetBlockState> overworldPalladiumOres = List.of(OreConfiguration.target(stoneReplaceables,
                SuperheroBlocks.PALLADIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, SuperheroBlocks.DEEPSLATE_PALLADIUM_ORE.get().defaultBlockState()));

        /*register(context, MOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Superhero.MOD_LOG.get()),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.simple(Superhero.MOD_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 2)).build());*/

        register(context, OVERWORLD_PALLADIUM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldPalladiumOres, 9));
        /*register(context, END_BLACK_OPAL_ORE_KEY, Feature.ORE, new OreConfiguration(endstoneReplaceables,
                SuperheroBlocks.ENDSTONE_PALLADIUM_ORE.get().defaultBlockState(), 9));
        register(context, NETHER_BLACK_OPEL_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplaceables,
                SuperheroBlocks.NETHERRACK_PALLADIUM_ORE.get().defaultBlockState(), 9));*/
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Superhero.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}