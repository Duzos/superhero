package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.items.ironman.IronManEdithGlasses;
import com.duzo.superhero.items.ironman.IronManNanotechItem;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.duzo.superhero.items.SuperheroArmourMaterials.IRON_MAN;

public class SuperheroItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Superhero.MODID);

    public static final RegistryObject<IronManNanotechItem> NANOTECH = ITEMS.register("nanotech",
            () -> new IronManNanotechItem(IRON_MAN, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<IronManEdithGlasses> EDITH_GLASSES = ITEMS.register("edith_glasses",
            () -> new IronManEdithGlasses(IRON_MAN,ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PALLADIUM_INGOT = ITEMS.register("palladium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PALLADIUM = ITEMS.register("raw_palladium",
            () -> new Item(new Item.Properties()));

    public static void init() {
        for (IronManMark mark: IronManMark.values()) {
            if (!mark.autoAdd()) continue;

            registerIronManSet(mark);
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
}
