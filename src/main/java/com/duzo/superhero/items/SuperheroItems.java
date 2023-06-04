package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SuperheroItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Superhero.MODID);

    public static final RegistryObject<IronManTestingItem> IRON_MAN_MARK_7_SPAWN_ITEM = ITEMS.register("iron_man_mark_7_spawn_item",
            () -> new IronManTestingItemBuilder(new Item.Properties().stacksTo(1)).mark("mark_7"));

    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_7_HELMET = ITEMS.register("iron_man_mark_7_helmet",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)).mark("mark_7"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_7_CHESTPLATE = ITEMS.register("iron_man_mark_7_chestplate",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)).mark("mark_7"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_7_LEGGINGS = ITEMS.register("iron_man_mark_7_leggings",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1)).mark("mark_7"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_7_BOOTS = ITEMS.register("iron_man_mark_7_boots",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1)).mark("mark_7"));

    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_5_HELMET = ITEMS.register("iron_man_mark_5_helmet",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)).mark("mark_5"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_5_CHESTPLATE = ITEMS.register("iron_man_mark_5_chestplate",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)).mark("mark_5"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_5_LEGGINGS = ITEMS.register("iron_man_mark_5_leggings",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1)).mark("mark_5"));
    public static final RegistryObject<IronManArmourItem> IRON_MAN_MARK_5_BOOTS = ITEMS.register("iron_man_mark_5_boots",
            () -> new IronManArmourItemBuilder(SuperheroArmourMaterials.IRON_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1)).mark("mark_5"));
}
