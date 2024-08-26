package mc.duzo.timeless;

import mc.duzo.timeless.suit.SuitRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Timeless implements ModInitializer {
	public static final String MOD_ID = "timeless";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SuitRegistry.init();
	}
}