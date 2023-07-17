package com.duzo.superhero.ids;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapability;
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
    public static final RegistryObject<AbstractIdentifier> MILES = IDS.register("miles",() -> new SpidermanIdentifier("miles", new SuperheroCapabilities(SuperheroCapability.INVISIBILITY)));

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
