package com.duzo.superhero.data.client;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroEnglish extends LanguageProvider {

    public SuperheroEnglish(PackOutput gen) {
        super(gen, Superhero.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Auto adding iron man items
        for (RegistryObject<Item> entry : SuperheroItems.ITEMS.getEntries()) {
            if (entry.get() instanceof IronManArmourItem item) {
                if (!item.getMark().autoAdd()) continue;
                add(entry.get(),item.getMark().getLangFileName(item.getEquipmentSlot()));
            }
        }

        // Items
        add(SuperheroItems.NANOTECH.get(),"Nanotech");
        add(SuperheroItems.EDITH_GLASSES.get(),"EDITH Glasses");
        add(SuperheroItems.PALLADIUM_INGOT.get(), "Palladium Ingot");
        add(SuperheroItems.RAW_PALLADIUM.get(), "Raw Palladium");

        // Blocks
        add(SuperheroBlocks.IRONMAN_SUITCASE.get(), "Iron Man Suitcase");
        add(SuperheroBlocks.PALLADIUM_ORE.get(), "Palladium Ore");
        add(SuperheroBlocks.DEEPSLATE_PALLADIUM_ORE.get(), "Deepslate Palladium Ore");

        // Tabs
        add("item_group.superhero.superhero","Duzo's Superheroes");

        // Keybinds
        add(KeyBinds.KEY_ABILITY_ONE, "Take off Iron Man suit");
    }
}
