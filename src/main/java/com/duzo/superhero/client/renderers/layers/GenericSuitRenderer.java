package com.duzo.superhero.client.renderers.layers;

import com.duzo.superhero.client.models.AlexSkinModel;
import com.duzo.superhero.client.models.SkinModel;
import com.duzo.superhero.client.models.SteveSkinModel;
import com.duzo.superhero.client.models.SuperheroModels;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.SuperheroIdentifier;
import com.duzo.superhero.util.SuperheroUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.duzo.superhero.client.models.SuperheroModels.getRoot;
import static com.duzo.superhero.util.SuperheroUtil.getIDFromPlayer;

@OnlyIn(Dist.CLIENT)
public class GenericSuitRenderer<T extends AbstractClientPlayer, M extends EntityModel<T>> extends RenderLayer<T, M> {


    public SkinModel model;
    public ResourceLocation texture;
    public ResourceLocation lightmap;

    public GenericSuitRenderer(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.model = new SteveSkinModel(getRoot(SuperheroModels.STEVE));
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int packedLight, T player, float q, float w, float e, float r, float t, float y) {
        /*pose.pushPose();
        this.getParentModel().copyPropertiesTo(this.ironman);
        Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        if (head instanceof IronManArmourItem) {
            this.ironman.head.visible = true;
            this.ironman.head.getChild("bone2").visible = true;
            this.ironman.head.getChild("helmet").visible = true;
        } else {
            this.ironman.head.visible = false;
            this.ironman.head.getChild("bone2").visible = false;
            this.ironman.head.getChild("helmet").visible = false;
        }
        if (chest instanceof IronManArmourItem) {
            this.ironman.body.visible = true;
            this.ironman.body.getChild("powerthing").visible = true;
            this.ironman.rightArm.visible = true;
            this.ironman.rightArm.getChild("right_beam").visible = true;
            //this.ironman.bone.getChild("RightArm").getChild("rocket").visible = true;
            this.ironman.leftArm.visible = true;
            this.ironman.leftArm.getChild("left_beam").visible = true;
            //this.ironman.bone.getChild("LeftArm").getChild("rocket2").visible = true;
        } else {
            this.ironman.body.visible = false;
            this.ironman.body.getChild("powerthing").visible = false;
            this.ironman.rightArm.visible = false;
            this.ironman.rightArm.getChild("right_beam").visible = false;
//this.ironman.bone.getChild("RightArm").getChild("rocket").visible = false;
            this.ironman.leftArm.visible = false;
            this.ironman.leftArm.getChild("left_beam").visible = false;
//this.ironman.bone.getChild("LeftArm").getChild("rocket2").visible = false;
        }
        if (legs instanceof IronManArmourItem) {
            this.ironman.leftLeg.visible = true;
            this.ironman.rightLeg.visible = true;
        } else {
            this.ironman.leftLeg.visible = false;
            this.ironman.rightLeg.visible = false;
        }
        if (feet instanceof IronManArmourItem) {
            this.ironman.leftLeg.getChild("LeftFoot").visible = true;
            this.ironman.rightLeg.getChild("RightFoot").visible = true;
            //this.ironman.bone.getChild("LeftLeg").getChild("LeftFoot").getChild("foot_left_beam").visible = true;
            //this.ironman.bone.getChild("RightLeg").getChild("RightFoot").getChild("foot_right_beam").visible = true;
        } else {
            this.ironman.leftLeg.getChild("LeftFoot").visible = false;
            this.ironman.rightLeg.getChild("RightFoot").visible = false;
        }
        VertexConsumer solid = buffer.getBuffer(RenderType.entitySolid(this.texture));
        VertexConsumer lightmap = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.lightmap, false));
        this.ironman.renderToBuffer(pose, solid, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        //this.ironman.renderToBuffer(pose, lightmap, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pose.popPose();*/
        PlayerModel playerModel = (PlayerModel) this.getParentModel();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "head");
        //this.ironman.head.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "body");
        //this.ironman.body.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "rightArm");
        //this.ironman.rightArm.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "leftArm");
        //this.ironman.leftArm.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "rightLeg");
        //this.ironman.rightLeg.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        //pose.pushPose();
        //copyPlayerMovement(playerModel, this.ironman.head, "leftLeg");
        //this.ironman.leftLeg.render(pose, buffer.getBuffer(RenderType.entityCutout(this.texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        //pose.popPose();
        if (!usesGenericRenderer(player)) return;
        pose.pushPose();

