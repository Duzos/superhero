package com.duzo.superhero.mixin;

import com.duzo.superhero.client.models.heroes.iron_man.IronManMagicModel;
import com.duzo.superhero.client.renderers.animations.AnimationHandler;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.duzo.superhero.util.ironman.IronManUtil.FlightUtil.canBlastOff;


@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(at = @At("HEAD"), cancellable = true, method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void setupAnimHead(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel<?> bipedModel = (HumanoidModel<?>) (Object) this;
        AnimationHandler.setupAnimPre(bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
    }

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void setupAnimTail(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel<LivingEntity> bipedModel = (HumanoidModel) (Object) this;
        if(livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
            AnimationHandler.setupAnimPost(bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
        }

        if (!(livingEntity instanceof Player)) return;

        if (canBlastOff((Player) livingEntity)) {
            bipedModel.head.setRotation(-1.5707964f,0,0);
            bipedModel.hat.setRotation(-1.5707964f,0,0);
            bipedModel.leftArm.setRotation(0,0,0);
            bipedModel.rightArm.setRotation(0,0,0);
            bipedModel.leftLeg.setRotation(0,0,0);
            bipedModel.rightLeg.setRotation(0,0,0);
            bipedModel.body.setRotation(0,0,0);
//            AABB box = player.getBoundingBox().deflate(0,1,0);
//            player.setBoundingBox(box);
        }
    }
}
