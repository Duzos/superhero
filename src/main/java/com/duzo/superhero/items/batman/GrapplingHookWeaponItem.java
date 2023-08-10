package com.duzo.superhero.items.batman;

import com.duzo.superhero.entities.batman.GrapplingHookRopeEntity;
import com.duzo.superhero.items.SuperheroWeaponItem;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.util.spiderman.SpiderManUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GrapplingHookWeaponItem extends SuperheroWeaponItem {
    public GrapplingHookWeaponItem(Properties properties, SuperheroCapability... requiredCapability) {
        super(properties, requiredCapability);
    }

    public GrapplingHookWeaponItem(Properties properties, SuperheroCapability requiredCapability) {
        super(properties, requiredCapability);
    }

    public GrapplingHookWeaponItem(Properties properties) {
        this(properties, SuperheroCapability.GRAPPLING_HOOK);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!canWeaponBeUsed(stack,player) || hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(this)) return InteractionResultHolder.pass(stack);

        BlockHitResult hit = SpiderManUtil.getPlayerPOVHitResult(level, player);
        BlockPos hitPos = hit.getBlockPos();
        BlockPos hitPosRelative = hitPos.relative(hit.getDirection());
        Vec3 hitVec3 = hitPosRelative.getCenter().relative(hit.getDirection().getOpposite(),0.45d);


        /*public static void rotatePlayerAroundEntity(Player player, Vec3 entityPosition, float radius) {
            Vec3 deltaPosition = player.position().subtract(entityPosition);

            double distance = deltaPosition.length();

            double yaw = Math.atan2(deltaPosition.z, deltaPosition.x);
            double pitch = Math.atan2(deltaPosition.y, distance);

            double newX = entityPosition.x + (radius * Math.cos(yaw) * Math.cos(pitch));
            double newY = entityPosition.y + (radius * Math.sin(pitch));
            double newZ = entityPosition.z + (radius * Math.sin(yaw) * Math.cos(pitch));

            Vec3 newPosition = new Vec3(newX, newY, newZ);

            // Calculate the delta movement
            Vec3 deltaMovement = newPosition.subtract(player.position()).normalize().multiply(0.4D, 0.4D, 0.4D);

            // Move the player relative to its current position
            player.setDeltaMovement(player.getDeltaMovement().add(deltaMovement.x, deltaMovement.y, deltaMovement.z));
        }*/

        if (level.getBlockState(hitPos).isAir()) return InteractionResultHolder.pass(stack);

        if (!level.isClientSide) {
            Vec3 look = player.getLookAngle().normalize().multiply(1f, 1f, 1f);

            GrapplingHookRopeEntity rope = new GrapplingHookRopeEntity(player.level(), hitVec3);

            int i = Mth.clamp(0, 0, 64);
            float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
            float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
            player.level().addFreshEntity(rope);
            rope.moveTo(player.getX() + (double) f2, player.getY() + (double) f6 + 1.25f, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
            rope.setPointsChanged();

            setMoveVector(stack, look);
        }
        return InteractionResultHolder.success(stack);
    }

    public static Vec3 getMoveVector(ItemStack stack) {
        if (stack.hasTag()) {
            if (!stack.getTag().contains("move")) return null;

            CompoundTag tag = stack.getTag().getCompound("move");
            return new Vec3(tag.getDouble("x"),tag.getDouble("y"),tag.getDouble("z"));
        }
        return null;
    };
    public static void setMoveVector(ItemStack stack, Vec3 move) {
        CompoundTag tag = new CompoundTag();

        tag.putDouble("x",move.x);
        tag.putDouble("y",move.y);
        tag.putDouble("z",move.z);

        stack.getOrCreateTag().put("move",tag);
    };

    public static void clearMoveVector(ItemStack stack) {
        if (stack.getOrCreateTag().contains("move")) {
            stack.getTag().remove("move");
        }
    }

    public static boolean isStackHeld(ItemStack stack, Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).equals(stack,false) || player.getItemInHand(InteractionHand.OFF_HAND).equals(stack,false);
    }
    public static boolean isStackHeld(ItemStack stack, Player player, InteractionHand hand) {
        return player.getItemInHand(hand).equals(stack,false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (level.isClientSide) {
            if (!(entity instanceof Player player)) return;

            if (!isStackHeld(stack, player,InteractionHand.MAIN_HAND)) clearMoveVector(stack);

            if (isStackHeld(stack, player, InteractionHand.MAIN_HAND) && getMoveVector(stack) != null) {
                player.getCooldowns().addCooldown(stack.getItem(),20);
//                Network.sendToPlayer(new ChangeDeltaMovementS2CPacket(getMoveVector(stack)), (ServerPlayer) player);
                player.setDeltaMovement(getMoveVector(stack));

                boolean flag = player.horizontalCollision || player.verticalCollision || player.verticalCollisionBelow;

                if (flag) {
                    clearMoveVector(stack);
                }
            }
        }
    }
}
