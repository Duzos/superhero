package com.duzo.superhero.capabilities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.duzo.superhero.entities.ironman.IronManEntity.fileNameToUsable;

public abstract class AbstractCapability {
    protected String name;
    protected abilityRunner runner;
    public AbstractCapability(String name) {
        this.name = name;
        this.runner = new abilityRunner() {};
    }
    public String getSerializedName() {
        return this.name.toLowerCase();
    }

    public void runAbility(int num, Player player) {
        this.runner.runAbility(num,player);
    }

    /**
     * Tick thats ran when this suit is equipped
     *
     * @param stack
     * @param level
     * @param player
     */
    public void tick(ItemStack stack, Level level, Player player) {
        this.runner.tick(stack,level,player);
    }

    /**
     * Tick thats ran when this suit is not equipped and is sat in the inventory.
     *
     * @param stack
     * @param level
     * @param player
     */
    public void unequippedTick(ItemStack stack, Level level, Player player) {
        this.runner.unequippedTick(stack,level,player);
    }

    public String getNameForText() {
        return fileNameToUsable(this.getSerializedName());
    }

    public interface abilityRunner {
        default void runAbility(int num, Player player) {

        }
        default void tick(ItemStack stack, Level level, Player player) {

        }
        default void unequippedTick(ItemStack stack, Level level, Player player) {

        }
    }
}
