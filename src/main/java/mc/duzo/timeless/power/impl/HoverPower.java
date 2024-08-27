package mc.duzo.timeless.power.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.suit.item.SuitItem;

public class HoverPower extends Power {
    private final Identifier id;

    public HoverPower() {
        this.id = new Identifier(Timeless.MOD_ID, "hover");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;

        boolean hasHover = data.getBoolean("HoverEnabled");
        data.putBoolean("HoverEnabled", !hasHover);

        return true;
    }

    @Override
    public void tick(ServerPlayerEntity player) {

    }

    public static boolean hasHover(PlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;

        return data.getBoolean("HoverEnabled");
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
