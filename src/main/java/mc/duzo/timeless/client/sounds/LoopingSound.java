package mc.duzo.timeless.client.sounds;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public abstract class LoopingSound extends MovingSoundInstance {
    public LoopingSound(SoundEvent soundEvent, SoundCategory soundCategory) {
        super(soundEvent, soundCategory, Random.create());
        this.repeat = true;
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public void tick() {
    }

    public void setPosition(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public BlockPos getPosition() {
        return new BlockPos((int) this.x, (int) this.y, (int) this.z);
    }
}