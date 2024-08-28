package mc.duzo.timeless.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.client.sounds.manager.SoundManager;

public record UpdateFlyingStatusS2CPacket(UUID player, boolean isFlying) implements FabricPacket {
    public static final PacketType<UpdateFlyingStatusS2CPacket> TYPE = PacketType.create(new Identifier(Timeless.MOD_ID, "update_flying_status"), UpdateFlyingStatusS2CPacket::new);

    public UpdateFlyingStatusS2CPacket(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(player);
        buf.writeBoolean(isFlying);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public void handle(ClientPlayerEntity client, PacketSender sender) {
        if (!(client.getWorld().getPlayerByUuid(player) instanceof AbstractClientPlayerEntity found)) return;

        SoundManager.thrusters().handle(found, isFlying);
    }
}
