package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateWebRopePlayerS2CPacket {
    public boolean messageIsValid;

    private int entityID;
    private int playerID;

    public UpdateWebRopePlayerS2CPacket(int entityID, int playerID) {
        this.entityID = entityID;
        this.playerID = playerID;
        this.messageIsValid = true;
    }
    public UpdateWebRopePlayerS2CPacket() {
        this.messageIsValid = false;
    }

    public static UpdateWebRopePlayerS2CPacket decode(FriendlyByteBuf buf) {
        UpdateWebRopePlayerS2CPacket packet = new UpdateWebRopePlayerS2CPacket();

        try {
            packet.entityID = buf.readInt();
            packet.playerID = buf.readInt();
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
        buf.writeInt(this.playerID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Level level = Minecraft.getInstance().level;

                if (level.getEntity(this.entityID) instanceof WebRopeEntity rope && level.getEntity(this.playerID) instanceof Player player) {
                    rope.setPlayer(player);
                }
            });
        });
        return true;
    }
}
