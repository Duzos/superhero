package com.duzo.superhero.mixin;

import com.duzo.superhero.items.ironman.IronManArmourItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.duzo.superhero.items.ironman.IronManArmourItem.canBlastOff;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    public boolean needToPop = false;

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", cancellable = true)
    private void setupAnim(T player, float p_102867_, float p_102868_, float p_102869_, float p_102870_, float p_102871_, CallbackInfo ci) {
        HumanoidModel humanoidModel = (HumanoidModel) (Object) this;

        if (!(player instanceof Player)) return;

        if (canBlastOff((Player) player)) {
            humanoidModel.head.setRotation(-1.5707964f,0,0);
            humanoidModel.leftArm.setRotation(0,0,0);
            humanoidModel.rightArm.setRotation(0,0,0);
            humanoidModel.leftLeg.setRotation(0,0,0);
            humanoidModel.rightLeg.setRotation(0,0,0);
//            AABB box = player.getBoundingBox().deflate(0,1,0);
//            player.setBoundingBox(box);
        }
        /*&& player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem
                && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem
                && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof IronManArmourItem
                && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
            float rotationX = player.getViewXRot(1f) * ((float)Math.PI / 180F);
            float rotationY = player.getViewYRot(1f) * ((float)Math.PI / 180F);
            System.out.println(rotationX + " " + rotationY);
            humanoidModel.body.xRot = rotationX;
            humanoidModel.leftArm.xRot = rotationX;
            humanoidModel.rightArm.xRot = rotationX;
            humanoidModel.leftLeg.xRot = rotationX;
            humanoidModel.rightLeg.xRot = rotationX;
            humanoidModel.body.yRot = rotationY;
            humanoidModel.leftArm.yRot = rotationY;
            humanoidModel.rightArm.yRot = rotationY;
            humanoidModel.leftLeg.yRot = rotationY;
            humanoidModel.rightLeg.yRot = rotationY;
            humanoidModel.leftArm.z = humanoidModel.body.z;
            humanoidModel.rightArm.z = humanoidModel.body.z;
            humanoidModel.leftLeg.offsetRotation(new Vector3f(humanoidModel.body.x, humanoidModel.body.y, humanoidModel.body.z));
            humanoidModel.rightLeg.offsetRotation(new Vector3f(humanoidModel.body.x, humanoidModel.body.y, humanoidModel.body.z));
        }
        /*float partialTicks = Minecraft.getInstance().getPartialTick();
        float interpolatedYaw = (player.yRotO + (humanoidModel.head.yRot - player.yRotO) * partialTicks);
        humanoidModel.body.setRotation(humanoidModel.head.xRot, humanoidModel.head.yRot, 0);
        humanoidModel.body.setPos(0, player.getEyeHeight() / 2f, 0);
        humanoidModel.leftArm.setRotation(humanoidModel.head.xRot, humanoidModel.head.yRot, 0);
        humanoidModel.leftArm.setPos(-2, player.getEyeHeight() / 2f, 0);
        humanoidModel.rightArm.setRotation(humanoidModel.head.xRot, humanoidModel.head.yRot, 0);
        humanoidModel.rightArm.setPos(2, player.getEyeHeight() / 2f, 0);
        humanoidModel.leftLeg.setRotation(humanoidModel.head.xRot, humanoidModel.head.yRot, 0);
        humanoidModel.leftLeg.setPos(-2, player.getEyeHeight() / 2f, 0);
        humanoidModel.rightLeg.setRotation(humanoidModel.head.xRot, humanoidModel.head.yRot, 0);
        humanoidModel.rightLeg.setPos(2, player.getEyeHeight() / 2f, 0);*/
    }

    //ClientThings.thing(humanoidModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
}
