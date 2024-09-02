package mc.duzo.timeless.suit.client.animation;

import java.util.HashMap;
import java.util.UUID;

import mc.duzo.animation.generic.AnimationTracker;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.registry.Register;

public class SuitAnimationTracker extends AnimationTracker<SuitAnimationHolder> {
    private static final HashMap<UUID, SuitAnimationHolder> ANIMATIONS = new HashMap<>();

    public SuitAnimationTracker() {
        super(new Identifier(Timeless.MOD_ID, "suit"));
    }

    public static SuitAnimationHolder getAnimation(AbstractClientPlayerEntity entity) {
        UUID uuid = entity.getUuid();

        SuitAnimationHolder anim = ANIMATIONS.get(uuid);

        if (anim != null && anim.isFinished(entity)) {
            ANIMATIONS.remove(uuid);
        }

        return anim;
    }
    public static void addAnimation(UUID uuid, SuitAnimationHolder animation) {
        ANIMATIONS.put(uuid, animation);
    }
    public static void clearAnimation(UUID uuid) {
        ANIMATIONS.remove(uuid);
    }

    public static SuitAnimationTracker getInstance() {
        return Register.Trackers.SUIT;
    }
}
