package com.duzo.superhero.util.spiderman;

import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import com.duzo.superhero.items.spiderman.SpiderManNanotechItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.ChangeDeltaMovementS2CPacket;
import com.duzo.superhero.network.packets.SwingArmS2CPacket;
import com.duzo.superhero.particles.SuperheroParticles;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.KeyBinds;
import com.duzo.superhero.util.SuperheroIdentifier;
import com.duzo.superhero.util.SuperheroUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.duzo.superhero.util.SuperheroUtil.playerHasEffect;

public class SpiderManUtil {

    public static void runWallClimbs(Player player) {
        if (Minecraft.getInstance().player != null) {
            LocalPlayer clientPlayer = Minecraft.getInstance().player;
            if (clientPlayer.horizontalCollision) {
                if (clientPlayer.isCrouching()) {
                    stickToWall(player);
                } else if (clientPlayer.input.jumping) {
                    climbWall(player);
                }
            }
        }
    }

    public static void stickToWall(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().x,0,player.getDeltaMovement().z);
    }

    public static void climbWall(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().x,0.4, player.getDeltaMovement().z);
    }

    public static void runMilesInvisibility(Player player) {
        if (!playerHasEffect(player, MobEffects.INVISIBILITY)) {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120 * 20, 1, false, false, false));
        } else {
            player.removeEffect(MobEffects.INVISIBILITY);
        }
