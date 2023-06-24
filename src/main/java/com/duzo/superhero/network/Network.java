package com.duzo.superhero.network;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.network.packets.AbilityC2SPacket;
import com.duzo.superhero.network.packets.ChangeDeltaMovementS2CPacket;
import com.duzo.superhero.network.packets.SwingArmS2CPacket;
import com.duzo.superhero.network.packets.TakeOffIronManSuitC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Network {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Superhero.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(TakeOffIronManSuitC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TakeOffIronManSuitC2SPacket::new)
                .encoder(TakeOffIronManSuitC2SPacket::toBytes)
                .consumerMainThread(TakeOffIronManSuitC2SPacket::handle)
                .add();
        net.messageBuilder(AbilityC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AbilityC2SPacket::decode)
                .encoder(AbilityC2SPacket::encode)
                .consumerMainThread(AbilityC2SPacket::handle)
                .add();
        net.messageBuilder(ChangeDeltaMovementS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChangeDeltaMovementS2CPacket::decode)
                .encoder(ChangeDeltaMovementS2CPacket::encode)
                .consumerMainThread(ChangeDeltaMovementS2CPacket::handle)
                .add();
        net.messageBuilder(SwingArmS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SwingArmS2CPacket::decode)
                .encoder(SwingArmS2CPacket::encode)
                .consumerMainThread(SwingArmS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
