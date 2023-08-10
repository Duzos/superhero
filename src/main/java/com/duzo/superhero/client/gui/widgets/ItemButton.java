package com.duzo.superhero.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemButton extends AbstractWidget {
    protected final OnPress onPress;
    protected final ItemStack item;
    protected ItemRenderer itemRenderer;
    protected Minecraft minecraft;
    protected Font font;

    public ItemButton(int x, int y, int width, int height, OnPress onPress, ItemStack stack) {
        super(x, y, width, height, Component.empty());
        this.onPress = onPress;
        this.item = stack;
        this.minecraft = Minecraft.getInstance();
        this.font = Minecraft.getInstance().font;
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderItem(graphics);
    }

    protected int getCenterX() {
        int x = this.getX();
        int endX = x + this.width;
        return Math.round((x + endX) / 2f);
    }
    protected int getCenterY() {
        int y = this.getY();
        int endY = y + this.height;
        return Math.round((y + endY) / 2f);
    }
    protected float getItemScaleX() {
        return this.width / 16f;
    }
    protected float getItemScaleY() {
        return this.height / 16f;
    }

    protected void renderItem(GuiGraphics graphics) {
        graphics.renderItem(this.item,this.getCenterX(),this.getCenterY()-8);
    }

//    protected void renderItem(GuiGraphics graphics, ItemStack itemstack, int x, int y) {
//        if (itemstack.isEmpty()) return;
//        stack.pushPose();
//        stack.translate(0.0F, 0.0F, 100.0F);
////        stack.scale(this.getItemScaleX(),this.getItemScaleY(),0); // @TODO scaling
//        this.itemRenderer.renderAndDecorateItem(stack, this.minecraft.player, itemstack, x, y,x + y);
//        this.itemRenderer.renderGuiItemDecorations(stack, this.font, itemstack, x, y, null);
//        stack.popPose();
//    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        this.onPress.onPress(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(ItemButton button);
    }
}
