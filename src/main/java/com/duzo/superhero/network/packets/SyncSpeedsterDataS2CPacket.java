package com.duzo.superhero.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSpeedsterDataS2CPacket {
    public boolean messageIsValid;

    private double speed;

    public SyncSpeedsterDataS2CPacket(double speed) {
        this.speed = speed;
        this.messageIsValid = true;
    }
    public SyncSpeedsterDataS2CPacket() {
        this.messageIsValid = false;
    }

    public static SyncSpeedsterDataS2CPacket decode(FriendlyByteBuf buf) {
        SyncSpeedsterDataS2CPacket packet = new SyncSpeedsterDataS2CPacket();

        try {
            packet.speed = buf.readDouble();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeDouble(this.speed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().player;

                player.getPersistentData().putDouble("speedster.speed",this.speed);
            });
        });
        return true;
    }
}
