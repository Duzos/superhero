package com.duzo.superhero.client.gui.widgets;

import com.duzo.superhero.recipes.SuperheroSuitRecipe;
import net.minecraft.world.entity.EquipmentSlot;

public class RecipeButton extends ItemButton {
    public final EquipmentSlot slot;
    public final SuperheroSuitRecipe recipe;
    public RecipeButton(int x, int y, int width, int height, OnPress onPress, EquipmentSlot slot, SuperheroSuitRecipe recipe) {
        super(x, y, width, height, onPress, recipe.getResult(slot).get());
        this.slot = slot;
        this.recipe = recipe;
    }
}
