package mc.duzo.timeless.suit;

import net.minecraft.nbt.NbtCompound;

public interface Serializable {
    NbtCompound serialize();
    void deserialize(NbtCompound data);
}
