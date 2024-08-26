package mc.duzo.timeless.suit.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.Suit;

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

    @Override
    public TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity user, Hand hand) {
        if (this.isBinding()) return TypedActionResult.fail(user.getStackInHand(hand));

        return super.equipAndSwap(item, world, user, hand);
    }
}
