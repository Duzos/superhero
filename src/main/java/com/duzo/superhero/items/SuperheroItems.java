package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.batman.BatManArmourItem;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import com.duzo.superhero.items.ironman.IronManNanotechItem;
import com.duzo.superhero.items.spiderman.MilesHoodieItem;
import com.duzo.superhero.items.spiderman.SpiderManArmourItem;
import com.duzo.superhero.items.spiderman.SpiderManNanotechItem;
import com.duzo.superhero.util.batman.BatManIdentifier;
import com.duzo.superhero.util.ironman.IronManMark;
import com.duzo.superhero.util.spiderman.SpiderManIdentifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.duzo.superhero.items.SuperheroArmourMaterials.*;

public class SuperheroItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Superhero.MODID);

    public static final RegistryObject<IronManNanotechItem> IRON_MAN_NANOTECH = ITEMS.register("iron_man_nanotech",
            () -> new IronManNanotechItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<IronManEdithGlasses> EDITH_GLASSES = ITEMS.register("edith_glasses",
            () -> new IronManEdithGlasses(IRON_MAN,ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));

    public static final RegistryObject<MilesHoodieItem> MILES_HOODIE = ITEMS.register("miles_hoodie",
            () -> new MilesHoodieItem(ArmorMaterials.LEATHER,ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<SpiderManNanotechItem> SPIDERMAN_NANOTECH = ITEMS.register("spiderman_nanotech",
            () -> new SpiderManNanotechItem(SPIDER_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PALLADIUM_INGOT = ITEMS.register("palladium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PALLADIUM = ITEMS.register("raw_palladium",
            () -> new Item(new Item.Properties()));

    public static void init() {
        for (IronManMark mark: IronManMark.values()) {
            if (!mark.autoAdd()) continue;

            registerIronManSet(mark);
        }

        for (SpiderManIdentifier id : SpiderManIdentifier.values()) {
            if (!id.autoAdd()) continue;

            registerSpiderManSet(id);
        }

        for (BatManIdentifier id : BatManIdentifier.values()) {
            if (!id.autoAdd()) continue;

            registerBatManSet(id);
        }
    }

    public static void registerIronManSet(IronManMark mark) {
        String name = mark.getSerializedName();

        ITEMS.register(name + "_helmet",
                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1),mark));
        ITEMS.register(name + "_chestplate",
                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1),mark));
        ITEMS.register(name + "_leggings",
                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1),mark));
        ITEMS.register(name + "_boots",
                () -> new IronManArmourItem(IRON_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1),mark));
    }

    public static void registerSpiderManSet(SpiderManIdentifier id) {
        String name = id.getSerializedName();

        ITEMS.register(name + "_helmet",
                () -> new SpiderManArmourItem(SPIDER_MAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_chestplate",
                () -> new SpiderManArmourItem(SPIDER_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_leggings",
                () -> new SpiderManArmourItem(SPIDER_MAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_boots",
                () -> new SpiderManArmourItem(SPIDER_MAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1),id));
    }

    public static void registerBatManSet(BatManIdentifier id) {
        String name = id.getSerializedName();

        ITEMS.register(name + "_helmet",
                () -> new BatManArmourItem(BATMAN, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_chestplate",
                () -> new BatManArmourItem(BATMAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_leggings",
                () -> new BatManArmourItem(BATMAN, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1),id));
        ITEMS.register(name + "_boots",
                () -> new BatManArmourItem(BATMAN, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1),id));
    }
}
