package io.toadlabs.quilt2cord;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Quilt2Cord implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("quilt2cord");

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Where did my motivation go!");
	}
}
