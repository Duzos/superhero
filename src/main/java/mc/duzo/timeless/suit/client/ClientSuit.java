package mc.duzo.timeless.suit.client;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;

public abstract class ClientSuit implements Identifiable {
    private final Suit parent;
    protected ClientSuit(Suit suit) {
        this.parent = suit;
    }
    protected ClientSuit(Identifier suit) {
        this(SuitRegistry.REGISTRY.get(suit));
    }

    public Suit toServer() {
        return this.parent;
    }

    @Override
    public Identifier id() {
        return this.toServer().id();
    }

    public abstract boolean hasRenderer();
}
