package com.duzo.superhero.client.layers;

import com.duzo.superhero.client.models.items.IronManArmourModel;
import com.duzo.superhero.entities.HumanoidEntity;
import com.duzo.superhero.items.IronManArmourItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Deprecated
public class IronManArmourLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public IronManArmourLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    private static final IronManArmourModel<?> model = new IronManArmourModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(IronManArmourModel.LAYER_LOCATION));

    @Override
    public void render(PoseStack stack, MultiBufferSource source, int light, T entity, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
        if (this.getParentModel() instanceof HumanoidModel<?> player && isValidArmourSet(head,chest,legs,feet)) {
            IronManArmourItem item = (IronManArmourItem) head.getItem();
            ResourceLocation texture = item.getTexture();
            model.renderToBuffer(stack,source.getBuffer(RenderType.entitySmoothCutout(texture)),light, OverlayTexture.NO_OVERLAY,1,1,1,1);
        }
    }

    public static boolean isValidArmourSet(ItemStack head,ItemStack chest,ItemStack legs,ItemStack feet) {
        boolean flag1 = head.getItem() instanceof IronManArmourItem && chest.getItem() instanceof IronManArmourItem && legs.getItem() instanceof IronManArmourItem && feet.getItem() instanceof IronManArmourItem;
        return flag1;
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
        if (isValidArmourSet(head, chest, legs, feet)) {
            IronManArmourItem item = (IronManArmourItem) head.getItem();
            return item.getTexture();
        }
        return HumanoidEntity.ERROR_TEXTURE;
    }
}
