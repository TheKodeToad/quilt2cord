package io.toadlabs.quilt2cord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;
import static io.toadlabs.quilt2cord.Quilt2cord.NAME;

public final class Bot {

	private final Quilt2cord mod;
	private JDA jda;

	public Bot(Quilt2cord mod) {
		this.mod = mod;
	}

	public void start() throws InterruptedException {
		LOGGER.info("Starting {}...", NAME);
		jda = JDABuilder.createLight(mod.getConfig().token).build();
		LOGGER.info("Waiting for the bot to be ready...");
		jda.awaitReady();
	}

}
