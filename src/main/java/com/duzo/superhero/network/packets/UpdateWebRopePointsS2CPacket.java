package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Deprecated
public class UpdateWebRopePointsS2CPacket {
    public boolean messageIsValid;

    private int entityID;
    private Vec3 origin,point;

    public UpdateWebRopePointsS2CPacket(int entityID, Vec3 origin, Vec3 point) {
        this.entityID = entityID;
        this.origin = origin;
        this.point = point;
        this.messageIsValid = true;
    }
    public UpdateWebRopePointsS2CPacket() {
        this.messageIsValid = false;
    }

    public static UpdateWebRopePointsS2CPacket decode(FriendlyByteBuf buf) {
        UpdateWebRopePointsS2CPacket packet = new UpdateWebRopePointsS2CPacket();

        try {
            packet.entityID = buf.readInt();
            packet.origin = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
            packet.point = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
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

        buf.writeDouble(this.origin.x);
        buf.writeDouble(this.origin.y);
        buf.writeDouble(this.origin.z);

        buf.writeDouble(this.point.x);
        buf.writeDouble(this.point.y);
        buf.writeDouble(this.point.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Level level = Minecraft.getInstance().level;

                if (level.getEntity(this.entityID) instanceof WebRopeEntity rope) {
                    rope.origin = this.origin;
                    rope.point = this.point;
                }
            });
        });
        return true;
    }
}
