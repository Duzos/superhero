package com.duzo.superhero.data.client;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.items.SuperheroNanotechItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.KeyBinds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroEnglish extends LanguageProvider {

    public SuperheroEnglish(PackOutput gen) {
        super(gen, Superhero.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Adding hover text
        for (RegistryObject<Item> entry : SuperheroItems.ITEMS.getEntries()) {
            if (entry.get() instanceof SuperheroArmourItem item) {
                if (item instanceof SuperheroNanotechItem) continue;
                if (!item.getIdentifier().autoAdd()) continue;
                add("text." + item.getDescriptionId(),item.getHoverTextMessage());
                add("text." + item.getDescriptionId() + ".shifting",item.getShiftingHoverTextMessage());
            }
        }

        // Auto adding superhero items
        for (RegistryObject<Item> entry : SuperheroItems.ITEMS.getEntries()) {
            if (entry.get() instanceof SuperheroArmourItem item) {
                if (item instanceof SuperheroNanotechItem) continue;
                if (!item.getIdentifier().autoAdd()) continue;
                add(entry.get(),item.getIdentifier().getLangFileName(item.getEquipmentSlot()));
            }
        }

        for (RegistryObject<Item> obj : SuperheroItems.ITEMS.getEntries()) {
            if (obj.get() instanceof SuperheroArmourItem) continue;

            Item item = obj.get();

            add(item,toName(item));
        }
        for (RegistryObject<EntityType<?>> obj : SuperheroEntities.ENTITIES.getEntries()) {
            add(obj.get(),toName(obj.get()));
        }
        for (RegistryObject<SoundEvent> obj : SuperheroSounds.SOUNDS.getEntries()) {
            add(getSubtitle(obj.get()),toName(obj.get()));
        }

        // Tabs
        add("item_group.superhero.superhero","Timeless Heroes");

        // Keybinds
        add(KeyBinds.KEY_ABILITY_ONE, "Suit Ability One");
        add(KeyBinds.KEY_ABILITY_TWO, "Suit Ability Two");
        add(KeyBinds.KEY_ABILITY_THREE, "Suit Ability Three");
        add(KeyBinds.KEY_ABILITY_FOUR, "Suit Ability Four");
    }

    public static String toName(Item item) {
        String id = item.getDescriptionId(); // blah.modid.blah_blah_blah
        String sub = id.substring(id.lastIndexOf(".") + 1); // blah_blah_blah

        String spaced = sub.replace("_", " ");
        String[] words = spaced.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return output.toString().substring(0,output.toString().length() - 1);
    }

    public static String getSubtitle(SoundEvent sound) {
        return "subtitle." + Superhero.MODID + "." + sound.getLocation().getPath();
    }
    public static String toName(EntityType<?> entity) {
        String id = entity.getDescriptionId(); // blah.modid.blah_blah_blah
        String sub = id.substring(id.lastIndexOf(".") + 1); // blah_blah_blah

        String spaced = sub.replace("_", " ");
        String[] words = spaced.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return output.toString().substring(0,output.toString().length() - 1);
    }
    public static String toName(Block block) {
        String id = block.getDescriptionId(); // blah.modid.blah_blah_blah
        String sub = id.substring(id.lastIndexOf(".") + 1); // blah_blah_blah

        String spaced = sub.replace("_", " ");
        String[] words = spaced.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return output.toString().substring(0,output.toString().length() - 1);
    }
    public static String toName(SoundEvent sound) {
        String sub = sound.getLocation().getPath(); // the sound part in modid:sound

        String spaced = sub.replace("_", " ");
        String[] words = spaced.split(" ");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return output.toString().substring(0,output.toString().length() - 1);
    }
}
