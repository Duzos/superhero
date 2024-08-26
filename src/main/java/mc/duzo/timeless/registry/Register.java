package mc.duzo.timeless.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.SuitRegistry;
import mc.duzo.timeless.suit.set.SetRegistry;

public class Register {
    public static class Items {
        public static <T extends Item> T register(Identifier id, T entry) {
            return Registry.register(Registries.ITEM, id, entry);
        }
    }

    public static void init() {
        SetRegistry.init();
        SuitRegistry.init();
    }

    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, new Identifier(Timeless.MOD_ID, name), entry);
    }

    public static <T extends Block> T registerBlockAndItem(String name, T entry) {
        T output = Register.register(Registries.BLOCK, name, entry);
        Registry.register(Registries.ITEM, new Identifier(Timeless.MOD_ID, name), new BlockItem(output, new FabricItemSettings()));
        return output;
    }
}
