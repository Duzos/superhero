package mc.duzo.timeless.suit;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public abstract class SuitItem extends ArmorItem {
	private final Suit parent;

	protected SuitItem(Suit suit, ArmorMaterial material, Type type, Settings settings) {
		super(material, type, settings.maxCount(1));

		this.parent = suit;
	}

	public boolean isBinding() {
		return this.parent.isBinding();
	}
}
