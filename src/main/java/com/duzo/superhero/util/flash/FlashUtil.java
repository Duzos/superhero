package com.duzo.superhero.util.flash;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapabilityRegistry;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.SyncSpeedsterDataS2CPacket;
import com.duzo.superhero.util.player.IEntityDataSaver;
import com.duzo.superhero.util.player.PlayerDataUtil;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class FlashUtil {
    public static final SuperheroCapabilities DEFAULT_CAPABILITIES = new SuperheroCapabilities(SuperheroCapabilityRegistry.SPEEDSTER,SuperheroCapabilityRegistry.FLASH_HUD);

    public static final UUID SPEED_UUID = UUID.fromString("fff934a3-13f8-47c6-877d-7067fb9a842f");

    public static double syncSpeed(Player player) {
        if (!player.level().isClientSide) {
            Network.sendToPlayer(new SyncSpeedsterDataS2CPacket(player.getPersistentData().getDouble("speedster.speed")), (ServerPlayer) player);
        }
        return player.getPersistentData().getDouble("speedster.speed");
    }
    public static double getSpeed(Entity entity) {
        CompoundTag data = PlayerDataUtil.getCustomData((IEntityDataSaver) entity);

        if (data.contains("speedster.speed")) {
            return data.getDouble("speedster.speed");
        }
        LogUtils.getLogger().error("Speed value was null, returning a default of 1 and attempting a resync!");

        if (entity instanceof Player player) {
            syncSpeed(player);
        }

        return 1.0D;
    }
    public static double setSpeed(Entity entity, double val) {
        CompoundTag data = PlayerDataUtil.getCustomData((IEntityDataSaver) entity);

        double clamped = Mth.clamp(val,1,50);

        PlayerDataUtil.insertData((IEntityDataSaver) entity,clamped,"speedster.speed");

        if (entity instanceof Player player) {
            syncSpeed(player);
        }

        return data.getDouble("speedster.speed");
    }

    /**
     *
     * @param entity
     * @param increment The value to change it by (can be negative)
     * @return The new speed
     */
    public static double changeSpeed(Entity entity, double increment) {
        double speed = FlashUtil.getSpeed(entity);
        return setSpeed(entity,speed + increment);
    }
    public static double changeSpeed(Entity entity, boolean negative) {
        if (negative) {
            return changeSpeed(entity,-getIncrement(entity,negative));
        } else {
            return changeSpeed(entity,getIncrement(entity,negative));
        }
    }
    public static double changeSpeed(Entity entity) {
        return changeSpeed(entity,false);
    }

    public static double getIncrement(Entity entity,boolean negative) {
        double speed = getSpeed(entity);

        if (speed < 3) {
            return 0.25;
        }
        if (speed >= 3 && speed < 10) {
            return 1;
        }
        if (speed == 10 && negative) {
            return 1;
        }
        if (speed >= 10) {
            return 5;
        }
        return 1;
    }

    public static void modifyPlayerSpeed(Player player) {
        if (player.level().isClientSide) {
//            System.out.println(player.getDeltaMovement().multiply(getSpeed(player),1,getSpeed(player)));
            // Dont even ask me what this is

            if (getSpeed(player) == 1) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_UUID);
                return;
            }

            if (getSpeed(player) > 6) {
                walkOnWater(player);
            }

            AttributeModifier mod = createModifier(SPEED_UUID,"Speedster speed", getSpeed(player), AttributeModifier.Operation.MULTIPLY_TOTAL);
            if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(mod)) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(mod);
            } else {
                AttributeModifier existing = player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(SPEED_UUID);
                if (existing.getAmount() != mod.getAmount()) {
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_UUID);
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(mod);
                }
            }
        }
    }
    // @TODO needs work.
    public static void walkOnWater(Player player) {
        if (player.level().isClientSide) {
            if (Minecraft.getInstance().player.input.up) {
                if (player.level().getBlockState(player.getOnPos()).is(Blocks.WATER)) {
                    Vec3 delta = player.getDeltaMovement();
                    if (delta.y < 0) {
//                        player.setDeltaMovement(delta.add(1 + (getSpeed(player)/10),0,1+(getSpeed(player)/10)).multiply(1,0,1));
                        player.setDeltaMovement(delta.multiply(0.6 + (getSpeed(player) / 100),0,0.6 + (getSpeed(player) / 100)));
                    }
                }
            }
        }
    }

    public static void removeFlashSpeed(Player player) {
        AttributeModifier mod = createSpeedModifier(player);
        if (player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(mod)) {
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(mod);
        }
    }

    /**
     * Dunno why i made this
     * @param uuid
     * @param description
     * @param amount
     * @param operation
     * @return
     */
    public static AttributeModifier createModifier(UUID uuid,String description, double amount,AttributeModifier.Operation operation) {
        AttributeModifier mod = new AttributeModifier(uuid,description,amount,operation);
        return mod;
    }
    public static AttributeModifier createSpeedModifier(Entity entity) {
        return createModifier(SPEED_UUID,"Speedster speed", getSpeed(entity), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
