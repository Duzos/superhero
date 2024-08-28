package mc.duzo.timeless.suit;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;

import mc.duzo.timeless.datagen.provider.lang.Translatable;
import mc.duzo.timeless.power.PowerList;
import mc.duzo.timeless.registry.Identifiable;
import mc.duzo.timeless.suit.client.ClientSuit;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;
import mc.duzo.timeless.suit.item.SuitItem;
import mc.duzo.timeless.suit.set.SuitSet;

public abstract class Suit implements Identifiable, Translatable {
    public static Optional<Suit> findSuit(LivingEntity entity) {
        if (!(entity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SuitItem item)) return Optional.empty();
        return Optional.ofNullable(item.getSuit());
    }

    public abstract boolean isBinding();
    public abstract SuitSet getSet();
    public abstract PowerList getPowers();
    public abstract SoundEvent getStepSound();

    @Override
    public String getTranslationKey() {
        return this.id().getNamespace() + ".suit." + this.id().getPath();
    }

    @Environment(EnvType.CLIENT)
    public ClientSuit toClient() {
        ClientSuit found = ClientSuitRegistry.REGISTRY.get(this.id());
        if (found != null) return found;

        return ClientSuitRegistry.register(this.createClient()); // todo registry may be frozen
    }

    /**
     * creates the client-side version of this suit
     * this MUST have the annotation @Environment(EnvType.CLIENT) or your game will crash
     */
    @Environment(EnvType.CLIENT)
    protected abstract ClientSuit createClient();
}
