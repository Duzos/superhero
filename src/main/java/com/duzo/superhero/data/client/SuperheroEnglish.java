package com.duzo.superhero.data.client;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.IronManSuitCaseBlock;
import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

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

        // Blocks
        add(SuperheroBlocks.IRONMAN_SUITCASE.get(), "Iron Man Suitcase");

        // Tabs
        add("item_group.superhero.superhero","Duzo's Superheroes");

        // Keybinds
        add(KeyBinds.KEY_TAKE_OFF_IRON_MAN_SUIT, "Take off Iron Man suit");
    }
}
