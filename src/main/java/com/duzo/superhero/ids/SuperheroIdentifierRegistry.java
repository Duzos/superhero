package com.duzo.superhero.ids;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.impls.IdentifierBuilder;
import com.duzo.superhero.ids.impls.IronManIdentifier;
import com.duzo.superhero.util.batman.BatManUtil;
import com.duzo.superhero.util.flash.FlashUtil;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static com.duzo.superhero.items.SuperheroItems.registerSuperheroSet;

public class SuperheroIdentifierRegistry {
    public static final DeferredRegister<AbstractIdentifier> IDS = DeferredRegister.create(new ResourceLocation(Superhero.MODID,"ids"), Superhero.MODID);

    // Register IDs here

    // Spiderman
    public static final RegistryObject<AbstractIdentifier> MILES = register("miles", () -> new IdentifierBuilder("miles")
            .itemPrefix("Miles")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.INVISIBILITY)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> MILES_CLOTHED = register("miles_clothed", () -> new IdentifierBuilder("miles_clothed")
            .itemPrefix("Miles")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.INVISIBILITY)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> GWEN = register("gwen", () -> new IdentifierBuilder("gwen")
            .itemPrefix("SpiderWoman")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> AMAZING_SPIDER_MAN = register("amazing_spider_man", () -> new IdentifierBuilder("amazing_spider_man")
            .itemPrefix("SpiderMan")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> IRON_SPIDER = register("iron_spider", () -> new IdentifierBuilder("iron_spider")
            .itemPrefix("SpiderMan")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.NANOTECH)
            .slim(true)
    );

    // Flash
    public static final RegistryObject<AbstractIdentifier> FLASH = register("flash", () -> new IdentifierBuilder("flash")
            .itemPrefix("Flash")
            .capabilities(FlashUtil.DEFAULT_CAPABILITIES)
    );

    // Batman
    public static final RegistryObject<AbstractIdentifier> BATMAN_VS_SUPERMAN = register("batman_vs_superman", () -> new IdentifierBuilder("batman_vs_superman")
            .itemPrefix("BatMan")
            .capabilities(BatManUtil.DEFAULT_CAPABILITIES)
    );


    // Iron-Man

    // The order the properties are set matters here as it returns IdentifierBuilder for the other things, @TODO is there a way to return the right thing?
    public static final RegistryObject<AbstractIdentifier> IRONMAN_MARK_1 = register("ironman_mark_1", () -> new IronManIdentifier("ironman_mark_1")
            .mark(1)
            .vertical(0.005)
            .blast(1d)
            .itemPrefix("Iron-Man")
            .capabilities(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.ICES_OVER,SuperheroCapability.BINDING,SuperheroCapability.IRON_MAN_FLIGHT)
    );
    public static final RegistryObject<AbstractIdentifier> IRONMAN_MARK_2 = register("ironman_mark_2", () -> new IronManIdentifier("ironman_mark_2")
            .mark(2)
            .vertical(0.01)
            .blast(1.25d)
            .itemPrefix("Iron-Man")
            .capabilities(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.ICES_OVER,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.BLAST_OFF,SuperheroCapability.BINDING,SuperheroCapability.IRON_MAN_FLIGHT)
    );
    public static final RegistryObject<AbstractIdentifier> IRONMAN_MARK_5 = register("ironman_mark_5", () -> new IronManIdentifier("ironman_mark_5")
            .mark(5)
            .vertical(0.015)
            .blast(1.5d)
            .itemPrefix("Iron-Man")
            .capabilities(SuperheroCapability.SUITCASE,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT)
    );
    public static final RegistryObject<AbstractIdentifier> IRONMAN_MARK_7 = register("ironman_mark_7", () -> new IronManIdentifier("ironman_mark_7")
            .mark(7)
            .vertical(0.0175)
            .blast(2d)
            .itemPrefix("Iron-Man")
            .capabilities(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.BRACELET_LOCATING,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT)
    );
    public static final RegistryObject<AbstractIdentifier> IRONMAN_MARK_9 = register("ironman_mark_9", () -> new IronManIdentifier("ironman_mark_9")
            .mark(9)
            .vertical(0.02)
            .blast(2.5d)
            .itemPrefix("Iron-Man")
            .capabilities(SuperheroCapability.MASK_TOGGLE, SuperheroCapability.NANOTECH,SuperheroCapability.JARVIS,SuperheroCapability.NIGHT_VISION_HELMET_ONLY,SuperheroCapability.IRON_MAN_WEAPONS,SuperheroCapability.BLAST_OFF,SuperheroCapability.IRON_MAN_FLIGHT)
    );


    public static Supplier<IForgeRegistry<AbstractIdentifier>> IDS_REGISTRY = IDS.makeRegistry(() -> new RegistryBuilder<AbstractIdentifier>().setMaxID(Integer.MAX_VALUE - 1));

    // Utils
    public static AbstractIdentifier fromID(String location) {
        return fromID(new ResourceLocation(location));
    }
    public static AbstractIdentifier fromID(ResourceLocation location) {
        AbstractIdentifier val = IDS_REGISTRY.get().getValue(location);

        if (val != null) {
            return val;
        }
        // DEFAULT VALUE
        return MILES.get();
    }
    public static ResourceLocation getIDLocation(AbstractIdentifier id) {
        @Nullable ResourceLocation location = IDS_REGISTRY.get().getKey(id);
        return location;
    }
    public static <T extends AbstractIdentifier> RegistryObject<T> register(String name,Supplier<T> supplier) {
        RegistryObject<T> id = IDS.register(name,supplier);
        if (supplier.get().autoAdd()) {
            registerSuperheroSet(supplier.get());
        }
        return id;
    }
}
