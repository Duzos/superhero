package mc.duzo.timeless.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveMaskAnimation;

public record MarkFiveMaskS2CPacket(UUID player, boolean isPuttingOn) implements FabricPacket {
    public static final PacketType<MarkFiveMaskS2CPacket> TYPE = PacketType.create(new Identifier(Timeless.MOD_ID, "mark_five_case"), MarkFiveMaskS2CPacket::new);

    public MarkFiveMaskS2CPacket(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readBoolean());
    }
    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(player);
        buf.writeBoolean(isPuttingOn);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handle(ClientPlayerEntity client, PacketSender sender) {
        SuitAnimationHolder suit = new MarkFiveMaskAnimation(isPuttingOn);
        SuitAnimationTracker.addAnimation(player, suit);
    }
}
