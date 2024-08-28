package mc.duzo.timeless.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.PowerRegistry;
import mc.duzo.timeless.power.impl.FlightPower;
import mc.duzo.timeless.power.impl.HoverPower;
import mc.duzo.timeless.suit.Suit;

public class JarvisGui {
    private static final Identifier HUD = new Identifier(Timeless.MOD_ID, "textures/gui/jarvis/hud.png");

    public static void render(DrawContext context, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();

        ClientPlayerEntity player = client.player;
        if (player == null) return;
        if (client.gameRenderer.getCamera().isThirdPerson()) return;

        Suit suit = Suit.findSuit(player).orElse(null);
        if (suit == null) return;
        if (!(suit.hasPower(PowerRegistry.JARVIS))) return;

        // main overlay
        context.drawTexture(HUD, 0, 0, 0, 0, getScreenWidth(), getScreenHeight(), getScreenWidth(), getScreenHeight());

        // todo - positioning of elements

        // position
        String i = Math.round(player.getX()) + ", " + Math.round(player.getY()) + ", " + Math.round(player.getZ());
        context.drawCenteredTextWithShadow(client.textRenderer, i, getScreenWidth() / 2, 10, 0xFFFFFF);

        // direction
        String d = Math.round(player.getPitch()) + ", " + Math.round(player.getYaw());
        context.drawCenteredTextWithShadow(client.textRenderer, d, getScreenWidth() / 2, 20, 0xFFFFFF);
        d = player.getMovementDirection().getName().toUpperCase();
        context.drawCenteredTextWithShadow(client.textRenderer, d, getScreenWidth() / 2, 30, 0xFFFFFF);

        // speed
        double deltaX = player.getX() - player.prevX;
        double deltaZ = player.getZ() - player.prevZ;
        double deltaY = player.getY() - player.prevY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        String s = Math.round(distance * 20) + " m/s";
        context.drawCenteredTextWithShadow(client.textRenderer, s, getScreenWidth() / 2, 40, 0xFFFFFF);

        // has flight
        boolean hasFlight = FlightPower.hasFlight(player);
        if (hasFlight) {
            context.drawCenteredTextWithShadow(client.textRenderer, "Flight", getScreenWidth() / 2, 50, 0xFFFFFF);

            boolean hasHover = HoverPower.hasHover(player);
            if (hasHover) {
                context.drawCenteredTextWithShadow(client.textRenderer, "Hover", getScreenWidth() / 2, 60, 0xFFFFFF);
            }
        }
    }

    private static int getScreenWidth() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth();
    }
    private static int getScreenHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }
}
