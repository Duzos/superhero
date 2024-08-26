package mc.duzo.timeless.suit.client;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;

public class ClientSuitRegistry {
    public static final SimpleRegistry<ClientSuit> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<ClientSuit>ofRegistry(new Identifier(Timeless.MOD_ID, "suit_client"))).buildAndRegister();

    public static <T extends ClientSuit> T register(T entry) {
        return Registry.register(REGISTRY, entry.id(), entry);
    }

    public static void init() {
        for (Suit suit : SuitRegistry.REGISTRY) {
            suit.toClient();
        }
    }
}
