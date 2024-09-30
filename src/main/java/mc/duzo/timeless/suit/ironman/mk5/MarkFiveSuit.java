package mc.duzo.timeless.suit.ironman.mk5;

import java.util.Optional;
import java.util.function.Supplier;

import mc.duzo.animation.generic.AnimationInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
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

        this.powers = PowerList.of(PowerRegistry.TO_CASE, PowerRegistry.FLIGHT, PowerRegistry.HOVER, PowerRegistry.MASK_TOGGLE, PowerRegistry.JARVIS);
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
        AnimationInfo info = new AnimationInfo(AnimationInfo.RenderType.TORSO_HEAD, null, AnimationInfo.Movement.ALLOW, null);

        return new ClientSuit(this) {
            @Override
            public Supplier<SuitModel> model() {
                return MarkFiveModel::new;
            }

            @Override
            public Optional<Identifier> emission() {
                return Optional.of(createEmission(this.texture()));
            }

            @Override
            public AnimationInfo getAnimationInfo(LivingEntity entity) {
                if (!(getSet().isWearing(entity))) return null;
                return info;
            }
        };
    }

    @Override
    public int getVerticalFlightModifier(boolean isSprinting) {
        return (isSprinting) ? 15 : 10;
    }

    @Override
    public int getHorizontalFlightModifier(boolean isSprinting) {
        return (isSprinting) ? 15 : 5;
    }

    @Override
    public Identifier getMaskAnimation(boolean isOpening) {
        return new Identifier(Timeless.MOD_ID, "ironman_mk5_mask_" + (isOpening ? "open" : "close"));
    }
}
