package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.entities.spiderman.WebRopeEntity;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.ChangeDeltaMovementS2CPacket;
import com.duzo.superhero.network.packets.SwingArmS2CPacket;
import com.duzo.superhero.particles.SuperheroParticles;
import com.duzo.superhero.sounds.SuperheroSounds;
import com.duzo.superhero.util.SuperheroCapability;
import com.duzo.superhero.util.spiderman.SpiderManIdentifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.items.spiderman.SpiderManNanotechItem.convertArmourToNanotech;

public class SpiderManArmourItem extends SuperheroArmourItem {
    public static final SpiderManIdentifier DEFAULT_ID = SpiderManIdentifier.MILES;
    private SpiderManIdentifier id;
    public SpiderManArmourItem(ArmorMaterial material, Type type, Properties properties, SpiderManIdentifier id) {
        super(material, type, properties);
        this.id = id;
    }
    public SpiderManArmourItem(ArmorMaterial material, Type type, Properties properties) {
        this(material,type,properties, DEFAULT_ID);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (this.getIdentifier() != null && Screen.hasShiftDown()) {
            components.add(Component.translatable(fileNameToUsable(this.getIdentifier().getSerializedName())).withStyle(ChatFormatting.GOLD));
        }

        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public String getShiftingHoverTextMessage() {
        StringBuilder base = new StringBuilder();

        for (SuperheroCapability capability : this.getIdentifier().getCapabilities()) {
            base.append("/n").append(fileNameToUsable(capability.getSerializedName()));
        }

        return base.toString();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);

        if (entity instanceof Player player) {
            if (!isEquipped(stack,player)) return;


            if (this.getEquipmentSlot() == EquipmentSlot.CHEST && isValidArmor(player)) {
                this.runEffects(player);
                this.runWallClimbs(player);
            }
        }
    }

    private void runEffects(Player player) {
        if (this.getIdentifier().getCapabilities().has(SuperheroCapability.SUPER_STRENGTH)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2 * 20, 1, false, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2 * 20, 1, false, false, false));
        }
        if (this.getIdentifier().getCapabilities().has(SuperheroCapability.FAST_MOBILITY)) {
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 2 * 20, 1, false, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2 * 20, 1, false, false, false));
        }
    }

    private void runWallClimbs(Player player) {
        if (this.getIdentifier().getCapabilities().has(SuperheroCapability.WALL_CLIMBING)) {
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
    }

    private static void stickToWall(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().x,0,player.getDeltaMovement().z);
    }

    private static void climbWall(Player player) {
        player.setDeltaMovement(player.getDeltaMovement().x,0.2,player.getDeltaMovement().z);
    }


    @Override
    public void runAbility(Player player, int number) {
        // Always server-side
        if (number == 1) {
            if (this.getIdentifier().getCapabilities().has(SuperheroCapability.WEB_SHOOTING)) {
                this.shootWebAndSwingToIt(player);
            }
        } else if (number == 2) {
            if (this.getIdentifier().getCapabilities().has(SuperheroCapability.INVISIBILITY)) {
                this.runMilesInvisibility(player);
            } else if (this.getIdentifier().getCapabilities().has(SuperheroCapability.NANOTECH)) {
                convertArmourToNanotech(player);
            }
        }
    }

    private void runMilesInvisibility(Player player) {
        if (!playerHasEffect(player,MobEffects.INVISIBILITY)) {
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120 * 20, 1, false, false, false));
        } else {
            player.removeEffect(MobEffects.INVISIBILITY);
        }
//        player.setInvisible(!player.isInvisible()); // This is good because its not an effect, but it causes more problems than it fixes
    }

    public static boolean playerHasEffect(Player player,MobEffect effect) {
        for (MobEffectInstance instance : player.getActiveEffects()) {
            if (instance.getEffect() == effect) return true;
        }
        return false;
    }

    private void shootWebAndSwingToIt(Player player) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(player.level,player);
        BlockPos hitPos = blockhitresult.getBlockPos();
        BlockPos hitPosRelative = hitPos.relative(blockhitresult.getDirection());
        Vec3 hitVec3 = hitPosRelative.getCenter().relative(blockhitresult.getDirection().getOpposite(),0.45d);

        Network.sendToPlayer(new SwingArmS2CPacket(InteractionHand.MAIN_HAND), (ServerPlayer) player);

        if (player.level.getBlockState(hitPos).isAir()) return;

        ((ServerLevel)player.level).sendParticles(SuperheroParticles.WEB_PARTICLES.get(),hitVec3.x(),hitVec3.y(),hitVec3.z(),1,0,0,0,0d);

        player.level.playSound(null,player, SuperheroSounds.SPIDERMAN_SHOOT.get(), SoundSource.PLAYERS,0.25f,1f);

        WebRopeEntity rope = new WebRopeEntity(player.level, hitVec3);

        int i = Mth.clamp(0, 0, 64);
        float f2 = Mth.cos(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f3 = Mth.sin(player.yBodyRot * ((float) Math.PI / 180F)) * (0F + 1.21F * (float) i);
        float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 0.0F);
        player.level.addFreshEntity(rope);
        rope.moveTo(player.getX() + (double) f2, player.getY() + (double) f6 + 1.25f, player.getZ() + (double) f3, player.getYRot(), player.getXRot());
        rope.setPointsChanged();
//        rope.setPointsChanged();

        Vec3 look = player.getLookAngle().normalize().multiply(2.5d, 2.5d, 2.5d);//.add(0,0.5d,0);
        Network.sendToPlayer(new ChangeDeltaMovementS2CPacket(look.add(player.getDeltaMovement().x,0,player.getDeltaMovement().z)), (ServerPlayer) player);
    }

    protected static BlockHitResult getPlayerPOVHitResult(Level level, Player player) {
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

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                SpiderManArmourItem item = (SpiderManArmourItem) itemStack.getItem(); // frick you craig im casting

                if (item.getIdentifier().isSlim()) {
                    AlexSkinModel<?> model = new AlexSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlexSkinModel.LAYER_LOCATION));

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

                    if (livingEntity.isInvisible()) model.setAllVisible(false);

                    return model;
                } else {
                    SteveSkinModel<?> model = new SteveSkinModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SteveSkinModel.LAYER_LOCATION));

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

                    if (livingEntity.isInvisible()) model.setAllVisible(false);

                    return model;
                }
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (entity.isInvisible()) return "superhero:textures/heroes/spider_man/invisible_skin.png";

        return "superhero:textures/heroes/spider_man/" + this.getIdentifier().getSerializedName() + ".png";
    }

    @Override
    public boolean isValidArmor(LivingEntity player) {
        SpiderManIdentifier currentMark = null;

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (!equipmentSlot.isArmor() || equipmentSlot == EquipmentSlot.HEAD) continue;
            ItemStack currentSlot = player.getItemBySlot(equipmentSlot);
            if (currentSlot.getItem() instanceof SpiderManArmourItem item) {
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

    public SpiderManIdentifier getIdentifier() {
        return this.id;
    }
}
