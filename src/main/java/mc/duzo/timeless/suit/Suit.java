package mc.duzo.timeless.suit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public abstract class Suit implements Identifiable {
    public abstract boolean isBinding();
    public abstract SuitSet getSet();

    @Environment(EnvType.CLIENT)
    public ClientSuit toClient() {
        ClientSuit found = ClientSuitRegistry.REGISTRY.get(this.id());
        if (found != null) return found;

        return ClientSuitRegistry.register(this.createClient()); // todo registry may be frozen
    }

    /**
     * creates the client-side version of this suit
     * this MUST have the annotation @Environment(EnvType.CLIENT) or your game will crash
     */
    @Environment(EnvType.CLIENT)
    protected abstract ClientSuit createClient();
}
