package mc.duzo.timeless.suit;

import mc.duzo.timeless.Timeless;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class SetRegistry {
	public static final SimpleRegistry<SuitSet> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<SuitSet>ofRegistry(new Identifier(Timeless.MOD_ID, "suit_set"))).buildAndRegister();

	public static SuitSet register(SuitSet suit) {
		return Registry.register(REGISTRY, suit.id(), suit);
	}

	public static void init() {

	}
}
