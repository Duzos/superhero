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
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveAnimations;
import mc.duzo.timeless.suit.client.animation.impl.ironman.mk5.MarkFiveCaseAnimation;

public record MarkFiveCaseS2CPacket(UUID player, boolean isPuttingOn) implements FabricPacket {
    public static final PacketType<MarkFiveCaseS2CPacket> TYPE = PacketType.create(new Identifier(Timeless.MOD_ID, "mark_five_animation"), MarkFiveCaseS2CPacket::new);

    public MarkFiveCaseS2CPacket(PacketByteBuf buf) {
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
        SuitAnimationHolder suit = new MarkFiveCaseAnimation(isPuttingOn);
        SuitAnimationTracker.addAnimation(player, suit);

        PlayerAnimationHolder anim = (isPuttingOn) ? new PlayerAnimationHolder(MarkFiveAnimations.CASE_OPEN_PLAYER) : new PlayerAnimationHolder(MarkFiveAnimations.CASE_CLOSE_PLAYER);
        PlayerAnimationTracker.addAnimation(player, anim);
    }
}
