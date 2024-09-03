package mc.duzo.timeless.power.impl;

import mc.duzo.animation.DuzoAnimationMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.item.SuitItem;

public class MaskTogglePower extends Power {
    private final Identifier id;

    public MaskTogglePower() {
        this.id = new Identifier(Timeless.MOD_ID, "mask_toggle");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        setMask(player, !hasMask(player), true);
        player.getWorld().playSound(null, player.getBlockPos(), Register.Sounds.IRONMAN_MASK, SoundCategory.PLAYERS, 0.25f, 1f);

        return true;
    }
    private static void setMask(ServerPlayerEntity player, boolean val, boolean sync) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return;

        data.putBoolean("MaskEnabled", val);

        if (sync) {
            SuitAnimationHolder anim = (val) ? Register.Animations.Suits.MarkFive.MASK_OPEN.get() : Register.Animations.Suits.MarkFive.MASK_CLOSE.get();
            DuzoAnimationMod.play(player, Register.Trackers.SUIT, anim);
        }
    }
    public static boolean hasMask(PlayerEntity player) {
        NbtCompound data = SuitItem.Data.get(player);

        if (data == null) return false;
        if (!(data.contains("MaskEnabled"))) return true;

        return data.getBoolean("MaskEnabled");
    }

    @Override
    public void tick(ServerPlayerEntity player) {

    }

    @Override
    public void onLoad(ServerPlayerEntity player) {
        setMask(player, hasMask(player), true);
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}