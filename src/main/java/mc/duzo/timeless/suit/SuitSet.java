package mc.duzo.timeless.suit;

import mc.duzo.timeless.registry.Identifiable;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class SuitSet extends HashMap<ArmorItem.Type, SuitItem> implements Identifiable {
	private final Suit suit;
	private final Identifier id;

	protected SuitSet(Identifier id, Suit suit, SuitItem... items) {
		this.id = id;
		this.suit = suit;
		this.put(items);
	}
	public SuitSet(Suit suit, SuitItem... items) {
		this(suit.id().withSuffixedPath("_set"), suit, items);
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
