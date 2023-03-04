package io.toadlabs.quilt2cord;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.toadlabs.quilt2cord.config.Config;

public final class Quilt2cord implements ModInitializer {

	public static final String NAME = "quilt2cord";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	private Bot bot = new Bot(this);
	private Config config = new Config();

	public Bot getBot() {
		return bot;
	}

	public Config getConfig() {
		return config;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		try {
			bot.start();
		} catch (InterruptedException error) {
			LOGGER.error("Oh dear!.. {} couldn't start", NAME, error);
			bot = null;
			config = null;
			return;
		}
	}

}
