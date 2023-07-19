package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.items.batman.GrapplingHookWeaponItem;
import com.duzo.superhero.items.spiderman.MilesHoodieItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.duzo.superhero.items.SuperheroArmourMaterials.IRON_MAN;

public class SuperheroItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Superhero.MODID);

    public static final RegistryObject<SuperheroNanotechItem> NANOTECH = ITEMS.register("nanotech",
            () -> new SuperheroNanotechItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
//    public static final RegistryObject<IronManEdithGlasses> EDITH_GLASSES = ITEMS.register("edith_glasses",
//            () -> new IronManEdithGlasses(IRON_MAN,ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));

    public static final RegistryObject<MilesHoodieItem> MILES_HOODIE = ITEMS.register("miles_hoodie",
            () -> new MilesHoodieItem(ArmorMaterials.LEATHER,ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PALLADIUM_INGOT = ITEMS.register("palladium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PALLADIUM = ITEMS.register("raw_palladium",
            () -> new Item(new Item.Properties()));


    // Batman Weapons
    public static final RegistryObject<GrapplingHookWeaponItem> GRAPPLING_HOOK = ITEMS.register("grappling_hook",
            () -> new GrapplingHookWeaponItem(new Item.Properties().stacksTo(1)));


    // @TODO smelly loqor and his custom smelly model for iron man meaning this still exists
//    public static void registerIronManSet(IronManIdentifier mark) {
//        String name = mark.getSerializedName();
//
//        ITEMS.register(name + "_helmet",
//                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1),mark));
//        ITEMS.register(name + "_chestplate",
//                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1),mark));
//        ITEMS.register(name + "_leggings",
//                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1),mark));
//        ITEMS.register(name + "_boots",
//                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1),mark));
//    }

    public static void registerSuperheroSet(AbstractIdentifier id) {
        String name = id.getSerializedName();

        ITEMS.register(name + "_helmet",
                () -> new SuperheroArmourItem(IRON_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_chestplate",
                () -> new SuperheroArmourItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_leggings",
                () -> new SuperheroArmourItem(IRON_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_boots",
                () -> new SuperheroArmourItem(IRON_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1),id));
    }
}
