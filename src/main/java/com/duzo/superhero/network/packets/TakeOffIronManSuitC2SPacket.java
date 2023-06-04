package com.duzo.superhero.network.packets;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.items.IronManArmourItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.duzo.superhero.entities.IronManEntity.isValidArmourSet;

public class TakeOffIronManSuitC2SPacket {
    public TakeOffIronManSuitC2SPacket() {

    }

    public TakeOffIronManSuitC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // SERVER
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

            if (!isValidArmourSet(head,chest,legs,feet)) return;

            IronManArmourItem item = (IronManArmourItem) head.getItem();
            IronManEntity.spawnNew(item.getMark(),level,player.getOnPos(),player);
            level.playSound(null,player, SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS,1f,1f);

            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        });
        return true;
    }
}
