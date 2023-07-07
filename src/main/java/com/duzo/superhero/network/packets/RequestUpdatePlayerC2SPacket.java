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

public class RequestUpdatePlayerC2SPacket {
    public boolean messageIsValid;

    private int entityID;
    private int playerID;

    public RequestUpdatePlayerC2SPacket(int entityID, int playerID) {
        this.entityID = entityID;
        this.playerID = playerID;
        this.messageIsValid = true;
    }
    public RequestUpdatePlayerC2SPacket() {
        this.messageIsValid = false;
    }

    public static RequestUpdatePlayerC2SPacket decode(FriendlyByteBuf buf) {
        RequestUpdatePlayerC2SPacket packet = new RequestUpdatePlayerC2SPacket();

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
                    rope.player = player;
                }
            });
        });
        return true;
    }
}
