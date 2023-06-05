package com.duzo.superhero.blocks;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.SuperheroItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SuperheroBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Superhero.MODID);

    public static final RegistryObject<Block> IRONMAN_SUITCASE = register("ironman_suitcase", () -> new IronManSuitCaseBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()),new Item.Properties().stacksTo(1));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties ){
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        SuperheroItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
