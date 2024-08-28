package mc.duzo.timeless.suit.ironman;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.datagen.provider.lang.AutomaticEnglish;
import mc.duzo.timeless.registry.Register;
import mc.duzo.timeless.suit.Suit;

public abstract class IronManSuit extends Suit implements AutomaticEnglish {
    private final Identifier id;

    protected IronManSuit(Identifier id) {
        this.id = id;
    }
    protected IronManSuit(String mark, String modid) {
        this(new Identifier(modid, "iron_man_" + mark));
    }

    /**
     * For Timeless heroes ONLY
     * Addon mods should use other constructor
     */
    protected IronManSuit(String mark) {
        this(mark, Timeless.MOD_ID);
    }

    @Override
    public boolean isBinding() {
        return true;
    }

    @Override
    public Identifier id() {
        return this.id;
    }

    @Override
    public SoundEvent getStepSound() {
        return Register.Sounds.IRONMAN_STEP;
    }

    public abstract int getVerticalFlightModifier(boolean isSprinting);
    public abstract int getHorizontalFlightModifier(boolean isSprinting);
}
