package mc.duzo.timeless.suit;

import mc.duzo.timeless.registry.Identifiable;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;

public abstract class SuitItem extends ArmorItem implements Identifiable {
	private final Suit parent;

	protected SuitItem(Suit suit, ArmorMaterial material, Type type, Settings settings) {
		super(material, type, settings.maxCount(1));

		this.parent = suit;
	}

	public boolean isBinding() {
		return this.parent.isBinding();
	}

	@Override
	public Identifier id() {
		return this.parent.id().withSuffixedPath("_" + this.type.getName());
	}

	public Suit getSuit() {
		return this.parent;
	}
}
