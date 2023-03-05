package io.toadlabs.quilt2cord.handler;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;

import java.util.EnumSet;

import org.quiltmc.qsl.chat.api.QuiltChatEvents;
import org.quiltmc.qsl.chat.api.QuiltMessageType;
import org.quiltmc.qsl.chat.api.types.AbstractChatMessage;
import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;

import io.toadlabs.quilt2cord.Bot;
import io.toadlabs.quilt2cord.config.ChatConfig;
import io.toadlabs.quilt2cord.config.placeholder.ParseException;
import io.toadlabs.quilt2cord.config.placeholder.Placeholder;
import io.toadlabs.quilt2cord.config.placeholder.eval.impl.MinecraftMessageEvaluator;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public final class MinecraftChatHandler {

	private final ChatConfig config;
	private final TextChannel channel;

	public MinecraftChatHandler(Bot bot, TextChannel channel) {
		config = bot.mod().config().chat;
		this.channel = channel;
	}

	public void register() {
		QuiltChatEvents.AFTER_PROCESS.register(EnumSet.of(QuiltMessageType.CHAT), this::chat);
	}

	public void chat(AbstractChatMessage<?> abstractMessage) {
		if (abstractMessage instanceof ChatC2SMessage message) {
			String text = message.getMessage();
			try {
				text = Placeholder.process(config.discordFormat, new MinecraftMessageEvaluator(message));
			} catch (ParseException error) {
				LOGGER.error("Failed to parse placeholder for Minecraft message", error);
				return;
			} catch (Throwable error) {
				LOGGER.error("Failed to apply placeholder for Minecraft message", error);
				return;
			}
			channel.sendMessage(text).queue();
		}
	}

}
