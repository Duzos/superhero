package mc.duzo.timeless.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import mc.duzo.timeless.network.s2c.MarkFiveAnimationS2CPacket;

public class ClientNetwork {
    static {
        ClientPlayNetworking.registerGlobalReceiver(MarkFiveAnimationS2CPacket.TYPE, MarkFiveAnimationS2CPacket::handle);
    }

    public static void init() {

    }
}
