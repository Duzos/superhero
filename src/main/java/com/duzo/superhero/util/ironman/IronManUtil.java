package com.duzo.superhero.util.ironman;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.ids.SuperheroIdentifierRegistry;
import com.duzo.superhero.ids.impls.IronManIdentifier;
import com.duzo.superhero.items.SuperheroArmourItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

import static com.duzo.superhero.util.SuperheroUtil.doesResourceLocationExist;
import static com.duzo.superhero.util.SuperheroUtil.keyDown;

public class IronManUtil {
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk3.png");
    private static final ResourceLocation DEFAULT_LIGHTMAP = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk3_l.png");

    private static String getMarkNumberString(AbstractIdentifier identifier) {
        return identifier.getSerializedName().substring(identifier.getSerializedName().length() - 1);
    }
    public static int getMarkNumber(AbstractIdentifier identifier) {
        return Integer.parseInt(getMarkNumberString(identifier));
    }
    public static ResourceLocation getTextureFromID(AbstractIdentifier id) {
        String s = "textures/heroes/iron_man/mk" + getMarkNumberString(id) + ".png";
        if (!doesResourceLocationExist(s)) {
            return DEFAULT_TEXTURE;
        }
        return new ResourceLocation(Superhero.MODID,s);
    }
    public static ResourceLocation getLightMapFromID(AbstractIdentifier id) {
        String s = "textures/heroes/iron_man/mk" + getMarkNumberString(id) + "_l.png";
        if (!doesResourceLocationExist(s)) {
            return DEFAULT_LIGHTMAP;
        }
        return new ResourceLocation(Superhero.MODID,s);
    }
    public static boolean isIronManSuit(AbstractIdentifier id) {
        return id instanceof IronManIdentifier;
    }
    public static boolean isIronManSuit(ItemStack stack) {
        return isIronManSuit(stack.getItem());
    }
    public static boolean isIronManSuit(Item item) {
        if (item instanceof SuperheroArmourItem superheroArmourItem) {
            return isIronManSuit(superheroArmourItem.getIdentifier());
        }
        return false;
    }

    public static IronManIdentifier getIDFromMark(int mark) {
        for (RegistryObject<AbstractIdentifier> reg : SuperheroIdentifierRegistry.IDS.getEntries()) {
            AbstractIdentifier id = reg.get();
            if (isIronManSuit(id)) {
                if (((IronManIdentifier) id).mark() == mark) {
                    return (IronManIdentifier) id;
                }
            }
        }
        return null;
    }

    public static String getHoverTextName(AbstractIdentifier id) {
        return "Mark " + id.getSerializedName().substring(id.getSerializedName().length() - 1);
    }

    public static class FlightUtil {
        // Flight that only goes up
        public static void bootsOnlyFlight(Player player, IronManIdentifier mark) {
            if(keyDown(GLFW.GLFW_KEY_SPACE)) {
                Vec3 motion = player.getDeltaMovement();
                double currentAccel = mark.vertical() * (motion.y() < 0.3D ? 2.5D : 1.0D);
                player.setDeltaMovement(motion.x(), motion.y() + currentAccel, motion.z());
            }
        }

        // @TODO movement to left right and back
        public static void runFlight(Player player, IronManIdentifier mark) {
            Vec3 motion = player.getDeltaMovement();
            double currentAccel = mark.vertical() * (motion.y() < 0.3D ? 2.5D : 1.0D);

            createParticles(player);

            if (Minecraft.getInstance().player == null) return;

            if (canBlastOff(player)) {
                // @TODO hitbox code
                blastOff(player,mark.blast());
            } else if (Minecraft.getInstance().player.input.jumping) {
                verticalFlight(player, motion, currentAccel);
            }
        }

        public static void createParticles(Player player) {
            if (player.level().isClientSide) return;

            Random random = new Random();
            if(!player.onGround()) {
                if (canBlastOff(player)) {
                    int i = Mth.clamp(0, 0, 64);
                    float f2 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F)) * (0.1F + 1.21F * (float) i);
                    float f3 = Mth.sin(player.getYRot() * ((float) Math.PI / 180F)) * (0.1F + 1.21F * (float) i);
                    float f4 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F)) * (0.4F + 5.21F * (float) i);
                    float f5 = Mth.sin(player.getYRot() * ((float) Math.PI / 180F)) * (0.4F + 5.21F * (float) i);
                    float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 9.0F);
                    float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 9.0F);
                    float f8 = (0.3F * 0.45F) * ((float) i * 0.3F + 9.0F);
                    ServerLevel serverLevel = (ServerLevel) player.level();
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);

                    //Smoke on feet
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                    //Smoke on hands
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                    //Flames on feet
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                    //Flames on hands
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                    //Smoke on back thrusters
                    //serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f8, player.getZ() + (double) f3 - 2, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    //serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f7, player.getZ() - (double) f3 - 2, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
