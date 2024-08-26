package mc.duzo.timeless.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.network.c2s.UsePowerC2SPacket;

public class Network {
    static {
        ServerPlayNetworking.registerGlobalReceiver(UsePowerC2SPacket.TYPE, UsePowerC2SPacket::handle);
    }

    public static void init() {

    }


    // codec networking is Theo's from Adventures in Time
    public static <T> void send(ServerPlayerEntity player, PacketByteBuf buf, Identifier id, Codec<T> codec, T t) {
        DataResult<NbtElement> result = codec.encodeStart(NbtOps.INSTANCE, t);
        NbtElement nbt = result.resultOrPartial(Timeless.LOGGER::error).orElseThrow();

        buf.writeNbt((NbtCompound) nbt);
        send(player, id, buf);
    }

    public static void send(ServerPlayerEntity player, Identifier id, PacketByteBuf buf) {
        if (player == null)
            return;

        ServerPlayNetworking.send(player, id, buf);
    }

    public static <T> T receive(Codec<T> codec, PacketByteBuf buf) {
        return codec.decode(NbtOps.INSTANCE, buf.readNbt())
                .resultOrPartial(Timeless.LOGGER::error)
                .orElseThrow().getFirst();
    }

    public static void toTracking(FabricPacket packet, ServerPlayerEntity target) {
        ServerPlayNetworking.send(target, packet);
        PlayerLookup.tracking(target).forEach(p -> ServerPlayNetworking.send(p, packet));
    }
}
