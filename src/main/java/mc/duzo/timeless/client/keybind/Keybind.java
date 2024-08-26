package mc.duzo.timeless.client.keybind;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;

public class Keybind {
    private final KeyBinding binding;
    private final Consumer<AbstractClientPlayerEntity> action;
    private boolean wasHeld;

    public Keybind(KeyBinding binding, Consumer<AbstractClientPlayerEntity> action) {
        this.binding = binding;
        this.action = action;
    }

    public void tick(AbstractClientPlayerEntity player) {
        if (!binding.wasPressed()) {
            if (wasHeld) wasHeld = false;
            return;
        }

        if (wasHeld) return;

        wasHeld = true;

        action.accept(player);
    }

    public void register() {
        KeyBindingHelper.registerKeyBinding(this.binding);
        TimelessKeybinds.register(this);
    }
}
