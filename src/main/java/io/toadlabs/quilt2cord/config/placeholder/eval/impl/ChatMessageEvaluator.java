package io.toadlabs.quilt2cord.config.placeholder.eval.impl;

import org.quiltmc.qsl.chat.api.types.ChatC2SMessage;

import io.toadlabs.quilt2cord.config.placeholder.eval.Evaluator;

public final class ChatMessageEvaluator implements Evaluator {

	private final ChatC2SMessage message;

	public ChatMessageEvaluator(ChatC2SMessage message) {
		this.message = message;
	}

	@Override
	public String eval(String expression) {
		String standard = StandardPlayerExpressions.eval(message.getPlayer(), expression);
		if (standard != null)
			return standard;

		if (expression.equals("message"))
			return message.getMessage();

		return null;
	}

}
