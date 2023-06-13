package com.duzo.superhero.client.models.items;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.duzo.superhero.Superhero;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class IronManArmourModel<T extends LivingEntity> extends HumanoidModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Superhero.MODID, "iron_man_armour"), "main");
	public final ModelPart hat;
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart right_leg;
	public final ModelPart left_leg;
	public final ModelPart left_arm;
	public final ModelPart right_arm;

	public IronManArmourModel(ModelPart root) {
		super(root);
		this.hat = root.getChild("hat");
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.right_leg = root.getChild("right_leg");
		this.left_leg = root.getChild("left_leg");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(60, 27).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.251F))
		.texOffs(32, 4).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.251F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(62, 17).addBox(-4.0F, 0.25F, -2.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(-0.001F))
		.texOffs(56, 4).addBox(-4.0F, 0.25F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -8.25F, -2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition rightLegFlame = right_leg.addOrReplaceChild("rightLegFlame", CubeListBuilder.create().texOffs(72, 58).addBox(6.0F, 16.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 58).addBox(6.0F, 16.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 52).addBox(6.0F, 12.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(64, 52).addBox(6.0F, 12.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-7.0F, 0.0F, 1.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition leftLegFlame = left_leg.addOrReplaceChild("leftLegFlame", CubeListBuilder.create().texOffs(72, 58).addBox(10.0F, 16.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 58).addBox(10.0F, 16.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 52).addBox(10.0F, 12.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(64, 52).addBox(10.0F, 12.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-11.0F, 0.0F, 1.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition leftArmFlame = left_arm.addOrReplaceChild("leftArmFlame", CubeListBuilder.create().texOffs(72, 58).addBox(14.0F, 4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 58).addBox(14.0F, 4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 52).addBox(14.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(64, 52).addBox(14.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-14.0F, 10.0F, 1.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition rightArmFlame = right_arm.addOrReplaceChild("rightArmFlame", CubeListBuilder.create().texOffs(72, 58).addBox(2.0F, 4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 58).addBox(2.0F, 4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(72, 52).addBox(2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(64, 52).addBox(2.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-4.0F, 10.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 80, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netheadYaw, float headPitch) {
		super.setupAnim(entity,limbSwing,limbSwingAmount,ageInTicks,netheadYaw,headPitch);
	}

	@Override
	public void prepareMobModel(T entity, float p_102862_, float p_102863_, float p_102864_) {
		super.prepareMobModel(entity, p_102862_, p_102863_, p_102864_);
		if(entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem item) {
			head.getChild("mask").setRotation(-86 * ((float) Math.PI / 180F), head.getChild("mask").yRot, head.getChild("mask").zRot);
			head.getChild("mask").y = head.getChild("mask").y + 1;
			head.getChild("mask").z = head.getChild("mask").z + 3;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		/*poseStack.pushPose();
		right_leg.getChild("rightLegFlame").offsetPos(new Vector3f(right_leg.x - 7,right_leg.y + 0.5f,right_leg.z + 1));
		right_leg.getChild("rightLegFlame").setRotation(right_leg.xRot, right_leg.yRot, right_leg.zRot);
		right_leg.getChild("rightLegFlame").render(poseStack, vertexConsumer, 15728880, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
		poseStack.pushPose();
		right_arm.getChild("rightArmFlame").offsetPos(new Vector3f(right_arm.x,right_arm.y - 3.5f,right_arm.z + 1));
		right_arm.getChild("rightArmFlame").setRotation(right_arm.xRot, right_arm.yRot, right_arm.zRot);
		right_arm.getChild("rightArmFlame").render(poseStack, vertexConsumer, 15728880, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
		poseStack.pushPose();
		left_leg.getChild("leftLegFlame").offsetPos(new Vector3f(left_leg.x - 11,left_leg.y + 0.5f,left_leg.z + 1));
		left_leg.getChild("leftLegFlame").setRotation(left_leg.xRot, left_leg.yRot, left_leg.zRot);
		left_leg.getChild("leftLegFlame").render(poseStack, vertexConsumer, 15728880, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
		poseStack.pushPose();
		left_arm.getChild("leftArmFlame").offsetPos(new Vector3f(left_arm.x - 13,left_arm.y + 9.5f,left_arm.z + 1));
		left_arm.getChild("leftArmFlame").setRotation(left_arm.xRot, left_arm.yRot, left_arm.zRot);
		left_arm.getChild("leftArmFlame").render(poseStack, vertexConsumer, 15728880, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();*/
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}