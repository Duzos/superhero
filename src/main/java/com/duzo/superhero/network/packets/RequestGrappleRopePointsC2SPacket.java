package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.batman.GrapplingHookRopeEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Deprecated
public class RequestGrappleRopePointsC2SPacket {
    public boolean messageIsValid;

    private int entityID;
    public RequestGrappleRopePointsC2SPacket(int entityID) {
        this.entityID = entityID;
        this.messageIsValid = true;
//        System.out.println(this);
    }
    public RequestGrappleRopePointsC2SPacket() {
        this.messageIsValid = false;
    }

    public static RequestGrappleRopePointsC2SPacket decode(FriendlyByteBuf buf) {
        RequestGrappleRopePointsC2SPacket packet = new RequestGrappleRopePointsC2SPacket();

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
            Level level = context.getSender().getLevel();

            if (level.getEntity(this.entityID) instanceof GrapplingHookRopeEntity rope) {
                rope.setPointsChanged();
            }
        });
        return true;
    }
}
