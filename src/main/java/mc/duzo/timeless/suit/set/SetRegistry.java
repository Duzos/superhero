package mc.duzo.timeless.suit.set;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.ironman.IronManSuitItem;
import mc.duzo.timeless.suit.ironman.mk2.MarkTwoSuit;
import mc.duzo.timeless.suit.ironman.mk5.MarkFiveSuit;

public class SetRegistry {
    public static final SimpleRegistry<SuitSet> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<SuitSet>ofRegistry(new Identifier(Timeless.MOD_ID, "suit_set"))).buildAndRegister();

    public static <T extends SuitSet> T register(T suit) {
        return Registry.register(REGISTRY, suit.id(), suit);
    }

    public static SuitSet MARK_FIVE;
    public static SuitSet MARK_TWO;

    public static void init() {
        MARK_FIVE = register(new RegisteringSuitSet(new MarkFiveSuit(), IronManSuitItem::new));
        MARK_TWO = register(new RegisteringSuitSet(new MarkTwoSuit(), IronManSuitItem::new));
    }
}
