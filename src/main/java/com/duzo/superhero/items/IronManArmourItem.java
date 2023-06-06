package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.Input;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public abstract class IronManArmourItem extends ArmorItem {
    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public abstract String getMark();
    public ResourceLocation getTexture() {
        return new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark() + ".png");
    }

    private static float changeValueByDirection(float val, Direction direction) {
        return switch(direction) {
            case DOWN, WEST, NORTH:
                yield val--;
            case UP, EAST, SOUTH:
                yield val++;
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {
            if (IronManEntity.isValidArmorButCooler(player)) {
                this.runFlight(player);
            }

            if (this.getEquipmentSlot() == EquipmentSlot.HEAD && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
//                if (!player.getActiveEffectsMap().containsKey(MobEffects.NIGHT_VISION)) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
//                }
            }
            if (this.getEquipmentSlot() == EquipmentSlot.FEET && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem && !IronManEntity.isValidArmorButCooler(player)) {
                bootsOnlyFlight(player);
            }
        }
    }

    // Flight that only goes up
    private void bootsOnlyFlight(Player player) {
        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_SPACE)) {
            Vec3 motion = player.getDeltaMovement();
            double motionY = motion.y();
            double currentAccel = 0.02D * (motionY < 0.3D ? 2.5D : 1.0D);
            player.setDeltaMovement(motion.x(), motion.y() + currentAccel, motion.z());
        }
    }

    // @TODO movement to left right and back
    private void runFlight(Player player) {
        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_SPACE)) {
            Vec3 motion = player.getDeltaMovement();
            double motionY = motion.y();
            double currentAccel = 0.02D * (motionY < 0.3D ? 2.5D : 1.0D);
            if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_W)) {
                if (!player.isSprinting()) {
                    player.setDeltaMovement(player.getLookAngle().x() / 2, motion.y() + currentAccel, player.getLookAngle().z() / 2);
                } else {
                    player.setDeltaMovement(player.getLookAngle().x() * 2, motion.y() + currentAccel, player.getLookAngle().z() * 2);
                }
            } else {
                player.setDeltaMovement(motion.x(),motion.y() + currentAccel,motion.z());
            }
        } else if (!player.isOnGround()) {
            Vec3 motion = player.getDeltaMovement();
            if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_W)) {
                if (!player.isSprinting()) {
                    player.setDeltaMovement(player.getLookAngle().x() / 2, motion.y(), player.getLookAngle().z() / 2);
                } else {
                    player.setDeltaMovement(player.getLookAngle().x() * 2, motion.y(), player.getLookAngle().z() * 2);
                }
            }
        }
    }
}
