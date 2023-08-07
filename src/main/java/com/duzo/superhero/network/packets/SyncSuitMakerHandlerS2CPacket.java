package com.duzo.superhero.network.packets;

import com.duzo.superhero.blocks.entities.SuitMakerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSuitMakerHandlerS2CPacket {
    public boolean messageIsValid;

    private CompoundTag inventory;
    private BlockPos pos;

    public SyncSuitMakerHandlerS2CPacket(BlockPos pos,CompoundTag inventory) {
        this.inventory = inventory;
        this.pos = pos;
        this.messageIsValid = true;
    }
    public SyncSuitMakerHandlerS2CPacket() {
        this.messageIsValid = false;
    }

    public static SyncSuitMakerHandlerS2CPacket decode(FriendlyByteBuf buf) {
        SyncSuitMakerHandlerS2CPacket packet = new SyncSuitMakerHandlerS2CPacket();

        try {
            packet.inventory = buf.readNbt();
            packet.pos = buf.readBlockPos();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeNbt(this.inventory);
        buf.writeBlockPos(this.pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Level level = Minecraft.getInstance().level;
                BlockEntity entity = level.getBlockEntity(this.pos);
                if (entity instanceof SuitMakerBlockEntity suitMaker) {
                    suitMaker.setItemHandlerData(this.inventory);
                }
            });
        });
        return true;
    }
}
