package mc.duzo.timeless.suit.ironman;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.client.ClientSuit;

public abstract class IronManSuit extends Suit {
    private final Identifier id;

    protected IronManSuit(Identifier id) {
        this.id = id;
    }
    protected IronManSuit(String mark, String modid) {
        this(new Identifier(modid, "iron_man_" + mark));
    }

    /**
     * For Timeless heroes ONLY
     * Addon mods should use other constructor
     */
    protected IronManSuit(String mark) {
        this(mark, Timeless.MOD_ID);
    }

    @Override
    public boolean isBinding() {
        return true;
    }

    @Override
    public Identifier id() {
        return this.id;
    }

    @Override
    protected ClientSuit createClient() {
        return new ClientSuit(this) {
            @Override
            public boolean hasRenderer() {
                return true;
            }
        };
    }
}
