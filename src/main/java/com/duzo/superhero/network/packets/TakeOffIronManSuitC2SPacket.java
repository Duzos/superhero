package com.duzo.superhero.network.packets;

import com.duzo.superhero.blocks.IronManSuitCaseBlock;
import com.duzo.superhero.blocks.SuperheroBlocks;
import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.IronManCapabilities;
import com.duzo.superhero.util.IronManCapability;
import com.duzo.superhero.util.IronManMark;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.convertArmourToSuitcase;
import static com.duzo.superhero.entities.IronManEntity.isValidArmorButCooler;
import static com.duzo.superhero.entities.IronManEntity.spawnNew;

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

            if (!isValidArmorButCooler(player)) return;

            IronManArmourItem item = (IronManArmourItem) head.getItem();
            IronManCapabilities cap = item.getMark().getCapabilities();

            if (cap.has(IronManCapability.SUITCASE)) {
                convertArmourToSuitcase(player);
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
            } else if (cap.has(IronManCapability.SEAMLESS)) {
                spawnNew(item.getMark(),level,player.getOnPos(),player);
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
            } else if (cap.has(IronManCapability.BRACELET_LOCATING)) {
                spawnNew(item.getMark(),level,player.getOnPos(),player); // @TODO temporarily the same as seamless until i make something new for it
                level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
            }
        });
        return true;
    }
}
