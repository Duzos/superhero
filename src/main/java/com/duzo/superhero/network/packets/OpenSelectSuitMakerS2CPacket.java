package com.duzo.superhero.network.packets;

import com.duzo.superhero.blocks.entities.SuitMakerBlockEntity;
import com.duzo.superhero.client.gui.screen.SuitMakerSelectScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenSelectSuitMakerS2CPacket {
    public boolean messageIsValid;
    private BlockPos pos;

    public OpenSelectSuitMakerS2CPacket(BlockPos pos) {
        this.pos = pos;
        this.messageIsValid = true;
    }
    public OpenSelectSuitMakerS2CPacket() {
        this.messageIsValid = false;
    }

    public static OpenSelectSuitMakerS2CPacket decode(FriendlyByteBuf buf) {
        OpenSelectSuitMakerS2CPacket packet = new OpenSelectSuitMakerS2CPacket();

        try {
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
                    Minecraft.getInstance().setScreen(new SuitMakerSelectScreen(suitMaker));
                }
            });
        });
        return true;
    }
}
