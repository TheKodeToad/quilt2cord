package io.toadlabs.quilt2cord;

import java.nio.file.Path;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.toadlabs.quilt2cord.config.Config;

public final class Quilt2cord implements ModInitializer {

	public static final String NAME = "quilt2cord";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	private Bot bot = new Bot(this);
	private Config config = new Config();

	public Bot bot() {
		return bot;
	}

	public Config config() {
		return config;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		try {
			Path configFile = QuiltLoader.getConfigDir().resolve(NAME + ".json5");
			config.load(configFile);
			config.save(configFile);
			if (config.token != null) {
				bot.start();
				return;
			} else {
				LOGGER.warn("In order for {} to function, you must provide a token in the config ({})", NAME,
						configFile);
			}
		} catch (Throwable error) {
			LOGGER.error("Oh dear!.. {} couldn't start", NAME, error);
		}
		bot = null;
		config = null;
	}

}
