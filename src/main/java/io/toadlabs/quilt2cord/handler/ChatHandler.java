package io.toadlabs.quilt2cord.handler;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;
import java.util.EnumSet;

import org.quiltmc.qsl.chat.api.QuiltChatEvents;
import org.quiltmc.qsl.chat.api.QuiltMessageType;
import org.quiltmc.qsl.chat.api.types.AbstractChatMessage;
import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;

import io.toadlabs.quilt2cord.Bot;
import io.toadlabs.quilt2cord.config.Config;
import io.toadlabs.quilt2cord.config.placeholder.ParseException;
import io.toadlabs.quilt2cord.config.placeholder.Placeholder;
import io.toadlabs.quilt2cord.config.placeholder.eval.impl.ChatMessageEvaluator;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class ChatHandler {

	private final Config config;
	private final TextChannel channel;

	public ChatHandler(Bot bot) {
		this.config = bot.mod().config();
		String channelId = config.chat.channel;
		if (!channelId.isEmpty()) {
			this.channel = bot.jda().getTextChannelById(bot.mod().config().chat.channel);
			if (channel == null) {
				LOGGER.warn("Cannot find channel with ID {} from config->chat->channel", channelId);
			}
		} else
			channel = null;
	}

	public void register() {
		if (channel != null)
			QuiltChatEvents.AFTER_PROCESS.register(EnumSet.of(QuiltMessageType.CHAT), this::chat);
	}

	public void chat(AbstractChatMessage<?> abstractMessage) {
		if (abstractMessage instanceof ChatC2SMessage message) {
			String text = message.getMessage();
			try {
				text = Placeholder.process(config.chat.discordFormat, new ChatMessageEvaluator(message));
			} catch (ParseException error) {
				LOGGER.error("Failed to parse placeholder {}", error);
			} catch (Throwable error) {
				LOGGER.error("Failed to apply placeholder {}", error);
			}
			channel.sendMessage(text).queue();
		}
	}

}
