package com.duzo.superhero.items.ironman;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.entities.SuperheroEntities;
import com.duzo.superhero.entities.UnibeamEntity;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.ironman.IronManCapabilities;
import com.duzo.superhero.util.ironman.IronManCapability;
import com.duzo.superhero.util.ironman.IronManMark;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static com.duzo.superhero.blocks.IronManSuitCaseBlock.convertArmourToSuitcase;
import static com.duzo.superhero.entities.IronManEntity.*;
import static com.duzo.superhero.entities.IronManEntity.spawnNew;
import static com.duzo.superhero.items.ironman.IronManNanotechItem.convertArmourToNanotech;
import static com.duzo.superhero.items.ironman.IronManNanotechItem.convertNanotechToArmour;

public class IronManArmourItem extends SuperheroArmourItem {
    private IronManMark mark;
    private List<UnibeamEntity> beamEntities = new ArrayList<>();

    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties, IronManMark mark) {
        super(material, type, properties);
        this.mark = mark;
    }
    public IronManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        this(material,type,properties,IronManEntity.DEFAULT_MARK);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getMark() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(fileNameToUsable(this.getMark().getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    public void setMark(IronManMark mark) {
        this.mark = mark;
    }

    public IronManMark getMark() {
        return this.mark;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(Superhero.MODID, "textures/entities/iron_man/" + this.getMark().getSerializedName() + ".png");
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
                    model.hat.setRotation(-1.5707964f,0,0);
                    model.head.visible = true;
                } else if (equipmentSlot == EquipmentSlot.CHEST) {
                    model.body.visible = true;
                    model.leftArm.visible = true;
                    model.rightArm.visible = true;
                    model.right_arm.getChild("rightArmFlame").visible = false;
                    model.left_arm.getChild("leftArmFlame").visible = false;
                } else if (equipmentSlot == EquipmentSlot.FEET || equipmentSlot == EquipmentSlot.LEGS) {
                    model.leftLeg.visible = true;
                    model.rightLeg.visible = true;
                    model.right_leg.getChild("rightLegFlame").visible = false;
                    model.left_leg.getChild("leftLegFlame").visible = false;
                }

                ForgeHooksClient.copyModelProperties(original,model);

                //model.setAllVisible(false);

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
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {
            if (!isEquipped(stack,player)) return;

            if (isValidArmor(player)) {
                this.runFlight(player);
            }

//            if (!hasBindingCurse(stack) && this.getMark().getCapabilities().has(IronManCapability.BINDING)) {
//                stack.enchant(Enchantments.BINDING_CURSE,1);
//            }

            if (this.getEquipmentSlot() == EquipmentSlot.HEAD && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 11 * 20, 1, false, false, false));
            }
            if (this.getEquipmentSlot() == EquipmentSlot.FEET && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
                if (!isValidArmor(player)) {
                    bootsOnlyFlight(player);
                }

                if (player.getY() > 185 && this.getMark().getCapabilities().has(IronManCapability.ICES_OVER)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 2 * 20, 0, false, false, false));
                }
            }
        }
    }

    @Override
    public boolean isValidArmor(LivingEntity player) {
        IronManMark currentMark = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor()) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof IronManArmourItem item) {
                if (currentMark == null) {
                    currentMark = item.getMark();
                } else if (!currentMark.equals(item.getMark())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void runAbility(Player player,int number) {
        if (number == 1) {
            this.runAbilityOne(player);
        } else if (number == 2) {
            this.runAbilityTwo(player);
        } else if (number == 4) {
            this.runAbilityFour();
        }
    }

    public void runAbilityOne(Player player) {
        Level level = player.getLevel();
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof IronManNanotechItem) {
            convertNanotechToArmour(chest,player);
            level.playSound(null,player, SuperheroSounds.IRONMAN_POWERUP.get(), SoundSource.PLAYERS,1f,1f);
            return;
        }

        ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
        IronManArmourItem item = (IronManArmourItem) head.getItem();
        IronManCapabilities cap = item.getMark().getCapabilities();


        if (!isValidArmor(player)) return;

        if (cap.has(IronManCapability.SUITCASE)) {
            convertArmourToSuitcase(player);
            level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
        } else if (cap.has(IronManCapability.SEAMLESS)) {
            spawnNew(item.getMark(),level,player.getOnPos(),player);
            level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
        } else if (cap.has(IronManCapability.BRACELET_LOCATING)) {
            spawnNew(item.getMark(),level,player.getOnPos(),player); // @TODO temporarily the same as seamless until i make something new for it
            level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
        } else if (cap.has(IronManCapability.NANOTECH)) {
            convertArmourToNanotech(player);
            level.playSound(null,player, SuperheroSounds.IRONMAN_POWERDOWN.get(), SoundSource.PLAYERS,1f,1f);
        }
    }
    public void runAbilityTwo(Player player) {
        /*SmallFireball fireball = new SmallFireball(player.level,player,player.getXRot(),player.getYRot(),player.getZ());
        fireball.shootFromRotation(player,player.getXRot(),player.getYRot(),0,1.5f,0);
        player.level.addFreshEntity(fireball);*/
        int i = Mth.clamp(0, 0, 64);
        float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
        UnibeamEntity unibeam = new UnibeamEntity(SuperheroEntities.UNIBEAM_ENTITY.get(), player.getLevel());
        unibeam.moveTo(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
        player.getLevel().addFreshEntity(unibeam);
        //unibeam.setPos(player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3);
            //unibeam.setYRot(player.getYRot());
            //unibeam.setXRot(player.getXRot());
        this.beamEntities.add(unibeam);
    }

    public boolean runAbilityFour() {
        return true;
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

        createParticles(player);

        if (Minecraft.getInstance().player == null) return;

        if (canBlastOff(player)) {
            // @TODO hitbox code
            blastOff(player,this.getMark().getBlastOffSpeed());
        } else if (Minecraft.getInstance().player.input.jumping) {
            verticalFlight(player, motion, currentAccel);
        }
    }

    public static void createParticles(Player player) {
        Random random = new Random();
        if(!player.isOnGround()) {
            if (canBlastOff(player)) {
                int i = Mth.clamp(0, 0, 64);
                float f2 = Mth.cos(player.getYHeadRot() * ((float) Math.PI / 180F)) * (0.1F + 1.21F * (float) i);
                float f3 = Mth.sin(player.getYHeadRot() * ((float) Math.PI / 180F)) * (0.1F + 1.21F * (float) i);
                float f4 = Mth.cos(player.getYHeadRot() * ((float) Math.PI / 180F)) * (0.4F + 5.21F * (float) i);
                float f5 = Mth.sin(player.getYHeadRot() * ((float) Math.PI / 180F)) * (0.4F + 5.21F * (float) i);
                float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 9.0F);
                float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 9.0F);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
            } else if (keyDown(GLFW.GLFW_KEY_SPACE)) {
                int i = Mth.clamp(0, 0, 64);
                float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                float f4 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                float f5 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 1F);
                float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 6F);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
            }
        }
    }



    public static boolean canBlastOff(Player player) {
        if (Minecraft.getInstance().player == null) return false;
        if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem hero)) return false;

        return Screen.hasControlDown() && Minecraft.getInstance().player.input.up && !player.isOnGround() && hero.getMark().getCapabilities().has(IronManCapability.BLAST_OFF) && !player.isSwimming();
    }

    private void blastOff(Player player,double factor) {
        Vec3 look = player.getLookAngle().normalize().multiply(factor, factor, factor);;
        player.setDeltaMovement(look);
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
}
