package com.duzo.superhero.client.gui.screen;

import com.duzo.superhero.Superhero;
import com.duzo.superhero.blocks.entities.SuitMakerBlockEntity;
import com.duzo.superhero.client.gui.widgets.IdentifierButton;
import com.duzo.superhero.client.gui.widgets.ItemButton;
import com.duzo.superhero.client.gui.widgets.RecipeButton;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.ids.SuperheroIdentifierRegistry;
import com.duzo.superhero.network.Network;
import com.duzo.superhero.network.packets.UpdateSuitMakerRecipeC2SPacket;
import com.duzo.superhero.recipes.SuperheroSuitRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class SuitMakerSelectScreen extends Screen {
    private final SuitMakerBlockEntity entity;
    private List<IdentifierButton> buttons = Lists.newArrayList();
    private List<RecipeButton> recipeButtons = Lists.newArrayList();
    private SuperheroSuitRecipe selectedRecipe;
    private EquipmentSlot selectedSlot;
    private static final int ITEM_SIZE = 16;
    private AbstractIdentifier id;
    public SuitMakerSelectScreen(SuitMakerBlockEntity maker) {
        super(Component.translatable("screen." + Superhero.MODID + ".select_screen"));
        this.entity = maker;
        this.generateButtons();
    }
    private void generateButtons() {
        int count = 0;
        int vert = 0;
        IdentifierButton button;
        for (AbstractIdentifier id : SuperheroIdentifierRegistry.IDS_REGISTRY.get().getValues()) {

            if (count >= Math.round(Minecraft.getInstance().getWindow().getGuiScaledWidth() / (float) ITEM_SIZE)) {
                count = 0;
                vert++;
            } // How many items can fit in the screen

            button = new IdentifierButton(count*ITEM_SIZE,vert*ITEM_SIZE,ITEM_SIZE,ITEM_SIZE,(butt) -> this.setSelectedId(((IdentifierButton) butt).id),id);
            this.buttons.add(button);
            this.addWidget(button);
            button.active = true;
            button.visible = true;
            count++;
        }
    }
    private void generateRecipeButtons() {
        if (this.getSelectedId() == null || this.selectedRecipe == null || this.selectedRecipe.isEmpty()) return;

        RecipeButton button;
        for (EquipmentSlot slot : this.selectedRecipe.keySet()) {
            if (!this.selectedRecipe.containsKey(slot)) continue;

            button = new RecipeButton(getXCoordinateForSlot(slot),getYCoordinateForSlot(slot),ITEM_SIZE,ITEM_SIZE,(butt) -> {
                this.selectedSlot = ((RecipeButton) butt).slot;
                this.syncToEntity();
                this.onClose();
            },slot,this.selectedRecipe);
            this.addWidget(button);
            this.recipeButtons.add(button);
            button.active = true;
            button.visible = true;
        }
    }
    private void setRecipeButtonsVisible(boolean val) {
        for (ItemButton button : this.recipeButtons) {
            button.visible = val;
        }
    }
    private void setButtonsVisible(boolean val) {
        for (IdentifierButton button : this.buttons) {
            button.visible = val;
        }
    }
    private int getXCoordinateForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case MAINHAND -> getWindowCenterX()  + ITEM_SIZE;
            case OFFHAND -> getWindowCenterX() - ITEM_SIZE;
            case HEAD,CHEST,LEGS,FEET -> getWindowCenterX();
        };
    }
    private int getYCoordinateForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case FEET -> getWindowCenterY() - (ITEM_SIZE * 2);
            case LEGS -> getWindowCenterY() + ITEM_SIZE;
            case CHEST,MAINHAND,OFFHAND -> getWindowCenterY();
            case HEAD -> getWindowCenterY() - ITEM_SIZE;
        };
    }
    private int getWindowCenterX() {
        return Math.round(Minecraft.getInstance().getWindow().getScreenWidth() / 2f);
    }
    private int getWindowCenterY() {
        return Math.round(Minecraft.getInstance().getWindow().getScreenHeight() / 2f);
    }

    private AbstractIdentifier getSelectedId() {
        return this.id;
    }
    private void setSelectedId(AbstractIdentifier id) {
        this.id = id;
        this.setSelectedRecipe(this.id.getRecipe());
    }
    private void setSelectedRecipe(SuperheroSuitRecipe recipe) {
        System.out.println(recipe);
        System.out.println(this.id.getRecipe());
        this.selectedRecipe = recipe;

        if (this.selectedRecipe.isEmpty() && this.id != null && !this.id.getRecipe().isEmpty()) {
            this.setSelectedRecipe(this.id.getRecipe());
        }
    }
    private void syncToEntity() {
        entity.selectedSuitRecipe = this.selectedRecipe;
        entity.selectedSuitSlot = this.selectedSlot;
        Network.sendToServer(new UpdateSuitMakerRecipeC2SPacket(entity.getBlockPos(),this.selectedSlot,this.selectedRecipe));
    }
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);

        if (this.getSelectedId() == null) {
            this.renderButtons(stack, mouseX, mouseY, delta);
        } else {
            this.renderRecipeSelect(stack, mouseX, mouseY, delta);
        }
    }

    public void renderButtons(PoseStack stack, int mouseX, int mouseY, float delta) {
        if (!(this.recipeButtons.isEmpty()) && this.recipeButtons.get(0).visible) this.setRecipeButtonsVisible(false);
        if (this.buttons.isEmpty()) this.generateButtons();

        for (IdentifierButton button : this.buttons) {
            button.render(stack, mouseX, mouseY, delta);
        }
    }

    public void renderRecipeSelect(PoseStack stack,int mouseX,int mouseY, float delta) {
        if (!this.buttons.isEmpty() && this.buttons.get(0).visible) this.setButtonsVisible(false);
        if (this.recipeButtons.isEmpty()) this.generateRecipeButtons();

        for (ItemButton button : this.recipeButtons) {
            button.render(stack, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
