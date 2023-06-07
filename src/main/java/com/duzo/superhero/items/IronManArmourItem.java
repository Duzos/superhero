package com.duzo.superhero.items;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.network.packets.TakeOffIronManSuitC2SPacket;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.sounds.ThrustersSoundInstance;
import com.duzo.superhero.util.IronManCapability;
import com.duzo.superhero.util.IronManMark;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.duzo.superhero.entities.IronManEntity.fileNameToUsable;

public abstract class IronManArmourItem extends ArmorItem {
    private ThrustersSoundInstance sound;
    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getMark() != null) {
            components.add(Component.translatable(fileNameToUsable(this.getMark().getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public abstract IronManMark getMark();
    public ResourceLocation getTexture() {
        return new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark().getSerializedName() + ".png");
    }
    private static boolean isEquipped(ItemStack stack,Player player) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;

            if (player.getItemBySlot(equipmentSlot) == stack) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                IronManArmourModel<?> model = new IronManArmourModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(IronManArmourModel.LAYER_LOCATION));
                model.setAllVisible(false);

                if (equipmentSlot == EquipmentSlot.HEAD) {
                    model.hat.visible = true;
                    model.head.visible = true;
                } else if (equipmentSlot == EquipmentSlot.CHEST) {
                    model.body.visible = true;
                    model.leftArm.visible = true;
                    model.rightArm.visible = true;
                } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                    model.leftLeg.visible = true;
                    model.rightLeg.visible = true;
                }

                ForgeHooksClient.copyModelProperties(original,model);

                model.setAllVisible(false);

                return model;
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/entities/iron_man/" + this.getMark().getSerializedName().toLowerCase() + ".png";
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {
            if (!isEquipped(stack,player)) return;

            if (IronManEntity.isValidArmorButCooler(player)) {
                this.runFlight(player);
            }

            if (this.getEquipmentSlot() == EquipmentSlot.HEAD && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
            }
            if (this.getEquipmentSlot() == EquipmentSlot.FEET && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
                if (!IronManEntity.isValidArmorButCooler(player)) {
                    bootsOnlyFlight(player);
                }

                if (player.getY() > 185 && this.getMark().getCapabilities().has(IronManCapability.ICES_OVER)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 2 * 20, 0, false, false, false));
                }
            }
        }
    }

    // Flight that only goes up
    private void bootsOnlyFlight(Player player) {
        if(keyDown(GLFW.GLFW_KEY_SPACE)) {
            Vec3 motion = player.getDeltaMovement();
            double currentAccel = this.getMark().getVerticalFlightSpeedForBoots() * (motion.y() < 0.3D ? 2.5D : 1.0D);
            player.setDeltaMovement(motion.x(), motion.y() + currentAccel, motion.z());
        }
    }

    // @TODO movement to left right and back
    private void runFlight(Player player) {
        Vec3 motion = player.getDeltaMovement();
        double currentAccel = this.getMark().getVerticalFlightSpeed() * (motion.y() < 0.3D ? 2.5D : 1.0D);
        double horizAccel = this.getMark().getHorizontalFlightSpeed(player.isSprinting());
        boolean spaceHeld = keyDown(GLFW.GLFW_KEY_SPACE);

        if (keyDown(GLFW.GLFW_KEY_W)) {
            forwardFlight(player,motion,horizAccel,currentAccel,spaceHeld);
        } else if (keyDown(GLFW.GLFW_KEY_SPACE)) {
            verticalFlight(player,motion,currentAccel);
        }
    }

    private void verticalFlight(Player player, Vec3 motion, double vertAccel) {
        player.setDeltaMovement(motion.add(0, vertAccel,0));
    }

    private void forwardFlight(Player player, Vec3 motion,double horizAccel, double vertAccel, boolean spaceHeld) {
        Vec3 movement = new Vec3(player.getLookAngle().x(), motion.y(),player.getLookAngle().z());

        if (spaceHeld) {
            if (!player.isSprinting()) {
                movement = new Vec3(movement.x() / horizAccel, movement.y() + vertAccel, movement.z() / horizAccel);
            } else {
                movement = new Vec3(movement.x() * horizAccel, movement.y() + vertAccel, movement.z() * horizAccel);
            }
        } else if (!player.isOnGround()) {
            if (!player.isSprinting()) {
                movement = new Vec3(movement.x() / horizAccel, movement.y(), movement.z() / horizAccel);
            } else {
                movement = new Vec3(movement.x() * horizAccel, movement.y(), movement.z() * horizAccel);
            }
        }

        if (!player.isOnGround()) {
            player.setDeltaMovement(movement);
        }
    }

    // @TODO this shit dont farting work, fuck this im making it so you cdan only move with W >:(
    private void leftFlight(Player player, Vec3 motion, double vertAccel, boolean spaceHeld) {
        Vec3 movement = new Vec3(0, 0,vertAccel);

        if (spaceHeld) {
            if (!player.isSprinting()) {
                movement = new Vec3(movement.x() / 2, movement.y() + vertAccel, movement.z() / 2);
            } else {
                movement = new Vec3(movement.x() * 2, movement.y() + vertAccel, movement.z() * 2);
            }
        } else if (!player.isOnGround()) {
            if (!player.isSprinting()) {
                movement = new Vec3(movement.x() / 2, movement.y(), movement.z() / 2);
            } else {
                movement = new Vec3(movement.x() * 2, movement.y(), movement.z() * 2);
            }
        }

        if (!player.isOnGround()) {
            player.moveRelative(1f,movement);
        }
    }

    private static boolean keyDown(int key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key);
    }
}
