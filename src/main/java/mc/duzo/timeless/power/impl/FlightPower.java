package mc.duzo.timeless.power.impl;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.suit.item.SuitItem;
import mc.duzo.timeless.util.ServerKeybind;

public class FlightPower extends Power {
    private final Identifier id;

    public FlightPower() {
        this.id = new Identifier(Timeless.MOD_ID, "flight");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;

        boolean hasFlight = data.getBoolean("FlightEnabled");
        data.putBoolean("FlightEnabled", !hasFlight);

        return true;
    }

    @Override
    public void tick(ServerPlayerEntity player) {
        player.fallDistance = 0;

        if (!ServerKeybind.isJumping(player)) return;
        if (player.isOnGround()) return;
        if (!(hasFlight(player))) return;

        Vec3d change = ServerKeybind.isMovingForward(player) ? getVelocity(player) : player.getVelocity().add(0, 0.1, 0);

        player.setVelocity(change);
        player.velocityModified = true;
    }

    private Vec3d getVelocity(ServerPlayerEntity player) {
        Vec3d change = new Vec3d(0, 0, 0);

        Vec3d look = player.getRotationVector().rotateZ((float) Math.toRadians(0));
        change = change.add(look.x / 4, 0.1, look.z / 4);

        look = player.getRotationVector();
        change = change.add(look);

        return change;
    }
    private boolean hasFlight(ServerPlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;

        return data.getBoolean("FlightEnabled");
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
