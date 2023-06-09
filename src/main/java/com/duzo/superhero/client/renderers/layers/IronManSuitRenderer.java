package com.duzo.superhero.client.renderers.layers;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.client.models.heroes.iron_man.IronManMagicModel;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.util.SuperheroIdentifier;
import com.duzo.superhero.util.ironman.IronManUtil;
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

import static com.duzo.superhero.util.SuperheroUtil.getIDFromPlayer;

@OnlyIn(Dist.CLIENT)
public class IronManSuitRenderer<T extends AbstractClientPlayer, M extends EntityModel<T>> extends RenderLayer<T, M> {

    //Suit Textures (solid)
    private static final ResourceLocation MK2 = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk2.png");
    private static final ResourceLocation MK3 = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk3.png");

    //Suit Lightmaps (lightmap)
    private static final ResourceLocation MK2_L = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk2_l.png");
    private static final ResourceLocation MK3_L = new ResourceLocation(Superhero.MODID, "textures/heroes/iron_man/mk3_l.png");

    public IronManMagicModel ironman;
    public ResourceLocation texture = MK3;
    public ResourceLocation lightmap = MK3_L;

    public IronManSuitRenderer(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
        this.ironman = new IronManMagicModel(IronManMagicModel.createBodyLayer().bakeRoot());
        this.texture = MK3;
        this.lightmap = MK3_L;
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
        pose.pushPose();

        updateTextures(player);

        copyPlayersMovement(playerModel, this.ironman);
        this.ironman.setupAnim(player,w,e,r,t,y);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(this.texture));
        Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = player.getItemBySlot(EquipmentSlot.FEET).getItem();
        if(head instanceof IronManArmourItem) {
            this.ironman.head.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        if(chest instanceof IronManArmourItem) {
            this.ironman.body.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            if(player.isOnGround()) {
                this.ironman.rightArm.getChild("right_beam").visible = true;
                this.ironman.leftArm.getChild("left_beam").visible = true;
            } else {
                this.ironman.rightArm.getChild("right_beam").visible = false;
                this.ironman.leftArm.getChild("left_beam").visible = false;
            }
            this.ironman.rightArm.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.ironman.leftArm.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        if(legs instanceof IronManArmourItem) {
            if(player.isOnGround()) {
                this.ironman.rightLeg.getChild("RightFoot").getChild("foot_right_beam").visible = true;
                this.ironman.leftLeg.getChild("LeftFoot").getChild("foot_left_beam").visible = true;
            } else {
                this.ironman.rightLeg.getChild("RightFoot").getChild("foot_right_beam").visible = false;
                this.ironman.leftLeg.getChild("LeftFoot").getChild("foot_left_beam").visible = false;
            }
            this.ironman.rightLeg.getChild("RightFoot").visible = true;
            this.ironman.leftLeg.getChild("LeftFoot").visible = true;
            this.ironman.rightLeg.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.ironman.leftLeg.render(pose, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        VertexConsumer vertexConsumer1 = buffer.getBuffer(RenderType.text(this.lightmap));
        if(head instanceof IronManArmourItem) {
            this.ironman.head.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        if(chest instanceof IronManArmourItem) {
            if(player.isOnGround()) {
                this.ironman.rightArm.getChild("right_beam").visible = true;
                this.ironman.leftArm.getChild("left_beam").visible = true;
            } else {
                this.ironman.rightArm.getChild("right_beam").visible = false;
                this.ironman.leftArm.getChild("left_beam").visible = false;
            }
            this.ironman.body.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.ironman.rightArm.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.ironman.leftArm.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        if(legs instanceof IronManArmourItem) {
            if(player.isOnGround()) {
                this.ironman.rightLeg.getChild("RightFoot").getChild("foot_right_beam").visible = true;
                this.ironman.leftLeg.getChild("LeftFoot").getChild("foot_left_beam").visible = true;
            } else {
                this.ironman.rightLeg.getChild("RightFoot").getChild("foot_right_beam").visible = false;
                this.ironman.leftLeg.getChild("LeftFoot").getChild("foot_left_beam").visible = false;
            }
            this.ironman.rightLeg.getChild("RightFoot").visible = true;
            this.ironman.leftLeg.getChild("LeftFoot").visible = true;
            this.ironman.rightLeg.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            this.ironman.leftLeg.render(pose, vertexConsumer1, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
        pose.popPose();
    }

    private void copyPlayersMovement(PlayerModel pm, IronManMagicModel mp) {
        mp.head.setPos(pm.head.x, pm.head.y, pm.head.z);
        mp.head.setRotation(pm.head.xRot, pm.head.yRot, pm.head.zRot);
        mp.body.setPos(pm.body.x, pm.body.y, pm.body.z);
        mp.body.setRotation(pm.body.xRot, pm.body.yRot, pm.body.zRot);
        mp.rightArm.setPos(pm.rightArm.x, pm.rightArm.y, pm.rightArm.z);
        mp.rightArm.setRotation(pm.rightArm.xRot, pm.rightArm.yRot, pm.rightArm.zRot);
        mp.leftArm.setPos(pm.leftArm.x, pm.leftArm.y, pm.leftArm.z);
        mp.leftArm.setRotation(pm.leftArm.xRot, pm.leftArm.yRot, pm.leftArm.zRot);
        mp.rightLeg.setPos(pm.rightLeg.x, pm.rightLeg.y, pm.rightLeg.z);
        mp.rightLeg.setRotation(pm.rightLeg.xRot, pm.rightLeg.yRot, pm.rightLeg.zRot);
        mp.leftLeg.setPos(pm.leftLeg.x, pm.leftLeg.y, pm.leftLeg.z);
        mp.leftLeg.setRotation(pm.leftLeg.xRot, pm.leftLeg.yRot, pm.leftLeg.zRot);
    }

    private void updateTextures(Player player) {
        // Get the correct texture
        SuperheroIdentifier id = getIDFromPlayer(player);
        if (id == null) return;
        if (!IronManUtil.isIronManSuit(id)) return;
        this.texture = IronManUtil.getTextureFromID(id);
        this.lightmap = IronManUtil.getLightMapFromID(id);
    }

    //private void copyPlayerMovement(PlayerModel pm, ModelPart mp, String name) {
    //    if(name.equals("head")) {
    //        mp.setPos(pm.head.x, pm.head.y, pm.head.z);
    //        mp.setRotation(pm.head.xRot, pm.head.yRot, pm.head.zRot);
    //    }
    //    if(name.equals("body")) {
    //        mp.setPos(pm.body.x, pm.body.y, pm.body.z);
    //        mp.setRotation(pm.body.xRot, pm.body.yRot, pm.body.zRot);
    //    }
    //    if(name.equals("rightArm")) {
    //        mp.setPos(pm.rightArm.x, pm.rightArm.y, pm.rightArm.z);
    //        mp.setRotation(pm.rightArm.xRot, pm.rightArm.yRot, pm.rightArm.zRot);
    //    }
    //    if(name.equals("leftArm")) {
    //        mp.setPos(pm.leftArm.x, pm.leftArm.y, pm.leftArm.z);
    //        mp.setRotation(pm.leftArm.xRot, pm.leftArm.yRot, pm.leftArm.zRot);
    //    }
    //    if(name.equals("rightLeg")) {
    //        mp.setPos(pm.rightLeg.x, pm.rightLeg.y, pm.rightLeg.z);
    //        mp.setRotation(pm.rightLeg.xRot, pm.rightLeg.yRot, pm.rightLeg.zRot);
    //    }
    //    if(name.equals("leftLeg")) {
    //        mp.setPos(pm.leftLeg.x, pm.leftLeg.y, pm.leftLeg.z);
    //        mp.setRotation(pm.leftLeg.xRot, pm.leftLeg.yRot, pm.leftLeg.zRot);
    //    }
    //}
}