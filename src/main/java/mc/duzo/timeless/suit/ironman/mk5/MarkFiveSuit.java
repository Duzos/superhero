package mc.duzo.timeless.suit.ironman.mk5;

import java.util.Optional;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.render.SuitModel;
import mc.duzo.timeless.suit.ironman.IronManSuit;
import mc.duzo.timeless.suit.ironman.mk5.client.MarkFiveModel;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public class MarkFiveSuit extends IronManSuit {
    public MarkFiveSuit() {
        super("mk_five");
    }

    @Override
    public SuitSet getSet() {
        return SetRegistry.MARK_FIVE;
    }

    @Override
    protected ClientSuit createClient() {
        SuitModel model = new MarkFiveModel();

        return new ClientSuit(this) {
            @Override
            public SuitModel model() {
                return model;
            }

            @Override
            public Optional<Identifier> emission() {
                return Optional.of(createEmission(this.texture()));
            }
        };
    }
}
