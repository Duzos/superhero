package mc.duzo.timeless.client.sounds.thruster;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.client.network.AbstractClientPlayerEntity;

public class ThrusterSoundHandler {
    private final HashMap<UUID, ThrusterSound> sounds;

    public ThrusterSoundHandler() {
        this.sounds = new HashMap<>();
    }

    public ThrusterSound get(AbstractClientPlayerEntity player) {
        return this.sounds.computeIfAbsent(
                player.getUuid(),
                uuid -> new ThrusterSound(player)
        );
    }

    public void onStart(AbstractClientPlayerEntity user) {
        this.get(user).onStart();
    }
    public void onFinish(AbstractClientPlayerEntity user) {
        this.get(user).onFinish();
    }

    public void handle(AbstractClientPlayerEntity player, boolean isFlying) {
        if (isFlying) this.onStart(player);
        else this.onFinish(player);
    }
}