        updateTextures(player);
        updateSlim(player);

        copyPlayersMovement(playerModel, this.model);
        this.model.setupAnim(player,w,e,r,t,y);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(this.texture));
//        VertexConsumer emission = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.lightmap)); // @TODO fix emission
        Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        if(head instanceof SuperheroArmourItem) {
            this.model.head.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.model.hat.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            if (!isInvisibleTexture(this.lightmap)) {
//                this.model.head.render(pose, emission, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            }
        }
        if(chest instanceof SuperheroArmourItem) {
            this.model.right_arm.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.model.left_arm.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.model.body.render(pose,vertexConsumer,packedLight,OverlayTexture.NO_OVERLAY,1,1,1,1);
//            if (!isInvisibleTexture(this.lightmap)) {
//                this.model.rightArm.render(pose, emission, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//                this.model.leftArm.render(pose, emission, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            }
        }
        if(legs instanceof SuperheroArmourItem) {
            this.model.right_leg.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.model.left_leg.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            if (!isInvisibleTexture(this.lightmap)) {
//                this.model.rightLeg.render(pose, emission, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//                this.model.leftLeg.render(pose, emission, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
//            }
        }
        pose.popPose();
    }

    private boolean usesGenericRenderer(Player player) {
        // Get the correct texture
        SuperheroIdentifier id = getIDFromPlayer(player);
        if (id == null) return false;
        return id.usesDefaultRenderer();
    }

    private void copyPlayersMovement(PlayerModel pm, SkinModel mp) {
        mp.head.setPos(pm.head.x, pm.head.y, pm.head.z);
        mp.head.setRotation(pm.head.xRot, pm.head.yRot, pm.head.zRot);
        mp.hat.setPos(pm.hat.x, pm.hat.y, pm.hat.z); // @TODO fix hat position for steve model
        mp.hat.setRotation(pm.hat.xRot, pm.hat.yRot, pm.hat.zRot);
        mp.body.setPos(pm.body.x, pm.body.y, pm.body.z);
        mp.body.setRotation(pm.body.xRot, pm.body.yRot, pm.body.zRot);
        mp.right_arm.setPos(pm.rightArm.x, pm.rightArm.y, pm.rightArm.z);
        mp.right_arm.setRotation(pm.rightArm.xRot, pm.rightArm.yRot, pm.rightArm.zRot);
        mp.left_arm.setPos(pm.leftArm.x, pm.leftArm.y, pm.leftArm.z);
        mp.left_arm.setRotation(pm.leftArm.xRot, pm.leftArm.yRot, pm.leftArm.zRot);
        mp.right_leg.setPos(pm.rightLeg.x, pm.rightLeg.y, pm.rightLeg.z);
        mp.right_leg.setRotation(pm.rightLeg.xRot, pm.rightLeg.yRot, pm.rightLeg.zRot);
        mp.left_leg.setPos(pm.leftLeg.x, pm.leftLeg.y, pm.leftLeg.z);
        mp.left_leg.setRotation(pm.leftLeg.xRot, pm.leftLeg.yRot, pm.leftLeg.zRot);
    }

    private void updateTextures(Player player) {
        // Get the correct texture
        SuperheroIdentifier id = getIDFromPlayer(player);
        if (id == null) return;
        this.texture = SuperheroUtil.getTextureFromID(id);
        this.lightmap = SuperheroUtil.getLightMapFromID(id);
    }
    private void updateSlim(Player player) {
        // Get the correct texture
        SuperheroIdentifier id = getIDFromPlayer(player);
        if (id == null) return;
        boolean slim = id.isSlim();

        if (!slim) {
            if (this.model instanceof SteveSkinModel<?>) return;

            this.model = new SteveSkinModel(getRoot(SuperheroModels.STEVE));
        } else {
            if (this.model instanceof AlexSkinModel<?>) return;

            this.model = new AlexSkinModel(getRoot(SuperheroModels.ALEX));
        }
    }
}
