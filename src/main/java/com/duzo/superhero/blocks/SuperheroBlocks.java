package com.duzo.superhero.blocks;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.SuperheroItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SuperheroBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Superhero.MODID);

    public static final RegistryObject<Block> IRONMAN_SUITCASE = register("ironman_suitcase", () ->
            new IronManSuitCaseBlock(BlockBehaviour.Properties.of().noOcclusion()),new Item.Properties().stacksTo(1));
    public static final RegistryObject<Block> SUIT_MAKER = register("suit_maker", () ->
            new SuitMakerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion()),new Item.Properties().stacksTo(1));

    public static final RegistryObject<Block> PALLADIUM_ORE = register("palladium_ore", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops()
                    .strength(3.0F, 3.0F)), new Item.Properties());

    public static final RegistryObject<Block> DEEPSLATE_PALLADIUM_ORE = register("deepslate_palladium_ore", () ->
            new Block(BlockBehaviour.Properties.copy(PALLADIUM_ORE.get())
                    .mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)), new Item.Properties());

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties ){
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        SuperheroItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
