package mc.duzo.timeless.power.impl;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;

public class IceOverPower extends Power {
    private final Identifier id;

    public IceOverPower() {
        this.id = new Identifier(Timeless.MOD_ID, "ices_over");
    }

    @Override
    public boolean run(ServerPlayerEntity player) {
        return false;
    }

    @Override
    public void tick(ServerPlayerEntity player) {
        if (player.getY() < 180) return;

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 5 * 20, 1));

        if (player.getY() > 210 && FlightPower.hasFlight(player)) {
            FlightPower.setFlight(player, false);
        }
    }

    @Override
    public void onLoad(ServerPlayerEntity player) {

    }

    @Override
    public Identifier id() {
        return this.id;
    }
}