//        player.setInvisible(!player.isInvisible()); // This is good because its not an effect, but it causes more problems than it fixes
    }

    public static void shootWebAndSwingToIt(Player player) {
        if (KeyBinds.ABILITY_ONE.isDown() && !(canPlayerShootRope(player))) {
            return;
        }


        BlockHitResult blockhitresult = getPlayerPOVHitResult(player.level,player);
        BlockPos hitPos = blockhitresult.getBlockPos();
        BlockPos hitPosRelative = hitPos.relative(blockhitresult.getDirection());
        Vec3 hitVec3 = hitPosRelative.getCenter().relative(blockhitresult.getDirection().getOpposite(),0.45d);

        Network.sendToPlayer(new SwingArmS2CPacket(InteractionHand.MAIN_HAND), (ServerPlayer) player);

        if (player.level.getBlockState(hitPos).isAir()) return;

        ((ServerLevel)player.level).sendParticles(SuperheroParticles.WEB_PARTICLES.get(),hitVec3.x(),hitVec3.y(),hitVec3.z(),1,0,0,0,0d);

        player.level.playSound(null,player, SuperheroSounds.SPIDERMAN_SHOOT.get(), SoundSource.PLAYERS,0.25f,1f);

        WebRopeEntity rope = new WebRopeEntity(player.level, hitVec3, player);

        //int i = Mth.clamp(0, 0, 64);
        //float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        //float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        //float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 0F * (float) i);
        //float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 0F * (float) i);
        //float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
        player.level.addFreshEntity(rope);
        //BlockPos hitPosition = hit.getBlockPos();
        if(player.level.getBlockState(hitPos).isAir()) return;
        rope.moveTo(hitVec3);
        rope.setYBodyRot(player.getYRot());
        rope.setXRot(player.getXRot());
        //rope.moveTo(player.getX() + (double) f2, player.getY() + (double) f6 + 1.25f, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
        rope.setPointsChanged();

        //Vec3 look = player.getLookAngle().normalize().multiply(2.5d, 2.5d, 2.5d);//.add(0,0.5d,0);
        //Network.sendToPlayer(new ChangeDeltaMovementS2CPacket(look.add(player.getDeltaMovement().x,0,player.getDeltaMovement().z)), (ServerPlayer) player);
    }

    public static void shootWebAndZipToIt(Player player) {
        if (KeyBinds.ABILITY_TWO.isDown() && !(canPlayerShootRope(player))) {
            return;
        }
        BlockHitResult blockhitresult = getPlayerPOVHitResult(player.level,player);
        BlockPos hitPos = blockhitresult.getBlockPos();
        BlockPos hitPosRelative = hitPos.relative(blockhitresult.getDirection());
        Vec3 hitVec3 = hitPosRelative.getCenter().relative(blockhitresult.getDirection().getOpposite(),0.45d);
        Network.sendToPlayer(new SwingArmS2CPacket(InteractionHand.MAIN_HAND), (ServerPlayer) player);
        if (player.level.getBlockState(hitPos).isAir()) return;
        ((ServerLevel)player.level).sendParticles(SuperheroParticles.WEB_PARTICLES.get(),hitVec3.x(),hitVec3.y(),hitVec3.z(),1,0,0,0,0d);
        player.level.playSound(null,player, SuperheroSounds.SPIDERMAN_SHOOT.get(), SoundSource.PLAYERS,0.25f,1f);
        WebRopeEntity rope = new WebRopeEntity(player.level, hitVec3, player);
        player.level.addFreshEntity(rope);
        if(player.level.getBlockState(hitPos).isAir()) return;
        rope.moveTo(hitVec3);
        rope.setYBodyRot(player.getYRot());
        rope.setXRot(player.getXRot());
        rope.setPointsChanged();
        Vec3 look = player.getLookAngle().normalize().multiply(4d, 4d, 4d);//.add(0,0.5d,0);
        Network.sendToPlayer(new ChangeDeltaMovementS2CPacket(look.add(player.getDeltaMovement().x,0,player.getDeltaMovement().z)), (ServerPlayer) player);
    }

    public static boolean canPlayerShootRope(Player player) {
        WebRopeEntity rope = getPlayersRope(player);
        if (rope == null) return true;
        return rope.getAlpha() < 0.5f;
    }
    public static boolean ropeExistsForPlayer(Player player) {
        return getPlayersRope(player) != null;
    }
    public static WebRopeEntity getPlayersRope(Player player) {
        if (!(player.level instanceof ServerLevel serverLevel)) return null;
        for (Entity entity : serverLevel.getAllEntities()) {
            if (!(entity instanceof WebRopeEntity rope)) continue;

            if (rope.getAlpha() < 0.85f) continue;
            if (rope.getPlayer() == player) return rope;
        }
        return null;
    }

    /**
     * Use {@link SuperheroUtil#putSuitOntoPlayer(SuperheroIdentifier, Player, boolean)}#
     **/
    @Deprecated
    public static boolean equipSpiderSuitForID(SuperheroIdentifier id, Player player, boolean excludeNanotech) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            if (!player.getItemBySlot(slot).isEmpty()) {
                if (excludeNanotech && slot == EquipmentSlot.CHEST) {
                    if (player.getItemBySlot(slot).getItem() instanceof SpiderManNanotechItem) {
                        continue;
                    }
                } else {
                    return false;
                }
            }
        }

        List<ArmorItem> armour = new ArrayList<>();
        for (RegistryObject<Item> item : SuperheroItems.ITEMS.getEntries()) {
            if (item.getId().toString().contains(id.getSerializedName())) {
                armour.add((ArmorItem) item.get());
            }
        }

        if (armour.isEmpty()) return false;

        for (ArmorItem item : armour) {
            player.setItemSlot(item.getEquipmentSlot(), new ItemStack(item));
        }

        return true;
    }

    /**
     * Doesnt need the helmet to be on to be valid
     * @param player
     * @return
     */
    public static boolean isValidArmor(LivingEntity player) {
        SuperheroIdentifier currentMark = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.HEAD) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof SuperheroArmourItem item) {
                if (currentMark == null) {
                    currentMark = item.getIdentifier();
                } else if (!currentMark.equals(item.getIdentifier())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
    public static BlockHitResult getPlayerPOVHitResult(Level level, Player player) {
        float f = player.getXRot();
        float f1 = player.getYRot();
        Vec3 vec3 = player.getEyePosition();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = player.getBlockReach() * 25;
        Vec3 vec31 = vec3.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return level.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}
