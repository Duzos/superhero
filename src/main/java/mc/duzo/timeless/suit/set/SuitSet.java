package mc.duzo.timeless.suit.set;

import java.util.HashMap;
import java.util.function.BiFunction;

import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.item.SuitItem;

public class SuitSet extends HashMap<ArmorItem.Type, SuitItem> implements Identifiable {
    protected final Suit suit;
    private final Identifier id;

    protected SuitSet(Identifier id, Suit suit, SuitItem... items) {
        this.id = id;
        this.suit = suit;
        this.put(items);
    }
    public SuitSet(Suit suit, SuitItem... items) {
        this(suit.id().withSuffixedPath("_set"), suit, items);
    }
    public SuitSet(Suit suit, BiFunction<Suit, ArmorItem.Type, SuitItem> func) {
        this(suit, func.apply(suit, ArmorItem.Type.HELMET), func.apply(suit, ArmorItem.Type.CHESTPLATE), func.apply(suit, ArmorItem.Type.LEGGINGS), func.apply(suit, ArmorItem.Type.BOOTS));
    }
    protected void put(SuitItem item) {
        if (!(this.suit.equals(item.getSuit())))
            throw new IllegalArgumentException("SuitItem does not match this.suit");

        this.put(item.getType(), item);
    }
    private void put(SuitItem... items) {
        for (SuitItem i : items) {
            this.put(i);
        }
    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
