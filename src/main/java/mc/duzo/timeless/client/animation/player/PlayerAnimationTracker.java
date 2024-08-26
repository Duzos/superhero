package mc.duzo.timeless.client.animation.player;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.network.AbstractClientPlayerEntity;

import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;

public class PlayerAnimationTracker {
    private static final HashMap<UUID, PlayerAnimationHolder> ANIMATIONS = new HashMap<>();

    public static PlayerAnimationHolder getAnimation(AbstractClientPlayerEntity entity) {
        UUID uuid = entity.getUuid();

        PlayerAnimationHolder anim = ANIMATIONS.get(uuid);

        if (anim != null && anim.isFinished(entity)) {
            ANIMATIONS.remove(uuid);
        }

        return anim;
    }
    public static void addAnimation(UUID uuid, PlayerAnimationHolder animation) {
        ANIMATIONS.put(uuid, animation);
    }
    public static void clearAnimation(UUID uuid) {
        ANIMATIONS.remove(uuid);
    }
}
