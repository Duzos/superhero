package com.duzo.superhero.network.packets;

import com.duzo.superhero.items.SuperheroArmourItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityC2SPacket {
    public boolean messageIsValid;

    private int number;

    public AbilityC2SPacket(int number) {
        this.number = number;
        this.messageIsValid = true;
    }
    public AbilityC2SPacket() {
        this.messageIsValid = false;
    }

    public static AbilityC2SPacket decode(FriendlyByteBuf buf) {
        AbilityC2SPacket packet = new AbilityC2SPacket();

        try {
            packet.number = buf.readInt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.out.println("Exception while reading Packet: " + e);
            return packet;
        }

        packet.messageIsValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!this.messageIsValid) return;

        buf.writeInt(this.number);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Make sure it's only executed on the physical client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                // SERVER
                ServerPlayer player = context.getSender();
                ServerLevel level = player.getLevel();

                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

                if (!(chest.getItem() instanceof SuperheroArmourItem hero)) return;
                if (!hero.isValidArmor(player)) return;

                hero.runAbility(player,this.number);

            });
        });
        return true;
    }
}
