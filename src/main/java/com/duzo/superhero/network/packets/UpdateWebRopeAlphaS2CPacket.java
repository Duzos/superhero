package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateWebRopeAlphaS2CPacket {
    public boolean messageIsValid;

    private int entityID;
    private float alpha;

    public UpdateWebRopeAlphaS2CPacket(int entityID, float alpha) {
        this.entityID = entityID;
        this.alpha = alpha;
        this.messageIsValid = true;
    }
    public UpdateWebRopeAlphaS2CPacket() {
        this.messageIsValid = false;
    }

    public static UpdateWebRopeAlphaS2CPacket decode(FriendlyByteBuf buf) {
        UpdateWebRopeAlphaS2CPacket packet = new UpdateWebRopeAlphaS2CPacket();

        try {
            packet.entityID = buf.readInt();
            packet.alpha = buf.readFloat();
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
        buf.writeFloat(this.alpha);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Level level = Minecraft.getInstance().level;

                if (level.getEntity(this.entityID) instanceof WebRopeEntity rope) {
                    rope.setAlpha(this.alpha);
                }
            });
        });
        return true;
    }
}
