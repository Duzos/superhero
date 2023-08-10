package com.duzo.superhero.recipes;

import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.ids.SuperheroIdentifierRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * A hashmap with each armour slot which each has an item and a set of ingredients to make the item
 */
public class SuperheroSuitRecipe extends HashMap<EquipmentSlot, HashMap<Supplier<ItemStack>,List<ItemStack>>> implements INBTSerializable<CompoundTag> {

    public boolean verifyRecipeExistsInIdentifiers() {
        List<SuperheroSuitRecipe> list = List.of();

        for (AbstractIdentifier identifier : SuperheroIdentifierRegistry.IDS_REGISTRY.get().getValues()) {
            list.add(identifier.getRecipe());
        }

        for (SuperheroSuitRecipe recipe : list) {
            if (recipe.equals(this)) return true;
        }
        return false;
    }

    public Supplier<ItemStack> getResult(EquipmentSlot slot) {
        if (!this.containsKey(slot)) return null;
        if (this.get(slot).keySet().stream().findAny().isEmpty()) return null;

        return this.get(slot).keySet().stream().findAny().get();
    }
    public List<ItemStack> getRecipe(EquipmentSlot slot) {
        return this.get(slot).get(this.getResult(slot));
    }
    public SuperheroSuitRecipe putRecipe(EquipmentSlot slot, Supplier<ItemStack> result, List<ItemStack> recipe) {
        HashMap<Supplier<ItemStack>, List<ItemStack>> map = new HashMap<>();
        map.put(result,recipe);
        this.put(slot, map);
        return this;
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        for (EquipmentSlot slot : this.keySet()) {
            CompoundTag t = new CompoundTag();
            HashMap<Supplier<ItemStack>, List<ItemStack>> map = this.get(slot);

            if (map.keySet().stream().findAny().isEmpty()) return tag;

            Supplier<ItemStack> result = map.keySet().stream().findAny().get();
            List<ItemStack> recipe = map.get(result);

            t.put("result",result.get().serializeNBT());

            CompoundTag recipeTag = new CompoundTag();
            recipe.forEach(item -> recipeTag.put(String.valueOf(recipe.indexOf(item)),item.serializeNBT()));
            t.put("recipe",recipeTag);

            tag.put(slot.getName().toLowerCase(),t);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (tag.get(slot.getName().toLowerCase()) == null) continue;

            CompoundTag t = tag.getCompound(slot.getName().toLowerCase());
            HashMap<Supplier<ItemStack>, List<ItemStack>> map = new HashMap<>();

            ItemStack result = ItemStack.of(t.getCompound("result"));

            CompoundTag recipeTag = t.getCompound("recipe");
            List<ItemStack> list = new ArrayList<>();
            for (String key : recipeTag.getAllKeys()) {
                list.add(ItemStack.of(recipeTag.getCompound(key)));
            }

            map.put(() -> result,list);
            this.put(slot,map);
        }
    }

    public static SuperheroSuitRecipe fromNBT(CompoundTag tag) {
        SuperheroSuitRecipe recipe = new SuperheroSuitRecipe();
        recipe.deserializeNBT(tag);
        return recipe;
    }
}
