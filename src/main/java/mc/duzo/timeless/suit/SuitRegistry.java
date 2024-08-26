package mc.duzo.timeless.suit;

import mc.duzo.timeless.Timeless;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class SuitRegistry {
	public static final SimpleRegistry<Suit> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<Suit>ofRegistry(new Identifier(Timeless.MOD_ID, "suit"))).buildAndRegister();

	public static Suit register(Suit suit) {
		return Registry.register(REGISTRY, suit.id(), suit);
	}

	public static void init() {

	}
}
