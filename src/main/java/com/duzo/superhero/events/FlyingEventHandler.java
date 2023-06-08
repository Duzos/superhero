package com.duzo.superhero.events;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.IronManArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static com.duzo.superhero.items.IronManArmourItem.keyDown;

@Mod.EventBusSubscriber
public class FlyingEventHandler {

    public boolean popPosing = false;

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        if (event.getEntity() != null) {
            Player playerEntity = (Player) event.getEntity();
            PlayerModel playerModel = event.getRenderer().getModel();
            Item chest = playerEntity.getItemBySlot(EquipmentSlot.CHEST).getItem();
            Item legs = playerEntity.getItemBySlot(EquipmentSlot.LEGS).getItem();
            Item feet = playerEntity.getItemBySlot(EquipmentSlot.FEET).getItem();

            if (!playerEntity.isOnGround()
                    && chest instanceof IronManArmourItem
                    && legs instanceof IronManArmourItem
                    && feet instanceof IronManArmourItem) {
                PoseStack pose = event.getPoseStack();
                pose.pushPose();
                float lax = 0;
                if (Math.abs(playerEntity.getDeltaMovement().x) * 10 > 0) {
                    lax = 90;
                    playerEntity.yBodyRot = 0;
                    playerModel.head.xRot = 0;
                    playerModel.head.yRot = 0;
                    playerModel.head.zRot = 0;
                    pose.mulPose(Axis.XP.rotationDegrees(lax));
                    pose.mulPose(Axis.ZP.rotationDegrees(playerEntity.getYRot()));
                    pose.translate(0.0F, -1.2F, 0.0F);
                    playerModel.body.setRotation(0, 0, 0);
                    playerModel.head.setRotation(0, 0, 0);
                    playerModel.leftArm.setRotation(0, 0, 0);
                    playerModel.leftLeg.setRotation(0, 0, 0);
                    playerModel.rightArm.setRotation(0, 0, 0);
                    playerModel.rightLeg.setRotation(0, 0, 0);
                } else {
                    lax = 0;
                }
                this.popPosing = true;
            }
        }
    }

    @SubscribeEvent
    public void onRenderPost(RenderPlayerEvent.Post event) {
        if(this.popPosing) {
            this.popPosing = false;
            event.getPoseStack().popPose();
        }
    }
}
