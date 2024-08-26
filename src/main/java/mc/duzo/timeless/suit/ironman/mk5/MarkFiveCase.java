package mc.duzo.timeless.suit.ironman.mk5;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.set.SetRegistry;
import mc.duzo.timeless.suit.set.SuitSet;

public class MarkFiveCase extends Item {
    public MarkFiveCase() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) return TypedActionResult.consume(user.getStackInHand(hand));

        boolean success = fromCase((ServerPlayerEntity) user, false);
        return (success) ? TypedActionResult.consume(user.getStackInHand(hand)) : TypedActionResult.fail(user.getStackInHand(hand));
    }

    public static boolean toCase(ServerPlayerEntity player, boolean force) {
        if (!force) {
            if (!(getSet().isWearing(player))) return false;
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
