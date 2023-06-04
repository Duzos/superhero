package com.duzo.superhero.data.client;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.SuperheroItems;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.logging.Logger;

public class ModelProviderItem extends ItemModelProvider {

    public ModelProviderItem(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Superhero.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        for (RegistryObject<Item> entry : SuperheroItems.ITEMS.getEntries()) {
//            if (entry.get() instanceof BlockItem item) {
//                blockItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(entry.get())));
//                continue;
//            }
//
//            simpleItem(entry);
//        }
        basicItem(SuperheroItems.MARK_5_CHESTPLATE.getId());
    }


    public ItemModelBuilder blockItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(item.getNamespace(), "block/" + item.getPath())));
    }
}
