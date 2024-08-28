package mc.duzo.timeless.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import mc.duzo.timeless.network.s2c.MarkFiveCaseS2CPacket;
import mc.duzo.timeless.network.s2c.MarkFiveMaskS2CPacket;
import mc.duzo.timeless.network.s2c.UpdateFlyingStatusS2CPacket;

public class ClientNetwork {
    static {
        ClientPlayNetworking.registerGlobalReceiver(MarkFiveCaseS2CPacket.TYPE, MarkFiveCaseS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(MarkFiveMaskS2CPacket.TYPE, MarkFiveMaskS2CPacket::handle);
        ClientPlayNetworking.registerGlobalReceiver(UpdateFlyingStatusS2CPacket.TYPE, UpdateFlyingStatusS2CPacket::handle);
    }

    public static void init() {

    }
}
