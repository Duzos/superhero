package mc.duzo.timeless.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.client.animation.player.PlayerAnimationTracker;
import mc.duzo.timeless.client.animation.player.TimelessPlayerAnimations;
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.animation.IronManAnimations;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;

public record MarkFiveAnimationS2CPacket(UUID player, boolean isPuttingOn) implements FabricPacket {
    public static final PacketType<MarkFiveAnimationS2CPacket> TYPE = PacketType.create(new Identifier(Timeless.MOD_ID, "mark_five_animation"), MarkFiveAnimationS2CPacket::new);

    public MarkFiveAnimationS2CPacket(PacketByteBuf buf) {
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
        SuitAnimationHolder suit = (isPuttingOn) ? new SuitAnimationHolder(IronManAnimations.MK5_CASE_OPEN, true, false, true) : new SuitAnimationHolder(IronManAnimations.MK5_CASE_CLOSE, true, false, true);
        SuitAnimationTracker.addAnimation(player, suit);

        PlayerAnimationHolder anim = (isPuttingOn) ? new PlayerAnimationHolder(TimelessPlayerAnimations.MK5_CASE_OPEN) : new PlayerAnimationHolder(TimelessPlayerAnimations.MK5_CASE_CLOSE);
        PlayerAnimationTracker.addAnimation(player, anim);
    }
}
