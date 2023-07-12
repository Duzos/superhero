package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateInitialDistanceS2CPacket {
    public boolean messageIsValid;

    private int entityID;
    private double initiald;

    public UpdateInitialDistanceS2CPacket(int entityID, double initiald) {
        this.entityID = entityID;
        this.initiald = initiald;
        this.messageIsValid = true;
    }
    public UpdateInitialDistanceS2CPacket() {
        this.messageIsValid = false;
    }

    public static UpdateInitialDistanceS2CPacket decode(FriendlyByteBuf buf) {
        UpdateInitialDistanceS2CPacket packet = new UpdateInitialDistanceS2CPacket();

        try {
            packet.entityID = buf.readInt();
            packet.initiald = buf.readDouble();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeInt(this.entityID);
        buf.writeDouble(this.initiald);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Level level = Minecraft.getInstance().level;

                if (level.getEntity(this.entityID) instanceof WebRopeEntity rope) {
                    rope.setInitialDistance(this.initiald);
                }
            });
        });
        return true;
    }
}
