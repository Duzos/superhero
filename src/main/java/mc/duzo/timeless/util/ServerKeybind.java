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
    public static final HashMap<UUID, Keymap> MOVEMENT = new HashMap<>();

    public static Keymap get(UUID id) {
        return MOVEMENT.computeIfAbsent(id, k -> new Keymap());
    }
    public static Keymap get(ServerPlayerEntity player) {
        return get(player.getUuid());
    }

    public static void process(PacketByteBuf data) {
        UUID id = data.readUuid();

        Keymap map = get(id);
        map.setJumping(data.readBoolean());
        map.setForward(data.readBoolean());
        map.setLeft(data.readBoolean());
        map.setRight(data.readBoolean());
        map.setBackward(data.readBoolean());
    }

    public static class Keymap extends HashMap<String, Boolean> {
        public boolean isMovingForward() {
            return this.computeIfAbsent("forward", k -> false);
        }
        public void setForward(boolean val) {
            this.put("forward", val);
        }

        public boolean isMovingLeft() {
            return this.computeIfAbsent("left", k -> false);
        }
        public void setLeft(boolean val) {
            this.put("left", val);
        }

        public boolean isMovingRight() {
            return this.computeIfAbsent("right", k -> false);
        }
        public void setRight(boolean val) {
            this.put("right", val);
        }
        public boolean isMovingBackward() {
            return this.computeIfAbsent("backward", k -> false);
        }
        public void setBackward(boolean val) {
            this.put("backward", val);
        }

        public boolean isJumping() {
            return this.computeIfAbsent("jump", k -> false);
        }
        public void setJumping(boolean val) {
            this.put("jump", val);
        }
    }
}
