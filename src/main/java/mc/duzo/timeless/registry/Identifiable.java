package mc.duzo.timeless.registry;

import net.minecraft.util.Identifier;

@Deprecated(forRemoval = true) // todo - use animation library identfiable
public interface Identifiable {
    Identifier id();
}