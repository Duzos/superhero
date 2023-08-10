package com.duzo.superhero.client.gui.screen;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.entities.SuitMakerBlockEntity;
import com.duzo.superhero.client.gui.menu.SuitMakerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SuitMakerScreen extends AbstractContainerScreen<SuitMakerMenu> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Superhero.MODID,"textures/gui/suit_maker_gui.png");
    public SuitMakerScreen(SuitMakerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(BG_TEXTURE,x,y,0,0,imageWidth,imageHeight);
    }

    protected void renderRecipeItems(GuiGraphics graphics) {
        if (this.menu.blockEntity.selectedSuitRecipe == null || this.menu.blockEntity.selectedSuitSlot == null) return;

        List<ItemStack> list = this.menu.blockEntity.selectedSuitRecipe.getRecipe(this.menu.blockEntity.selectedSuitSlot);

        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;

        int count = 0;
        int vert = 0;
        for (ItemStack item : list) {
            if (SuitMakerBlockEntity.hasItemInInventory(this.menu.blockEntity,item)) continue;

            if (count == 10) {
                vert++;
                count = 0;
            }

            graphics.renderItem(item, i + 5 + (16*count), j + 16 + (16*vert));

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

//    protected void renderItem(GuiGraphics graphics, ItemStack itemstack,int x,int y) {
//        if (itemstack.isEmpty()) return;
//        graphics.pushPose();
//        stack.translate(0.0F, 0.0F, 100.0F);
//        this.itemRenderer.renderAndDecorateItem(stack, this.minecraft.player, itemstack, x, y,x + y * this.imageWidth);
//        this.itemRenderer.renderGuiItemDecorations(stack, this.font, itemstack, x, y, null);
//        stack.popPose();
//    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics,mouseX,mouseY );
        renderRecipeItems(graphics);
    }
}
