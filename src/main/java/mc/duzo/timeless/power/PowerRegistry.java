package mc.duzo.timeless.power;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.ironman.mk5.MarkFiveCase;

public class PowerRegistry {
    public static final SimpleRegistry<Power> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<Power>ofRegistry(new Identifier(Timeless.MOD_ID, "power"))).buildAndRegister();

    public static <T extends Power> T register(T entry) {
        return Registry.register(REGISTRY, entry.id(), entry);
    }

    public static Power TO_CASE = Power.create(new Identifier(Timeless.MOD_ID, "to_case"), player -> MarkFiveCase.toCase(player, false)).register();

    public static void init() {}
}
