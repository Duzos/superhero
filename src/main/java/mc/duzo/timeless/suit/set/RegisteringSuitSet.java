package mc.duzo.timeless.suit.set;

import java.util.function.BiFunction;

import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.Suit;
import mc.duzo.timeless.suit.SuitRegistry;
import mc.duzo.timeless.suit.item.SuitItem;

public class RegisteringSuitSet extends SuitSet {
    protected RegisteringSuitSet(Identifier id, Suit suit, SuitItem... items) {
        super(id, suit, items);

        this.register();
    }

    public RegisteringSuitSet(Suit suit, SuitItem... items) {
        super(suit, items);

        this.register();
    }

    public RegisteringSuitSet(Suit suit, BiFunction<Suit, ArmorItem.Type, SuitItem> func) {
        super(suit, func);

        this.register();
    }

    private void register() {
        this.registerSuit();

        for (SuitItem item : this.values()) {
            this.registerItem(item);
        }
    }
    private void registerItem(SuitItem item) {
        Register.Items.register(item.id(), item);
    }
    private void registerSuit() {
        SuitRegistry.register(this.suit);
    }
}
