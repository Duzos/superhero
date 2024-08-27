package mc.duzo.timeless.suit.ironman.mk5;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.power.PowerList;
import mc.duzo.timeless.power.PowerRegistry;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.render.SuitModel;
import mc.duzo.timeless.suit.ironman.IronManSuit;
import mc.duzo.timeless.suit.ironman.mk5.client.MarkFiveModel;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public class MarkFiveSuit extends IronManSuit {
    private final PowerList powers;

    public MarkFiveSuit() {
        super("mark_five");

        this.powers = PowerList.of(PowerRegistry.TO_CASE, PowerRegistry.FLIGHT);
    }

    @Override
    public SuitSet getSet() {
        return SetRegistry.MARK_FIVE;
    }

    @Override
    public PowerList getPowers() {
        return this.powers;
    }

    @Environment(EnvType.CLIENT)
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
