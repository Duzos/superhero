package mc.duzo.timeless.suit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import mc.duzo.timeless.datagen.provider.lang.Translatable;
import mc.duzo.timeless.power.PowerList;
import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public abstract class Suit implements Identifiable, Translatable {
    public abstract boolean isBinding();
    public abstract SuitSet getSet();
    public abstract PowerList getPowers();

    @Override
    public String getTranslationKey() {
        return this.id().getNamespace() + ".suit." + this.id().getPath();
    }

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
