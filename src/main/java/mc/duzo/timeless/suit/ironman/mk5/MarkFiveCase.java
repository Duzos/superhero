package mc.duzo.timeless.suit.ironman.mk5;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import mc.duzo.timeless.client.animation.player.PlayerAnimationTracker;
import mc.duzo.timeless.client.animation.player.TimelessPlayerAnimations;
import mc.duzo.timeless.client.animation.player.holder.PlayerAnimationHolder;
import mc.duzo.timeless.datagen.provider.lang.AutomaticEnglish;
import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.client.animation.IronManAnimations;
import mc.duzo.timeless.suit.client.animation.SuitAnimationHolder;
import mc.duzo.timeless.suit.client.animation.SuitAnimationTracker;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public class MarkFiveCase extends Item implements AutomaticEnglish {
    public MarkFiveCase() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            boolean wearing = getSet().isWearing(user);

            if (!wearing) {
                // todo - send off a packet instead
                SuitAnimationTracker.addAnimation(user.getUuid(), new SuitAnimationHolder(IronManAnimations.MK5_CASE_OPEN, true, false));
                PlayerAnimationTracker.addAnimation(user.getUuid(), new PlayerAnimationHolder(TimelessPlayerAnimations.MK5_CASE_OPEN));
            }

            return (!wearing) ? TypedActionResult.consume(user.getStackInHand(hand)) : TypedActionResult.fail(user.getStackInHand(hand));
        }

        boolean success = fromCase((ServerPlayerEntity) user, false);
        return (success) ? TypedActionResult.consume(user.getStackInHand(hand)) : TypedActionResult.fail(user.getStackInHand(hand));
    }

    public static boolean toCase(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!(getSet().isWearing(player))) return false;

            player.getArmorItems().forEach(stack -> stack.setCount(0));
        }

        player.getInventory().offerOrDrop(new ItemStack(Register.Items.MARK_FIVE_CASE));
        return true;
    }
    public static boolean fromCase(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!player.getMainHandStack().isOf(Register.Items.MARK_FIVE_CASE)) return false; // not holding
            if (getSet().isWearing(player)) return false; // already wearing

            player.getMainHandStack().setCount(0);
        }

        getSet().wear(player);
        return true;
    }

    private static SuitSet getSet() {
        return SetRegistry.MARK_FIVE;
    }
}
