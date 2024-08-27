package mc.duzo.timeless.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import mc.duzo.timeless.client.gui.JarvisGui;
import mc.duzo.timeless.client.keybind.TimelessKeybinds;
import mc.duzo.timeless.client.network.ClientNetwork;
import mc.duzo.timeless.suit.client.ClientSuitRegistry;

public class TimelessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetwork.init();
        ClientSuitRegistry.init();
        TimelessKeybinds.init();

        HudRenderCallback.EVENT.register((stack, delta) -> {
            JarvisGui.render(stack, delta);
        });
    }
}
