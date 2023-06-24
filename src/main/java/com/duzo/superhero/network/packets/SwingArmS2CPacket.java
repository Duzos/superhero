package com.duzo.superhero.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwingArmS2CPacket {
    public boolean messageIsValid;

    private InteractionHand hand;

    public SwingArmS2CPacket(InteractionHand hand) {
        this.hand = hand;
        this.messageIsValid = true;
    }
    public SwingArmS2CPacket() {
        this.messageIsValid = false;
    }

    public static SwingArmS2CPacket decode(FriendlyByteBuf buf) {
        SwingArmS2CPacket packet = new SwingArmS2CPacket();

        try {
            packet.hand = InteractionHand.values()[buf.readInt()];
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeInt(this.hand.ordinal());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().player;

                player.swing(this.hand);
            });
        });
        return true;
    }
}
