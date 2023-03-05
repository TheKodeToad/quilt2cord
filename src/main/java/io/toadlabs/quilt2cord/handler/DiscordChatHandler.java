package io.toadlabs.quilt2cord.handler;

import io.toadlabs.quilt2cord.Bot;
import io.toadlabs.quilt2cord.config.ChatConfig;
import io.toadlabs.quilt2cord.config.placeholder.ParseException;
import io.toadlabs.quilt2cord.config.placeholder.Placeholder;
import io.toadlabs.quilt2cord.config.placeholder.eval.impl.DiscordMessageEvaluator;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.text.Text;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;
import static io.toadlabs.quilt2cord.handler.MinecraftServerCaptureHandler.server;

public final class DiscordChatHandler extends ListenerAdapter {

	private final Bot bot;
	private final ChatConfig config;
	private final TextChannel channel;

	public DiscordChatHandler(Bot bot, TextChannel channel) {
		this.bot = bot;
		config = bot.mod().config().chat;
		this.channel = channel;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getChannel().getIdLong() != channel.getIdLong())
			return;
		if (server == null)
			return;
		// TODO: pluralkit support?
		if (event.getMember().getUser().isBot())
			return;

		String text = event.getMessage().getContentDisplay();
		try {
			text = Placeholder.process(config.minecraftFormat, new DiscordMessageEvaluator(event.getMessage()));
		} catch (ParseException error) {
			LOGGER.error("Failed to parse placeholder for Discord message", error);
			return;
		} catch (Throwable error) {
			LOGGER.error("Failed to apply placeholder for Discord message", error);
			return;
		}
		server.getPlayerManager().broadcastSystemMessage(Text.literal(text), false);
	}

	public void register() {
		bot.jda().addEventListener(this);
	}

}
