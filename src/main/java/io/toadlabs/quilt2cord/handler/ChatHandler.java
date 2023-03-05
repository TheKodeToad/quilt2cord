package io.toadlabs.quilt2cord.handler;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;
import java.util.EnumSet;

import org.quiltmc.qsl.chat.api.QuiltChatEvents;
import org.quiltmc.qsl.chat.api.QuiltMessageType;
import org.quiltmc.qsl.chat.api.types.AbstractChatMessage;
import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;

import io.toadlabs.quilt2cord.Bot;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class ChatHandler {

	private final TextChannel channel;

	public ChatHandler(Bot bot) {
		String channelId = bot.mod().config().chat.channel;
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
		if (abstractMessage instanceof ChatC2SMessage message)
			channel.sendMessage(message.getMessage()).queue();
	}

}
