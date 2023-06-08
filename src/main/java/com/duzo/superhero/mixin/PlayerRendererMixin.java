package com.duzo.superhero.mixin;

import com.duzo.superhero.entities.IronManEntity;
import com.duzo.superhero.items.IronManArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.duzo.superhero.items.IronManArmourItem.keyDown;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Shadow protected abstract void setupRotations(AbstractClientPlayer p_117802_, PoseStack p_117803_, float p_117804_, float p_117805_, float p_117806_);

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }
//    @Inject(at = @At("TAIL"),method = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V",cancellable = false)
//    private void addLayers(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
//
//    }
//    @Inject(at = @At("HEAD"),method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
//    private void setSkin(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> cir) {
//
//        if (IronManEntity.isValidArmorButCooler(player)) {
//            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
//            if(head.getItem() instanceof IronManArmourItem item){
//                cir.setReturnValue(item.getTexture());
//            }
//        }
//    }

    @SuppressWarnings("resource")
    @Inject(method = "setModelProperties", at = @At("RETURN"))
    public void setModelProperties(AbstractClientPlayer abstractClientPlayer, CallbackInfo info) {
        PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
        Item chest = abstractClientPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = abstractClientPlayer.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = abstractClientPlayer.getItemBySlot(EquipmentSlot.FEET).getItem();
        if(chest instanceof IronManArmourItem) {
            playerModel.jacket.visible = false;
            playerModel.leftSleeve.visible = false;
            playerModel.rightSleeve.visible = false;
        }
        if(legs instanceof IronManArmourItem || abstractClientPlayer.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
            playerModel.leftPants.visible = false;
            playerModel.rightPants.visible = false;
        }
        /*if(!abstractClientPlayer.isOnGround() && keyDown(GLFW.GLFW_KEY_W)
                && abstractClientPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem
                && abstractClientPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IronManArmourItem
                && abstractClientPlayer.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof IronManArmourItem
                && abstractClientPlayer.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof IronManArmourItem) {
            float rotation = abstractClientPlayer.getViewXRot(1f);
            System.out.println(rotation);
            playerModel.body.xRot = rotation;
            playerModel.leftArm.xRot = rotation;
            playerModel.rightArm.xRot = rotation;
            playerModel.leftLeg.xRot = rotation;
            playerModel.rightLeg.xRot = rotation;
        } else {
            playerModel.body.xRot = 0;
            playerModel.leftArm.xRot = 0;
            playerModel.rightArm.xRot = 0;
            playerModel.leftLeg.xRot = 0;
            playerModel.rightLeg.xRot = 0;
        }*/
    }
}