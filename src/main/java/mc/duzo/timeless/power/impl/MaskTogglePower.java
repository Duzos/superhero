package mc.duzo.timeless.power.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.network.Network;
import mc.duzo.timeless.network.s2c.MarkFiveMaskS2CPacket;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.suit.item.SuitItem;

public class MaskTogglePower extends Power {
    private final Identifier id;

    public MaskTogglePower() {
        this.id = new Identifier(Timeless.MOD_ID, "mask_toggle");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        setMask(player, !hasMask(player));
        Network.toTracking(new MarkFiveMaskS2CPacket(player.getUuid(), hasMask(player)), player);

        return true;
    }
    private static void setMask(ServerPlayerEntity player, boolean val) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return;

        data.putBoolean("MaskEnabled", val);
    }
    public static boolean hasMask(PlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;
        if (!(data.contains("MaskEnabled"))) return true;

        return data.getBoolean("MaskEnabled");
    }

    @Override
    public void tick(ServerPlayerEntity player) {

    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
