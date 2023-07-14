package com.duzo.superhero.client.models.heroes.iron_man;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.duzo.superhero.client.renderers.animations.IronManAnimations;
import com.duzo.superhero.items.ironman.IronManArmourItem;
import com.duzo.superhero.util.KeyBinds;
import com.duzo.superhero.util.ironman.IronManUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class IronManMagicModel<T extends LivingEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("superhero", "ironmanmagicmodel"), "main");
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart rightArm;
	public final ModelPart leftArm;
	public final ModelPart rightLeg;
	public final ModelPart leftLeg;
	public ModelPart modelRoot;
	private boolean isAnimating = false;
	public long maskTime = 0;
	private boolean isOpen = false;

	public AnimationState MASK_OPEN = new AnimationState();
	public AnimationState MASK_CLOSE = new AnimationState();

	public IronManMagicModel(ModelPart root) {
		this.modelRoot = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = root.getChild("rightArm");
		this.leftArm = root.getChild("leftArm");
		this.rightLeg = root.getChild("rightLeg");
		this.leftLeg = root.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(36, 79).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(6, 95).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, -2.5F));

		PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(7, 86).addBox(-4.0F, -3.0F, -3.25F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.5F))
		.texOffs(75, 29).addBox(-3.0F, -2.0F, -3.5F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -5.0F, -0.5F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(33, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(29, 30).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.275F))
		.texOffs(60, 60).addBox(-3.5F, 0.5F, -2.0F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.975F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r1 = body.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(40, 65).addBox(-6.0F, 0.0F, -2.0F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.25F, 3.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r2 = body.addOrReplaceChild("BodyLayer_r2", CubeListBuilder.create().texOffs(60, 70).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, -3.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition powerthing = body.addOrReplaceChild("powerthing", CubeListBuilder.create().texOffs(18, 34).addBox(-2.0F, -2.0F, 0.25F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.275F)), PartPose.offset(0.0F, 2.8F, -2.35F));

		PartDefinition rightArm = partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(47, 47).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false)
		.texOffs(30, 47).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(33, 17).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.75F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition LeftArmLayer_r1 = rightArm.addOrReplaceChild("LeftArmLayer_r1", CubeListBuilder.create().texOffs(75, 20).mirror().addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(-0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition right_beam = rightArm.addOrReplaceChild("right_beam", CubeListBuilder.create().texOffs(13, 47).addBox(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.0F, 10.25F, 0.0F));

		PartDefinition rocket = rightArm.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(25, 65).mirror().addBox(-1.5F, 0.0F, -4.025F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.85F, -2.725F, 2.25F));

		PartDefinition leftArm = partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(47, 47).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.001F))
		.texOffs(30, 47).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(33, 17).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition LeftArmLayer_r2 = leftArm.addOrReplaceChild("LeftArmLayer_r2", CubeListBuilder.create().texOffs(75, 20).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition left_beam = leftArm.addOrReplaceChild("left_beam", CubeListBuilder.create().texOffs(13, 47).addBox(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.0F, 10.25F, 0.0F));

		PartDefinition rocket2 = leftArm.addOrReplaceChild("rocket2", CubeListBuilder.create().texOffs(25, 65).addBox(-1.5F, 0.0F, -4.025F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.85F, -2.725F, 2.25F));

		PartDefinition rightLeg = partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(58, 0).addBox(-2F, -0.25F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.001F))
		.texOffs(50, 17).addBox(-2F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition RightFoot = rightLeg.addOrReplaceChild("RightFoot", CubeListBuilder.create().texOffs(75, 0).addBox(-2F, 7.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(54, 34).addBox(-2F, 7.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition foot_right_beam = RightFoot.addOrReplaceChild("foot_right_beam", CubeListBuilder.create().texOffs(13, 47).addBox(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.1F, 12.0F, 0.0F));

		PartDefinition leftLeg = partdefinition.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(58, 0).mirror().addBox(-2F, -0.25F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false)
		.texOffs(50, 17).mirror().addBox(-2F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition LeftFoot = leftLeg.addOrReplaceChild("LeftFoot", CubeListBuilder.create().texOffs(75, 0).mirror().addBox(-2.1F, 7.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
		.texOffs(54, 34).mirror().addBox(-2F, 7.75F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition foot_left_beam = LeftFoot.addOrReplaceChild("foot_left_beam", CubeListBuilder.create().texOffs(13, 47).mirror().addBox(-2.0F, -1.25F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-0.1F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.modelRoot.getAllParts().forEach(ModelPart::resetPose);
		//head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.modelRoot;
	}

	@Override
	public void setupAnim(T player, float p_102619_, float p_102620_, float tick, float p_102622_, float p_102623_) {
		//System.out.println(runOnce);
		//@TODO ANIMATION STUFF FOR IRON MAN'S MASK
		if(KeyBinds.ABILITY_THREE.isDown() && !isAnimating && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem item) {
			if(!this.MASK_OPEN.isStarted() && !isOpen) {
				maskTime = this.MASK_OPEN.getAccumulatedTime();
				isAnimating = !isAnimating;
				isOpen = !isOpen;
				this.MASK_OPEN.start(0);
			}
			if(!this.MASK_CLOSE.isStarted() && isOpen) {
				maskTime = this.MASK_CLOSE.getAccumulatedTime();
				isAnimating = !isAnimating;
				isOpen = !isOpen;
				this.MASK_CLOSE.start(0);
			}
		}
		if(isAnimating && maskTime + (IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN.lengthInSeconds() * 20) <= this.MASK_OPEN.getAccumulatedTime() && isOpen) {
			this.MASK_OPEN.stop();
			isAnimating = !isAnimating;
		}
		if(isAnimating && maskTime + (IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds() * 20) <= this.MASK_CLOSE.getAccumulatedTime() && !isOpen) {
			this.MASK_CLOSE.stop();
			isAnimating = !isAnimating;
		}
		//System.out.println("Open length in seconds: " + IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN.lengthInSeconds() +" Close length in seconds: " + IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE.lengthInSeconds());
		this.animate(this.MASK_OPEN, IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_OPEN, tick);
		this.animate(this.MASK_CLOSE, IronManAnimations.SUPERHERO_IRONMANMAGICMODEL_MASK_CLOSE, tick);
		if(KeyBinds.ABILITY_TWO.isDown() && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof IronManArmourItem) {
			this.head.getChild("helmet").setPos(this.head.getChild("helmet").getInitialPose().x, this.head.getChild("helmet").getInitialPose().y, this.head.getChild("helmet").getInitialPose().z);
			this.head.getChild("helmet").setRotation(this.head.getChild("helmet").getInitialPose().xRot, this.head.getChild("helmet").getInitialPose().yRot, this.head.getChild("helmet").getInitialPose().zRot);
		}
	}
}