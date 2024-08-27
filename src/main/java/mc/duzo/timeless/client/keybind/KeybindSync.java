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
    private static boolean leftLast = false;
    private static boolean rightLast = false;
    private static boolean backLast = false;

    public static void tick(ClientPlayerEntity player) {
        boolean hasChanged = (jumpingLast != player.input.jumping) || (forwardLast != player.input.pressingForward) || (leftLast != player.input.pressingLeft) || (rightLast != player.input.pressingRight) || (backLast != player.input.pressingBack);

        if (!hasChanged) return;

        jumpingLast = player.input.jumping;
        forwardLast = player.input.pressingForward;
        leftLast = player.input.pressingLeft;
        rightLast = player.input.pressingRight;
        backLast = player.input.pressingBack;

        toServer(jumpingLast, forwardLast, leftLast, rightLast, backLast);
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
