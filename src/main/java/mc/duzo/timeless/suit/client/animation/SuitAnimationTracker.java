package mc.duzo.timeless.suit.client.animation;

import mc.duzo.animation.generic.AnimationTracker;

import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.registry.Register;

public class SuitAnimationTracker extends AnimationTracker<SuitAnimationHolder> {
    public SuitAnimationTracker() {
        super(new Identifier(Timeless.MOD_ID, "suit"));
    }


    public static SuitAnimationTracker getInstance() {
        return Register.Trackers.SUIT;
    }
}
