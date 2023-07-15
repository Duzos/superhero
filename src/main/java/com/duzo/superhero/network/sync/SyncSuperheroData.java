package com.duzo.superhero.network.sync;

import com.duzo.superhero.data.SuperheroData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSuperheroData {
    public boolean messageIsValid;

    private int entityId;
    private CompoundTag nbt;

    public SyncSuperheroData(int entityId, CompoundTag nbt) {
        this.entityId = entityId;
        this.nbt = nbt;
        this.messageIsValid = true;
    }
    public SyncSuperheroData() {
        this.messageIsValid = false;
    }

    public static SyncSuperheroData decode(FriendlyByteBuf buf) {
        SyncSuperheroData packet = new SyncSuperheroData();

        try {
            packet.entityId = buf.readInt();
            packet.nbt = buf.readNbt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeInt(this.entityId);
        buf.writeNbt(this.nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            Entity entity = level.getEntity(this.entityId);
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if (entity instanceof Player player)
                    SuperheroData.get(player).ifPresent(superheroData -> superheroData.deserializeNBT(this.nbt));
            });
        });
        return true;
    }
}