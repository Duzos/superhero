package mc.duzo.timeless.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

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
        if (!SuitFeature.shouldRender(player)) return;

        this.getModel().setVisible(false);

        MinecraftClient client = MinecraftClient.getInstance();
        if (player.equals(client.player) && !client.gameRenderer.getCamera().isThirdPerson()) {
            this.getModel().setVisible(true);
        }
    }
}