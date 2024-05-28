package com.duzo.superhero.client.renderers.animations;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class AnimationHandler {


    public static void setupAnimPre(HumanoidModel<?> humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {

    }

    public static void setupAnimPost(HumanoidModel humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {

    }


}
