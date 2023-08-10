package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestWebRopePlayerC2SPacket {
    public boolean messageIsValid;

    private int entityID;

    public RequestWebRopePlayerC2SPacket(int entityID) {
        this.entityID = entityID;
        this.messageIsValid = true;
    }
    public RequestWebRopePlayerC2SPacket() {
        this.messageIsValid = false;
    }

    public static RequestWebRopePlayerC2SPacket decode(FriendlyByteBuf buf) {
        RequestWebRopePlayerC2SPacket packet = new RequestWebRopePlayerC2SPacket();

        try {
            packet.entityID = buf.readInt();
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
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = context.getSender().level();

            System.out.println(level.getEntity(this.entityID));

            if (level.getEntity(this.entityID) instanceof WebRopeEntity rope) {
                rope.syncPlayerToClient();
            }
        });
        return true;
    }
}
