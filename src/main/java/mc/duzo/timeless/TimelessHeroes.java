package mc.duzo.timeless;

import mc.duzo.timeless.heroes.HeroRegistry;
import mc.duzo.timeless.network.TimelessServerNetworking;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimelessHeroes implements ModInitializer {
	public static final String MOD_ID = "timeless";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		TimelessServerNetworking.init();

		HeroRegistry.init();
	}
}