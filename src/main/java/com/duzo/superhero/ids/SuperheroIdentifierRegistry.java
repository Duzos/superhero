package com.duzo.superhero.ids;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.impls.IdentifierBuilder;
import com.duzo.superhero.util.flash.FlashUtil;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SuperheroIdentifierRegistry {
    public static final DeferredRegister<AbstractIdentifier> IDS = DeferredRegister.create(new ResourceLocation(Superhero.MODID,"ids"), Superhero.MODID);

    // Register IDs here

    // Spiderman
    public static final RegistryObject<AbstractIdentifier> MILES = IDS.register("miles", () -> new IdentifierBuilder("miles")
            .itemPrefix("Miles")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.INVISIBILITY)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> MILES_CLOTHED = IDS.register("miles_clothed", () -> new IdentifierBuilder("miles_clothed")
            .itemPrefix("Miles")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.INVISIBILITY)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> GWEN = IDS.register("gwen", () -> new IdentifierBuilder("gwen")
            .itemPrefix("SpiderWoman")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> AMAZING_SPIDER_MAN = IDS.register("amazing_spider_man", () -> new IdentifierBuilder("amazing_spider_man")
            .itemPrefix("SpiderMan")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .slim(true)
    );
    public static final RegistryObject<AbstractIdentifier> IRON_SPIDER = IDS.register("iron_spider", () -> new IdentifierBuilder("iron_spider")
            .itemPrefix("SpiderMan")
            .capabilities(SpiderManUtil.DEFAULT_CAPABILITIES)
            .capabilities(SuperheroCapability.NANOTECH)
            .slim(true)
    );

    // Flash
    public static final RegistryObject<AbstractIdentifier> FLASH = IDS.register("flash", () -> new IdentifierBuilder("flash")
            .itemPrefix("Flash")
            .capabilities(FlashUtil.DEFAULT_CAPABILITIES)
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
}
