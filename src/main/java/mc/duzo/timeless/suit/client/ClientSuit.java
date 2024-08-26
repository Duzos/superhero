package mc.duzo.timeless.suit.client;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;
import mc.duzo.timeless.suit.client.render.SuitModel;

@Environment(EnvType.CLIENT)
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

    /**
     * @return whether this suit has a custom model and will use the timeless renderer
     */
    public boolean hasModel() {
        return this.model() != null;
    }
    public SuitModel model() {
        return null;
    }
    public Identifier texture() {
        return new Identifier(this.id().getNamespace(), "textures/suit/" + this.id().getPath() + ".png");
    }
    public Optional<Identifier> emission() {
        return Optional.empty();
    }
    public static Identifier createEmission(Identifier texture) {
        int index = texture.getPath().lastIndexOf(".");
        return new Identifier(texture.getNamespace(), texture.getPath().substring(0, index) + "_emission.png");
    }
}
