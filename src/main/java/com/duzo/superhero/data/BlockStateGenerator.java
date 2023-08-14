package com.duzo.superhero.data;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.SuperheroBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    private final ExistingFileHelper fileHelper;
    public BlockStateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.fileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> entry : SuperheroBlocks.BLOCKS.getEntries()) {
            // @TODO this better
            if (entry.get() instanceof StairBlock) {
                stairsBlock((StairBlock) entry.get(), new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + ".png"));
                continue;
            }
            if (entry.get() instanceof WallBlock) {
                wallBlock((WallBlock) entry.get(), new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + ".png"));
            }
            if (entry.get() instanceof SlabBlock) {
                slabBlock((SlabBlock) entry.get(), new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + ".png"), new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + ".png"));
            }
            if (entry.get() instanceof IronBarsBlock) {
                paneBlock((IronBarsBlock) entry.get(),new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + ".png"),new ResourceLocation(Superhero.MODID,"textures/block/" + entry.get().getDescriptionId() + "_side.png"));
            }

            simpleBlock(entry.get());
        }
    }
    public boolean modelExists(Block block) {
        return this.fileHelper.exists(new ResourceLocation(Superhero.MODID,"block/" + block.getDescriptionId()),  new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "models"));
    }
    public ModelFile getModel(Block block) {
        if (modelExists(block)) {
            return new ModelFile.ExistingModelFile(new ResourceLocation(Superhero.MODID,"block/" + block.getDescriptionId()),this.fileHelper);
        }
        return cubeAll(block);
    }
}
