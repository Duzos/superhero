package mc.duzo.timeless.network;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class TimelessServerNetworking {
    private static MinecraftServer SERVER;

    public static void init() {
        ServerWorldEvents.UNLOAD.register(((server, world) -> SERVER = server));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> SERVER = null);
    }

    public static MinecraftServer server() {
        return SERVER;
    }

    public static void send(FabricPacket packet, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, packet);
    }

    public static void sendToAll(FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.all(server())) {
            send(packet, player);
        }
    }
    public static void sendToTracking(BlockEntity target, FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.tracking(target)) {
            send(packet, player);
        }
    }
    public static void sendToTracking(Entity target, FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.tracking(target)) {
            send(packet, player);
        }
    }
    public static void sendToWorld(ServerWorld target, FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.world(target)) {
            send(packet, player);
        }
    }
    public static void sendToNearby(ServerWorld world, Vec3d target, double radius, FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.around(world, target, radius)) {
            send(packet, player);
        }
    }
    public static void sendToNearby(ServerWorld world, Vec3i target, double radius, FabricPacket packet) {
        for (ServerPlayerEntity player : PlayerLookup.around(world, target, radius)) {
            send(packet, player);
        }
    }
}
