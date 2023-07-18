package com.duzo.superhero.client.renderers.animations;

import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.util.ironman.IronManUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class AnimationHandler {


    public static void setupAnimPre(HumanoidModel<?> humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {

    }

    public static void setupAnimPost(HumanoidModel humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        if (livingEntity instanceof Player && livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SuperheroArmourItem item) {
            if (IronManUtil.isIronManSuit(item.getIdentifier())) {
                //resetPoseAll(humanoidModel);
                //SuperheroData animData = SuperheroData.get(livingEntity).get();
                //SuperheroData data = SuperheroData.get(livingEntity).get();
                //resetPose(humanoidModel.body, humanoidModel.leftArm, humanoidModel.rightArm, humanoidModel.leftLeg, humanoidModel.rightLeg);
                //System.out.println(animData.isOpen());
                //AnimationUtil.animate(humanoidModel, data.getAnimation(SuperheroData.AnimationStates.MASK_OPEN), IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE, ageInTicks, 1);
                //fixLayers(humanoidModel);
            }
        }
    }


    public static void resetPose(ModelPart... modelParts) {
        for (ModelPart part : modelParts) {
            part.resetPose();
        }
    }

    private static void resetPoseAll(HumanoidModel<?> bipedModel) {
        bipedModel.head.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.body.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.leftArm.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.rightArm.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.leftLeg.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.rightLeg.getAllParts().forEach(ModelPart::resetPose);
    }

    //private static void fixLayers(HumanoidModel<?> bipedModel) {
    //    if (bipedModel instanceof humanoidModel<?> ironManModel) {
    //
    //    }
    //}
}
