package com.duzo.superhero.network.packets;

import com.duzo.superhero.blocks.entities.SuitMakerBlockEntity;
import com.duzo.superhero.recipes.SuperheroSuitRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSuitMakerRecipeC2SPacket {
    public boolean messageIsValid;
    private BlockPos pos;
    private SuperheroSuitRecipe recipe;
    private EquipmentSlot slot;

    public UpdateSuitMakerRecipeC2SPacket(BlockPos pos, EquipmentSlot slot, SuperheroSuitRecipe recipe) {
        this.pos = pos;
        this.slot = slot;
        this.recipe = recipe;
        this.messageIsValid = true;
    }
    public UpdateSuitMakerRecipeC2SPacket() {
        this.messageIsValid = false;
    }

    public static UpdateSuitMakerRecipeC2SPacket decode(FriendlyByteBuf buf) {
        UpdateSuitMakerRecipeC2SPacket packet = new UpdateSuitMakerRecipeC2SPacket();

        try {
            packet.pos = buf.readBlockPos();
            packet.recipe = SuperheroSuitRecipe.fromNBT(buf.readNbt());
            packet.slot = EquipmentSlot.values()[buf.readInt()];
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
        buf.writeNbt(this.recipe.serializeNBT());
        buf.writeInt(this.slot.ordinal());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = context.getSender().level;

            if (level.getBlockEntity(this.pos) instanceof SuitMakerBlockEntity suit) {
                suit.selectedSuitSlot = this.slot;
                suit.selectedSuitRecipe = this.recipe;
                suit.verifyRecipe();
            }
        });
        return true;
    }
}
