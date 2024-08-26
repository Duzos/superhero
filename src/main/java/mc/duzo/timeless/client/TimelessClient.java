package mc.duzo.timeless.client;

import net.fabricmc.api.ClientModInitializer;

import mc.duzo.timeless.client.keybind.TimelessKeybinds;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;

public class TimelessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientSuitRegistry.init();

        TimelessKeybinds.init();
    }
}
