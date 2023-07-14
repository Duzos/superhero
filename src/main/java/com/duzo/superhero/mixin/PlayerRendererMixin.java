package com.duzo.superhero.mixin;

import com.duzo.superhero.client.renderers.layers.GenericSuitRenderer;
import com.duzo.superhero.client.renderers.layers.IronManSuitRenderer;
import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.spiderman.MilesHoodieItem;
import com.duzo.superhero.util.SuperheroUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "renderHand", at = @At("HEAD"),cancellable = true)
    public void setModelProperties(PoseStack p_117776_, MultiBufferSource p_117777_, int p_117778_, AbstractClientPlayer player, ModelPart p_117780_, ModelPart p_117781_, CallbackInfo ci) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SuperheroArmourItem item) {
            // @TODO Loqor's silly iron man models mean this is broken for iron man

            ResourceLocation skin = SuperheroUtil.getTextureFromID(item.getIdentifier());

            PlayerModel<AbstractClientPlayer> playermodel = this.getModel();
            this.setModelProperties(player);
            playermodel.attackTime = 0.0F;
            playermodel.crouching = false;
            playermodel.swimAmount = 0.0F;
            playermodel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            p_117780_.xRot = 0.0F;
            p_117780_.render(p_117776_, p_117777_.getBuffer(RenderType.entitySolid(skin)), p_117778_, OverlayTexture.NO_OVERLAY);
            p_117781_.xRot = 0.0F;
            p_117781_.render(p_117776_, p_117777_.getBuffer(RenderType.entityTranslucent(skin)), p_117778_, OverlayTexture.NO_OVERLAY);
            ci.cancel();
        }
    }

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer player);
    @SuppressWarnings("resource")
    @Inject(method = "setModelProperties", at = @At("RETURN"))
    public void setModelProperties(AbstractClientPlayer abstractClientPlayer, CallbackInfo info) {
        PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
        Item head = abstractClientPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem();
        Item chest = abstractClientPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem();
        Item legs = abstractClientPlayer.getItemBySlot(EquipmentSlot.LEGS).getItem();
        Item feet = abstractClientPlayer.getItemBySlot(EquipmentSlot.FEET).getItem();
        if(head instanceof SuperheroArmourItem) {
            playerModel.hat.visible = false;
        }
        if(chest instanceof SuperheroArmourItem || chest instanceof MilesHoodieItem) {
            playerModel.jacket.visible = false;
            playerModel.leftSleeve.visible = false;
            playerModel.rightSleeve.visible = false;
        }
        if(legs instanceof SuperheroArmourItem || feet instanceof SuperheroArmourItem) {
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

    @Inject(
            method = "<init>",  // the jvm bytecode signature for the constructor
            at = @At("TAIL"), cancellable = true  // signal that this void should be run at the method HEAD, meaning the first opcode
    )
    private void PlayerRenderer(EntityRendererProvider.Context p_174557_, boolean p_174558_, CallbackInfo ci) {
        this.addLayer(new IronManSuitRenderer<>(this));
        this.addLayer(new GenericSuitRenderer<>(this));
    }
}