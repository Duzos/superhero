package com.duzo.superhero.blocks.entities;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.SuperheroBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Superhero.MODID);

    public static final RegistryObject<BlockEntityType<SuitMakerBlockEntity>> SUIT_MAKER = BLOCK_ENTITIES.register("suit_maker",
            () -> BlockEntityType.Builder.of(SuitMakerBlockEntity::new, SuperheroBlocks.SUIT_MAKER.get()).build(null)
    );
}
