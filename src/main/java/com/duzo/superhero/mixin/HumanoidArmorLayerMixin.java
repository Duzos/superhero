package com.duzo.superhero.mixin;

import com.duzo.superhero.items.SuperheroArmourItem;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> p_117346_) {
        super(p_117346_);
    }

    @Shadow
    protected abstract void setPartVisibility(A pModel, EquipmentSlot p_117127_);

    @Shadow
    protected abstract net.minecraft.client.model.Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot p_117129_);

    @Shadow
    protected abstract void renderModel(PoseStack p_117107_, MultiBufferSource p_117108_, int p_117109_, boolean p_117111_, net.minecraft.client.model.Model p_117112_, float p_117114_, float p_117115_, float p_117116_, ResourceLocation armorResource);

    @Shadow
    public abstract ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type);

    @Inject(method = "renderArmorPiece", at = @At("HEAD"),cancellable = true)
    public void changePartVisibilities_SUPERHERO(PoseStack p_117119_, MultiBufferSource p_117120_, T p_117121_, EquipmentSlot p_117122_, int p_117123_, A pModel, CallbackInfo ci) {
        HumanoidArmorLayer layer = (HumanoidArmorLayer) (Object) this;

        if (p_117122_ == null) return;

        ItemStack itemstack = p_117121_.getItemBySlot(p_117122_);
        Item $$9 = itemstack.getItem();
        if ($$9 instanceof SuperheroArmourItem armoritem && !($$9 instanceof IronManArmourItem)) {
            if (armoritem.getEquipmentSlot() == p_117122_) {
                this.getParentModel().copyPropertiesTo(pModel);
//                this.setPartVisibility(pModel, p_117122_);

//                pModel.setAllVisible(false);
//                switch (p_117122_) {
//                    case HEAD -> {
//                        pModel.head.visible = true;
//                        pModel.hat.visible = true;
//                        /*if(IronManUtil.isIronManSuit(armoritem.getIdentifier())) {
//                            float i = 0;
//                            if (KeyBinds.ABILITY_THREE.isDown() && i == 0) {
//                                i = -1.5707964f;
//                            } else {
//                                i = 0;
//                            }
//                            pModel.head.getChild("mask").setRotation(i, 0, 0);
//                        }*/
//                    }
//                    case CHEST -> {
//                        pModel.body.visible = true;
//                        pModel.rightArm.visible = true;
//                        pModel.leftArm.visible = true;
//                    }
//                    case LEGS, FEET -> {
//                        pModel.rightLeg.visible = true;
//                        pModel.leftLeg.visible = true;
//                    }
                }
                net.minecraft.client.model.Model model = getArmorModelHook(p_117121_, itemstack, p_117122_, pModel);
                boolean flag1 = this.usesInnerModel(p_117122_);
                boolean flag = itemstack.hasFoil();
                this.renderModel(p_117119_, p_117120_, p_117123_, flag, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, null));
                ci.cancel();
            }
//        } else if ($$9 instanceof MilesHoodieItem armoritem) {
//            if (armoritem.getEquipmentSlot() == p_117122_) {
//                this.getParentModel().copyPropertiesTo(pModel);
////                this.setPartVisibility(pModel, p_117122_);
//
//                pModel.setAllVisible(true);
//                //pModel.head.visible = true;
//                //pModel.hat.visible = true;
//                //pModel.body.visible = true;
//                //pModel.rightArm.visible = true;
//                //pModel.leftArm.visible = true;
//
//                net.minecraft.client.model.Model model = getArmorModelHook(p_117121_, itemstack, p_117122_, pModel);
//                boolean flag1 = this.usesInnerModel(p_117122_);
//                boolean flag = itemstack.hasFoil();
//                this.renderModel(p_117119_, p_117120_, p_117123_, flag, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_117121_, itemstack, p_117122_, null));
//                ci.cancel();
//            }
//        }
    }
}
