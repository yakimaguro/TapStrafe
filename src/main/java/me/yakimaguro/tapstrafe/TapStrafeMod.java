package me.yakimaguro.tapstrafe;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TapStrafeMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("TapStrafe");

	@Override
	public void onInitialize() {
		LOGGER.info("onInitialize");
	}
}
