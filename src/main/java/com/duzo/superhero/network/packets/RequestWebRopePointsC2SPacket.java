package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Deprecated
public class RequestWebRopePointsC2SPacket {
    public boolean messageIsValid;

    private int entityID;
    public RequestWebRopePointsC2SPacket(int entityID) {
        this.entityID = entityID;
        this.messageIsValid = true;
//        System.out.println(this);
    }
    public RequestWebRopePointsC2SPacket() {
        this.messageIsValid = false;
    }

    public static RequestWebRopePointsC2SPacket decode(FriendlyByteBuf buf) {
        RequestWebRopePointsC2SPacket packet = new RequestWebRopePointsC2SPacket();

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

            if (level.getEntity(this.entityID) instanceof WebRopeEntity rope) {
                rope.setPointsChanged();
            }
        });
        return true;
    }
}
