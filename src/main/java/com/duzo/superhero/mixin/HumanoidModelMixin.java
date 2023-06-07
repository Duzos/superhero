package com.duzo.superhero.mixin;

import com.duzo.superhero.items.IronManArmourItem;
import com.duzo.superhero.items.SuperheroItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", cancellable = true)
    private void setupAnim(T player, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_, CallbackInfo ci) {
        HumanoidModel humanoidModel = (HumanoidModel) (Object) this;
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
                humanoidModel.hat.visible = false;
                humanoidModel.head.visible = false;
            } else if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem) {
                humanoidModel.body.visible = true;
                humanoidModel.leftArm.visible = false;
                humanoidModel.rightArm.visible = false;
            } else if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof IronManArmourItem) {
                humanoidModel.leftLeg.visible = false;
                humanoidModel.rightLeg.visible = false;
            }
            /*float rotation = 90f;
            humanoidModel.body.xRot = 90;
            //humanoidModel.head.xRot = 90;
            humanoidModel.leftArm.xRot = rotation;
            humanoidModel.rightArm.xRot = rotation;
            humanoidModel.leftLeg.xRot = rotation;
            humanoidModel.rightLeg.xRot = rotation;*/
    }





    //ClientThings.thing(humanoidModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
}
