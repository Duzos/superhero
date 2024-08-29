package mc.duzo.timeless.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

import mc.duzo.timeless.client.animation.AnimationInfo;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.render.SuitFeature;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void timeless$PlayerEntityRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) this;

        this.addFeature(new SuitFeature<>(renderer, ctx.getModelLoader()));
    }

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void timeless$playerRender(AbstractClientPlayerEntity player, CallbackInfo ci) {
        boolean current = this.getModel().body.visible;
        if (!(current)) return;

        MinecraftClient client = MinecraftClient.getInstance();
        AnimationInfo.RenderType type = SuitFeature.getRenderType(player);

        if (type == AnimationInfo.RenderType.NONE || type == AnimationInfo.RenderType.TORSO_HEAD && player.equals(client.player) && !client.gameRenderer.getCamera().isThirdPerson()) {
            return;
        }

        type.apply(this.getModel());
    }

    @Inject(method = "renderArm" , at = @At("HEAD"), cancellable = true)
    private void timeless$renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci){
        Suit suit = Suit.findSuit(player).orElse(null);
        if (suit == null) return;
        ClientSuit clientSuit = suit.toClient();
        if (!(clientSuit.hasModel())) return;

        boolean isRight = player.getMainArm() == Arm.RIGHT;
        clientSuit.model().renderArm(isRight, player, 0, matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(clientSuit.texture())), light, 1, 1, 1, 1);
        ci.cancel();
    }
}