package io.toadlabs.quilt2cord;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;
import static io.toadlabs.quilt2cord.Quilt2cord.NAME;

import java.util.EnumSet;
import java.util.List;

import io.toadlabs.quilt2cord.handler.DiscordChatHandler;
import io.toadlabs.quilt2cord.handler.MinecraftChatHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class Bot {

	private final Quilt2cord mod;
	private JDA jda;

	public Bot(Quilt2cord mod) {
		this.mod = mod;
	}

	public void start() throws InterruptedException {
		LOGGER.info("Starting {}...", NAME);
		jda = JDABuilder.createLight(mod.config().token).enableIntents(EnumSet.of(GatewayIntent.MESSAGE_CONTENT))
				.build();
		LOGGER.info("Waiting for the bot to be ready...");
		jda.awaitReady();

		String channelId = mod.config().chat.channel;
		if (!channelId.isEmpty()) {
			TextChannel channel = jda.getTextChannelById(channelId);
			if (channel != null) {
				new MinecraftChatHandler(this, channel).register();
				new DiscordChatHandler(this, channel).register();
			} else {
				LOGGER.warn("Cannot find channel by id {} (config->chat->channel)", channelId);
			}
		}
	}

	public Quilt2cord mod() {
		return mod;
	}

	public JDA jda() {
		return jda;
	}

}
