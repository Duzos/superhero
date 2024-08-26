package mc.duzo.timeless.client.keybind;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.network.c2s.UsePowerC2SPacket;

public class PowerKeybind extends Keybind {
    private final int power;

    public PowerKeybind(int power, int key) {
        super(
            new KeyBinding(
                "key." + Timeless.MOD_ID + ".power_" + power,
                InputUtil.Type.KEYSYM,
                key,
                "key.categories." + Timeless.MOD_ID
            ),
            (player) -> ClientPlayNetworking.send(new UsePowerC2SPacket(power))
        );

        this.power = power;
    }
}
