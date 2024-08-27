package mc.duzo.timeless.util;

import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;

public class ServerKeybind {
    public static final Identifier UPDATE = new Identifier(Timeless.MOD_ID, "update");

    static {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE, (server, player, handler, buf, responseSender) -> process(buf));
    }
    public static final HashMap<UUID, Boolean> JUMPS = new HashMap<>();
    public static final HashMap<UUID, Boolean> FORWARDS = new HashMap<>();

    public static boolean isJumping(UUID id) {
        return JUMPS.computeIfAbsent(id, k -> false);
    }
    public static boolean isJumping(ServerPlayerEntity player) {
        return isJumping(player.getUuid());
    }

    public static void setJumping(UUID id, boolean val) {
        JUMPS.put(id, val);
    }

    public static boolean isMovingForward(UUID id) {
        return FORWARDS.computeIfAbsent(id, k -> false);
    }
    public static boolean isMovingForward(ServerPlayerEntity player) {
        return isMovingForward(player.getUuid());
    }

    public static void setForwards(UUID id, boolean val) {
        FORWARDS.put(id, val);
    }

    public static void process(PacketByteBuf data) {
        UUID id = data.readUuid();

        setJumping(id, data.readBoolean());
        setForwards(id, data.readBoolean());
    }
}
