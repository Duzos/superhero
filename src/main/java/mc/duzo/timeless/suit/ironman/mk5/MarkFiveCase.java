package mc.duzo.timeless.suit.ironman.mk5;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import mc.duzo.timeless.datagen.provider.lang.AutomaticEnglish;
import mc.duzo.timeless.network.Network;
import mc.duzo.timeless.network.s2c.MarkFiveAnimationS2CPacket;
import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;
import mc.duzo.timeless.util.time.Scheduler;
import mc.duzo.timeless.util.time.TimeUnit;

public class MarkFiveCase extends Item implements AutomaticEnglish {
    public MarkFiveCase() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            boolean wearing = getSet().isWearing(user);
            return (!wearing) ? TypedActionResult.consume(user.getStackInHand(hand)) : TypedActionResult.fail(user.getStackInHand(hand));
        }

        boolean success = fromCase((ServerPlayerEntity) user, false);
        return (success) ? TypedActionResult.consume(user.getStackInHand(hand)) : TypedActionResult.fail(user.getStackInHand(hand));
    }

    public static boolean toCase(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!player.isOnGround()) return false;
            if (!(getSet().isWearing(player))) return false;

            player.getWorld().playSound(null, player.getBlockPos(), Register.Sounds.MARK5_NOISES, SoundCategory.PLAYERS, 0.25f, 1f);
            Network.toTracking(new MarkFiveAnimationS2CPacket(player.getUuid(), false), player);
        }

        Scheduler.runTaskLater(() -> toCasePost(player, force), TimeUnit.SECONDS, (long) (8.038f));
        return true;
    }
    private static void toCasePost(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!(getSet().isWearing(player))) return;

            player.getArmorItems().forEach(stack -> stack.setCount(0));
        }
        player.getInventory().offerOrDrop(new ItemStack(Register.Items.MARK_FIVE_CASE));
    }

    public static boolean fromCase(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!player.isOnGround()) return false;
            if (!player.getMainHandStack().isOf(Register.Items.MARK_FIVE_CASE)) return false; // not holding
            if (getSet().isWearing(player)) return false; // already wearing

            player.getWorld().playSound(null, player.getBlockPos(), Register.Sounds.MARK5_NOISES, SoundCategory.PLAYERS, 0.25f, 1f);
            Network.toTracking(new MarkFiveAnimationS2CPacket(player.getUuid(), true), player);

            player.getMainHandStack().setCount(0);
        }

        getSet().wear(player);
        return true;
    }

    private static SuitSet getSet() {
        return SetRegistry.MARK_FIVE;
    }
}
