package com.duzo.superhero.client.screen;

import com.duzo.superhero.Superhero;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SuitMakerScreen extends AbstractContainerScreen<SuitMakerMenu> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Superhero.MODID,"textures/gui/suit_maker_gui.png");
    public SuitMakerScreen(SuitMakerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,BG_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        blit(stack,x,y,0,0,imageWidth,imageHeight);
    }

    protected void renderRecipeItems(PoseStack stack) {
        if (this.menu.blockEntity.selectedSuitRecipe == null || this.menu.blockEntity.selectedSuitSlot == null) return;

        List<ItemStack> list = this.menu.blockEntity.selectedSuitRecipe.getRecipe(this.menu.blockEntity.selectedSuitSlot);

        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;

        int count = 0;
        for (ItemStack item : list) {
            if (isEven(count)) {
                renderItem(stack, item, i + 5 + 16, j + 16 + (16* workOutRow(count)));
            } else {
                renderItem(stack, item, i + 5, j + 16 + (16* workOutRow(count)));
            }
            count++;
        }
    }

    private static int workOutRow(int count) {
        if (isEven(count)) {
            return (count/2);
        }
        return (int) ((count/2) + 0.5);
    }

    private static boolean isEven(int num) {
        return num % 2 == 0;
    }

    protected void renderItem(PoseStack stack, ItemStack itemstack,int x,int y) {
        if (itemstack.isEmpty()) return;
        stack.pushPose();
        stack.translate(0.0F, 0.0F, 100.0F);
        this.itemRenderer.renderAndDecorateItem(stack, this.minecraft.player, itemstack, x, y,x + y * this.imageWidth);
        this.itemRenderer.renderGuiItemDecorations(stack, this.font, itemstack, x, y, null);
        stack.popPose();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        renderTooltip(stack,mouseX,mouseY );
        renderRecipeItems(stack);
    }
}
