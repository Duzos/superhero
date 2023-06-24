package com.duzo.superhero.items.spiderman;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.ChangeDeltaMovementS2CPacket;
import com.duzo.superhero.network.packets.SwingArmS2CPacket;
import com.duzo.superhero.util.spiderman.SpiderManIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

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
    public void runAbility(Player player, int number) {
        if (number == 1) {
            this.shootWebAndSwingToIt(player);
        }
    }

    private void shootWebAndSwingToIt(Player player) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(player.level,player);
        BlockPos hitPos = blockhitresult.getBlockPos();
        BlockPos hitPosRelative = hitPos.relative(blockhitresult.getDirection());

        Network.sendToPlayer(new SwingArmS2CPacket(InteractionHand.MAIN_HAND), (ServerPlayer) player);

        if (player.level.getBlockState(hitPos).isAir()) return;

        Vec3 look = player.getLookAngle().normalize().multiply(2.5d, 3.5d, 2.5d);//.add(0,0.5d,0);
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

                    return model;
                }
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "superhero:textures/heroes/spider_man/" + this.getIdentifier().getSerializedName() + ".png";
    }

    public SpiderManIdentifier getIdentifier() {
        return this.id;
    }
}
