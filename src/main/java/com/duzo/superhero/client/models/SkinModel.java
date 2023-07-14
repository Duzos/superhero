package com.duzo.superhero.client.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

// Probably useless @todo find out.
public abstract class SkinModel<T extends LivingEntity> extends HierarchicalModel<T> {
    public final ModelPart root;
    public final ModelPart hat;
    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart right_arm;
    public final ModelPart left_arm;
    public final ModelPart right_leg;
    public final ModelPart left_leg;
    public SkinModel(ModelPart root) {
        this.root = root;
        this.hat = root.getChild("hat");
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_arm = root.getChild("right_arm");
        this.left_arm = root.getChild("left_arm");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
