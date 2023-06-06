package com.duzo.superhero.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ThrustersSoundInstance extends AbstractTickableSoundInstance {
    private static final float VOLUME_MIN = 0.0F;
    private static final float VOLUME_MAX = 0.7F;
    private static final float PITCH_MIN = 0.0F;
    private static final float PITCH_MAX = 1.0F;
    private static final float PITCH_DELTA = 0.0025F;
    private final Player player;
    private float pitch = 0.0F;

    public ThrustersSoundInstance(Player player) {
        super(SoundEvents.FIRE_AMBIENT, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.x = (double) ((float) player.getX());
        this.y = (double) ((float) player.getY());
        this.z = (double) ((float) player.getZ());
    }

    public boolean canPlaySound() {
        return !this.player.isSilent();
    }

    public boolean canStartSilent() {
        return true;
    }

    public void tick() {
        if (this.player.isOnGround()) {
            this.stop();
        } else {
            this.x = (double) ((float) this.player.getX());
            this.y = (double) ((float) this.player.getY());
            this.z = (double) ((float) this.player.getZ());
            float f = (float) this.player.getDeltaMovement().horizontalDistance();
            if (f >= 0.01F) {
                this.pitch = Mth.clamp(this.pitch + 0.0025F, 0.0F, 1.0F);
                this.volume = Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0.0F, 0.7F);
            } else {
                this.pitch = 0.0F;
                this.volume = 0.0F;
            }

        }
    }
}