package io.toadlabs.quilt2cord.config.placeholder.eval.impl;

import io.toadlabs.quilt2cord.config.placeholder.eval.Evaluator;
import net.dv8tion.jda.api.entities.Message;

public final class DiscordMessageEvaluator implements Evaluator {

	private final Message message;

	public DiscordMessageEvaluator(Message message) {
		this.message = message;
	}

	@Override
	public String eval(String expression) {
		switch (expression) {
		case "user":
			return message.getMember().getEffectiveName();
		case "username":
			return message.getMember().getUser().getName();
		case "user_id":
			return message.getMember().getUser().getId();
		case "message":
			return message.getContentDisplay();
		case "message_id":
			return message.getId();
		}
		return null;
	}

}