//
                    //Flames on back thrusters
                    //serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() + (double) f2, player.getY() + (double) f8, player.getZ() + (double) f3 - 2, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    //serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() - (double) f2, player.getY() + (double) f8, player.getZ() - (double) f3 - 2, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                } else if (keyDown(GLFW.GLFW_KEY_SPACE)) {
                    int i = Mth.clamp(0, 0, 64);
                    float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                    float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                    float f4 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                    float f5 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                    float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 1F);
                    float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 6F);
                    ServerLevel serverLevel = (ServerLevel) player.level();
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    //player.getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMOKE, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() + (double) f2, player.getY() + (double) f6, player.getZ() + (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() - (double) f2, player.getY() + (double) f6, player.getZ() - (double) f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() + (double) f4, player.getY() + (double) f7, player.getZ() + (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                    serverLevel.sendParticles(ParticleTypes.SMALL_FLAME, player.getX() - (double) f4, player.getY() + (double) f7, player.getZ() - (double) f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                }
            }
        }

        public static boolean canBlastOff(Player player) {
            if (Minecraft.getInstance().player == null) return false;
            Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
            if (!(isIronManSuit(chest))) return false;

            SuperheroArmourItem hero = (SuperheroArmourItem) chest;

            return Screen.hasControlDown() && Minecraft.getInstance().player.input.up && !player.onGround() && hero.getIdentifier().getCapabilities().has(SuperheroCapability.BLAST_OFF) && !player.isSwimming();
        }

        public static void blastOff(Player player,double factor) {
            Vec3 look = player.getLookAngle().normalize().multiply(factor, factor, factor);
            player.setDeltaMovement(look);
        }

        public static void verticalFlight(Player player, Vec3 motion, double vertAccel) {
            player.setDeltaMovement(motion.add(0, vertAccel,0));
        }

        public static void forwardFlight(Player player, Vec3 motion,double horizAccel, double vertAccel, boolean spaceHeld) {
            Vec3 movement = new Vec3(player.getLookAngle().x(), motion.y(),player.getLookAngle().z());

            if (spaceHeld) {
                if (!player.isSprinting()) {
                    movement = new Vec3(movement.x() / horizAccel, movement.y() + vertAccel, movement.z() / horizAccel);
                } else {
                    movement = new Vec3(movement.x() * horizAccel, movement.y() + vertAccel, movement.z() * horizAccel);
                }
            } else if (!player.onGround()) {
                if (!player.isSprinting()) {
                    movement = new Vec3(movement.x() / horizAccel, movement.y(), movement.z() / horizAccel);
                } else {
                    movement = new Vec3(movement.x() * horizAccel, movement.y(), movement.z() * horizAccel);
                }
            }

            if (!player.onGround()) {
                player.setDeltaMovement(movement);
            }
        }

        // @TODO this shit dont farting work, fuck this im making it so you cdan only move with W >:(
        public static void leftFlight(Player player, Vec3 motion, double vertAccel, boolean spaceHeld) {
            Vec3 movement = new Vec3(0, 0,vertAccel);

            if (spaceHeld) {
                if (!player.isSprinting()) {
                    movement = new Vec3(movement.x() / 2, movement.y() + vertAccel, movement.z() / 2);
                } else {
                    movement = new Vec3(movement.x() * 2, movement.y() + vertAccel, movement.z() * 2);
                }
            } else if (!player.onGround()) {
                if (!player.isSprinting()) {
                    movement = new Vec3(movement.x() / 2, movement.y(), movement.z() / 2);
                } else {
                    movement = new Vec3(movement.x() * 2, movement.y(), movement.z() * 2);
                }
            }

            if (!player.onGround()) {
                player.moveRelative(1f,movement);
            }
        }
    }
}
