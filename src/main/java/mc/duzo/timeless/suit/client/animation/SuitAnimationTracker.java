package mc.duzo.timeless.suit.client.animation;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.network.AbstractClientPlayerEntity;

public class SuitAnimationTracker {
    private static final HashMap<UUID, SuitAnimationHolder> ANIMATIONS = new HashMap<>();

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
}
