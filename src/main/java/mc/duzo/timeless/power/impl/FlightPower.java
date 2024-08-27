package mc.duzo.timeless.power.impl;

import mc.duzo.timeless.Timeless;
import mc.duzo.timeless.power.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FlightPower extends Power {
	private final Identifier id;

	public FlightPower() {
		this.id = new Identifier(Timeless.MOD_ID, "flight");
	}

	@Override
	public boolean run(ServerPlayerEntity player) {
		return false;
	}

	@Override
	public void tick(ServerPlayerEntity player) {
		if (player.isSneaking() && !player.isOnGround()) {
			player.setVelocity(player.getVelocity().add(0, 0.25, 0));
			player.velocityModified = true;
		}
	}

	@Override
	public Identifier id() {
		return this.id;
	}
}
