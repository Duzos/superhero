package mc.duzo.timeless.client;

import net.fabricmc.api.ClientModInitializer;

import mc.duzo.timeless.client.keybind.TimelessKeybinds;
import mc.duzo.timeless.client.network.ClientNetwork;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;

public class TimelessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetwork.init();
        ClientSuitRegistry.init();
        TimelessKeybinds.init();
    }
}
