package mc.duzo.timeless.suit.ironman.mk3;

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
import mc.duzo.timeless.suit.ironman.mk3.client.MarkThreeModel;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public class MarkThreeSuit extends IronManSuit {
    private final PowerList powers;

    public MarkThreeSuit() {
        super("mark_three");

        this.powers = PowerList.of(PowerRegistry.JARVIS, PowerRegistry.FLIGHT, PowerRegistry.HOVER, PowerRegistry.MASK_TOGGLE );
    }

    @Override
    public SuitSet getSet() {
        return SetRegistry.MARK_THREE;
    }

    @Override
    public PowerList getPowers() {
        return this.powers;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ClientSuit createClient() {
        SuitModel model = new MarkThreeModel();
        AnimationInfo info = new AnimationInfo(AnimationInfo.RenderType.TORSO_HEAD, null, AnimationInfo.Movement.ALLOW, null);

        return new ClientSuit(this) {
            @Override
            public SuitModel model() {
                return model;
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
        return (isSprinting) ? 13 : 9;
    }

    @Override
    public int getHorizontalFlightModifier(boolean isSprinting) {
        return (isSprinting) ? 13 : 5;
    }

    @Override
    public Identifier getMaskAnimation(boolean isOpening) {
        return new Identifier(Timeless.MOD_ID, "ironman_generic_mask_" + (isOpening ? "open" : "close"));
    }
}
