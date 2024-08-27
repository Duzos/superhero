package mc.duzo.timeless.power.impl;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.util.ServerKeybind;

public class FlightPower extends Power {
    private final Identifier id;

    public FlightPower() {
        this.id = new Identifier(Timeless.MOD_ID, "flight");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        return false;
    }

    @Override
    public void tick(ServerPlayerEntity player) {
        if (!ServerKeybind.isJumping(player)) return;
        if (player.isOnGround()) return;

        player.fallDistance = 0;

        Vec3d change = ServerKeybind.isMovingForward(player) ? getVelocity(player) : player.getVelocity().add(0, 0.1, 0);

        System.out.println(change);

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

    @Override
    public Identifier id() {
        return this.id;
    }
}
