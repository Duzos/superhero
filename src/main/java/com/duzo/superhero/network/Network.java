package com.duzo.superhero.network;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.network.packets.*;
import com.duzo.superhero.network.sync.SyncSuperheroData;
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
        net.messageBuilder(SyncSuperheroData.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncSuperheroData::decode)
                .encoder(SyncSuperheroData::encode)
                .consumerMainThread(SyncSuperheroData::handle)
                .add();
        net.messageBuilder(SyncSpeedsterDataS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncSpeedsterDataS2CPacket::decode)
                .encoder(SyncSpeedsterDataS2CPacket::encode)
                .consumerMainThread(SyncSpeedsterDataS2CPacket::handle)
                .add();
        net.messageBuilder(SwingArmS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SwingArmS2CPacket::decode)
                .encoder(SwingArmS2CPacket::encode)
                .consumerMainThread(SwingArmS2CPacket::handle)
                .add();
        net.messageBuilder(UpdateWebRopePointsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateWebRopePointsS2CPacket::decode)
                .encoder(UpdateWebRopePointsS2CPacket::encode)
                .consumerMainThread(UpdateWebRopePointsS2CPacket::handle)
                .add();
        net.messageBuilder(RequestWebRopePointsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestWebRopePointsC2SPacket::decode)
                .encoder(RequestWebRopePointsC2SPacket::encode)
                .consumerMainThread(RequestWebRopePointsC2SPacket::handle)
                .add();
        net.messageBuilder(UpdateWebRopeAlphaS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateWebRopeAlphaS2CPacket::decode)
                .encoder(UpdateWebRopeAlphaS2CPacket::encode)
                .consumerMainThread(UpdateWebRopeAlphaS2CPacket::handle)
                .add();
        net.messageBuilder(UpdateInitialDistanceS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateInitialDistanceS2CPacket::decode)
                .encoder(UpdateInitialDistanceS2CPacket::encode)
                .consumerMainThread(UpdateInitialDistanceS2CPacket::handle)
                .add();
        net.messageBuilder(UpdateGrappleRopePointsS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateGrappleRopePointsS2CPacket::decode)
                .encoder(UpdateGrappleRopePointsS2CPacket::encode)
                .consumerMainThread(UpdateGrappleRopePointsS2CPacket::handle)
                .add();
        net.messageBuilder(RequestGrappleRopePointsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestGrappleRopePointsC2SPacket::decode)
                .encoder(RequestGrappleRopePointsC2SPacket::encode)
                .consumerMainThread(RequestGrappleRopePointsC2SPacket::handle)
                .add();
        net.messageBuilder(RequestWebRopePlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestWebRopePlayerC2SPacket::decode)
                .encoder(RequestWebRopePlayerC2SPacket::encode)
                .consumerMainThread(RequestWebRopePlayerC2SPacket::handle)
                .add();
        net.messageBuilder(UpdateWebRopePlayerS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UpdateWebRopePlayerS2CPacket::decode)
                .encoder(UpdateWebRopePlayerS2CPacket::encode)
                .consumerMainThread(UpdateWebRopePlayerS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToTracking(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(()-> player), message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
