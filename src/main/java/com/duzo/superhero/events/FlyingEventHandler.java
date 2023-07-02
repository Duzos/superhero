package com.duzo.superhero.events;

import com.duzo.superhero.items.SuperheroArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.canBlastOff;

@Mod.EventBusSubscriber
public class FlyingEventHandler {

    public boolean popPosing = false;

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        if (event.getEntity() != null) {
            AbstractClientPlayer playerEntity = (AbstractClientPlayer) event.getEntity();
            PlayerModel playerModel = event.getRenderer().getModel();

            ItemStack chest = playerEntity.getItemBySlot(EquipmentSlot.CHEST);

            if (!(chest.getItem() instanceof SuperheroArmourItem hero)) return;
            if (!hero.getIdentifier().isValidArmour(playerEntity)) return;


            if (canBlastOff(playerEntity)) {
                PoseStack pose = event.getPoseStack();
                pose.pushPose();
                float lax = 0;
                if (Math.abs(playerEntity.getDeltaMovement().x) * 10 > 1) {
                    lax = 90;

                    playerEntity.yBodyRot = 0;
                    playerModel.head.yRot = 0;

                    pose.translate(0.0F, 1.2F, 0.0F);
                    pose.mulPose(Axis.XP.rotationDegrees(lax));
                    pose.mulPose(Axis.ZP.rotationDegrees(playerEntity.getYRot()));
                    pose.mulPose(Axis.XP.rotationDegrees(playerEntity.getXRot()));
                    pose.translate(0.0F, -1.2F, 0.0F);
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
