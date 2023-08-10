package com.duzo.superhero.ids.impls;

import com.duzo.superhero.capabilities.SuperheroCapabilities;
import com.duzo.superhero.capabilities.SuperheroCapability;
import com.duzo.superhero.ids.AbstractIdentifier;
import com.duzo.superhero.recipes.SuperheroSuitRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;
import static com.duzo.superhero.entities.ironman.IronManEntity.nameFromSlot;

public class IdentifierBuilder extends AbstractIdentifier {
    protected boolean slim = false;
    protected boolean auto = true;
    protected String prefix = "Superhero";
    protected boolean defaultRenderer = true;
    public IdentifierBuilder(String name) {
        super(name);
    }

    public IdentifierBuilder capabilities(SuperheroCapability... caps) {
        for (SuperheroCapability cap : caps) {
            this.caps.add(cap);
        }
        return this;
    }
    public IdentifierBuilder capabilities(SuperheroCapabilities caps) {
        for (SuperheroCapability cap : caps) {
            this.caps.add(cap);
        }
        return this;
    }


    public IdentifierBuilder slim(boolean slim) {
        this.slim = slim;
        return this;
    }

    @Override
    public boolean isSlim() {
        return this.slim;
    }


    public IdentifierBuilder autoAdd(boolean add) {
        this.auto = add;
        return this;
    }

    @Override
    public boolean autoAdd() {
        return this.auto;
    }

    /**
     * The name for the superhero, shown in the item as "NAME Chestplate" etc
     * @param name
     * @return
     */
    public IdentifierBuilder itemPrefix(String name) {
        this.prefix = name;
        return this;
    }

    @Override
    public String getLangFileName(EquipmentSlot slot) {
        String slotName = fileNameToUsable(nameFromSlot(slot));

        return this.prefix + " " + slotName;
    }


    public IdentifierBuilder usesDefaultRenderer(boolean val) {
        this.defaultRenderer = val;
        return this;
    }

    @Override
    public boolean usesDefaultRenderer() {
        return this.defaultRenderer;
    }

    public IdentifierBuilder texture(String location) {
        return this.texture(new ResourceLocation(location));
    }
    public IdentifierBuilder texture(ResourceLocation location) {
        this.texture = location;
        return this;
    }

    public IdentifierBuilder lightmap(String location) {
        return this.lightmap(new ResourceLocation(location));
    }
    public IdentifierBuilder lightmap(ResourceLocation location) {
        this.lightmap = location;
        return this;
    }

    public IdentifierBuilder recipe(SuperheroSuitRecipe recipe) {
        this.recipe = recipe;
        return this;
    }
    public IdentifierBuilder icon(Supplier<ItemStack> supplier) {
        this.icon = supplier;
        return this;
    }
}
