package com.duzo.superhero.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeDeltaMovementS2CPacket {
    public boolean messageIsValid;

    private Vec3 delta;

    public ChangeDeltaMovementS2CPacket(Vec3 delta) {
        this.delta = delta;
        this.messageIsValid = true;
    }
    public ChangeDeltaMovementS2CPacket() {
        this.messageIsValid = false;
    }

    public static ChangeDeltaMovementS2CPacket decode(FriendlyByteBuf buf) {
        ChangeDeltaMovementS2CPacket packet = new ChangeDeltaMovementS2CPacket();

        try {
            packet.delta = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeDouble(this.delta.x);
        buf.writeDouble(this.delta.y);
        buf.writeDouble(this.delta.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Player player = Minecraft.getInstance().player;

                player.setDeltaMovement(this.delta);
            });
        });
        return true;
    }
}