package mc.duzo.timeless.power.impl;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.suit.ironman.IronManSuit;
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

        if (player.isOnGround()) return;
        if (!(hasFlight(player))) return;

        Vec3d change = getVelocity(player);

        player.setVelocity(change);
        player.velocityModified = true;

        this.createParticles(player);
    }

    private Vec3d getVelocity(ServerPlayerEntity player) {
        Vec3d change = new Vec3d(0, 0, 0);

        ServerKeybind.Keymap map = ServerKeybind.get(player);
        change = change.add(getVelocityFor(player, map.isMovingForward(), 0));
        change = change.add(getVelocityFor(player, map.isMovingBackward(), 180));
        change = change.add(getVelocityFor(player, map.isMovingRight(), 270));
        change = change.add(getVelocityFor(player, map.isMovingLeft(), 90));
        change = change.add(getVerticalVelocity(player));

        Vec3d current = player.getVelocity();
        change = change.add(current.x, 0, current.z);

        return change;
    }
    private Vec3d getVelocityFor(ServerPlayerEntity player, boolean shouldRun, double angle) {
        if (!shouldRun) return Vec3d.ZERO;

        Vec3d change = new Vec3d(0, 0, 0);

        Vec3d look = player.getRotationVector().rotateY((float) Math.toRadians(angle));

        double multiplier = (getSuit(player).getHorizontalFlightModifier(player.isSprinting()) / 100f);
        change = change.add(look.x * multiplier, 0, look.z * multiplier);

        return change;
    }
    private Vec3d getVerticalVelocity(ServerPlayerEntity player) {
        Vec3d change = new Vec3d(0, 0, 0);

        if (ServerKeybind.get(player).isJumping()) {
            return change.add(0, (getSuit(player).getVerticalFlightModifier(player.isSprinting()) / 100f), 0).add(0, player.getVelocity().y, 0);
        }

        double yVelocity = player.getVelocity().y;

        if (HoverPower.hasHover(player)) {
            yVelocity = Math.max(yVelocity, 0.08); // todo seems to flicker movement downwards
            if (player.isSneaking()) yVelocity = -0.25;
        }

        change = change.add(0, yVelocity, 0);

        return change;
    }

    public static boolean hasFlight(PlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;

        return data.getBoolean("FlightEnabled");
    }

    public static IronManSuit getSuit(PlayerEntity player) { // todo not assume IronManSuit
        if (!(player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SuitItem item)) return null;
        if (!(item.getSuit() instanceof IronManSuit suit)) return null;

        return suit;
    }

    private void createParticles(ServerPlayerEntity player) {
        // taken from the old forge archive, will need reworking
        ServerWorld world = player.getServerWorld();
        Random random = world.getRandom();

        int i = MathHelper.clamp(0, 0, 64);
        double f2 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
        double f3 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
        double f4 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
        double f5 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 1F);
        float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 6F);

        world.spawnParticles(ParticleTypes.SMOKE, player.getX() + f2, player.getY() + (double) f6, player.getZ() + f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMOKE, player.getX() - f2, player.getY() + (double) f6, player.getZ() - f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMOKE, player.getX() + f4, player.getY() + (double) f7, player.getZ() + f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMOKE, player.getX() - f4, player.getY() + (double) f7, player.getZ() - f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMALL_FLAME, player.getX() + f2, player.getY() + (double) f6, player.getZ() + f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMALL_FLAME, player.getX() - f2, player.getY() + (double) f6, player.getZ() - f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMALL_FLAME, player.getX() + f4, player.getY() + (double) f7, player.getZ() + f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
        world.spawnParticles(ParticleTypes.SMALL_FLAME, player.getX() - f4, player.getY() + (double) f7, player.getZ() - f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
