package com.duzo.superhero.entities;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class IronManArmourMarks {
    private static final Map<String, NonNullList<Item>> marks = new HashMap<>();

    /**
     * Marks are registered here:
     */
    public static void init() {
        register("mark_7", NonNullList.of(Items.LEATHER_HELMET,Items.LEATHER_CHESTPLATE,Items.LEATHER_LEGGINGS,Items.LEATHER_BOOTS));
    }
    public static void register(String mark,NonNullList<Item> armour) {
        marks.put(mark,armour);
    }
    public static NonNullList<Item> get(String id) {
        return marks.get(id);}
}
