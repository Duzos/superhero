package mc.duzo.timeless.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import mc.duzo.timeless.network.c2s.UsePowerC2SPacket;

public class Network {
    static {
        ServerPlayNetworking.registerGlobalReceiver(UsePowerC2SPacket.TYPE, UsePowerC2SPacket::handle);
    }

    public static void init() {

    }
}
