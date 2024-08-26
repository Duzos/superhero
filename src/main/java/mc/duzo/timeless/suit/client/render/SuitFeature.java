package mc.duzo.timeless.suit.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.item.SuitItem;
import mc.duzo.timeless.suit.set.SuitSet;

public class SuitFeature<T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {

    public SuitFeature(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        Suit suit = findSuit(livingEntity);
        if (suit == null) return;

        SuitSet set = suit.getSet();
        if (!(set.isWearing(livingEntity))) return; // todo this check every frame is bad

        VertexConsumer consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(suit.toClient().texture()));
        SuitModel model = suit.toClient().model();

        BipedEntityModel<?> context = (BipedEntityModel<?>) this.getContextModel();

        model.copyFrom(context);
        model.setAngles(livingEntity, f, g, j, k, l);

        model.render(livingEntity, j, matrixStack, consumer, i, 1, 1, 1, 1);
    }

    private static Suit findSuit(LivingEntity entity) {
        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (!(chest.getItem() instanceof SuitItem item)) return null;

        return item.getSuit();
    }

    public static boolean shouldRender(LivingEntity livingEntity) {
        Suit suit = findSuit(livingEntity);
        if (suit == null) return false;

        SuitSet set = suit.getSet();
        return set.isWearing(livingEntity); // todo this check every frame is bad
    }
}
