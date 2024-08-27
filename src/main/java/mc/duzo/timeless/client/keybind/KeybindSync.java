package mc.duzo.timeless.client.keybind;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import mc.duzo.timeless.util.ServerKeybind;

public class KeybindSync {
    private static boolean jumpingLast = false;
    private static boolean forwardLast = false;

    public static void tick(ClientPlayerEntity player) {
        boolean hasChanged = (jumpingLast != player.input.jumping) || (forwardLast != player.input.pressingForward);

        if (!hasChanged) return;

        jumpingLast = player.input.jumping;
        forwardLast = player.input.pressingForward;
        toServer(jumpingLast, forwardLast);
    }

    public static void toServer(Boolean... args) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeUuid(MinecraftClient.getInstance().player.getUuid());

        for (Boolean b : args) {
            buf.writeBoolean(b);
        }

        ClientPlayNetworking.send(ServerKeybind.UPDATE, buf);
    }
}
