package mc.duzo.timeless.suit;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;

public class SuitRegistry {
    public static final SimpleRegistry<Suit> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<Suit>ofRegistry(new Identifier(Timeless.MOD_ID, "suit"))).buildAndRegister();

    public static <T extends Suit> T register(T entry) {
        return Registry.register(REGISTRY, entry.id(), entry);
    }

    public static void init() {}
}
