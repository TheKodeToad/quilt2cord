package io.toadlabs.quilt2cord.config.placeholder.eval.impl;

import net.minecraft.entity.player.PlayerEntity;

public final class StandardPlayerExpressions {

	public static String eval(PlayerEntity player, String expression) {
		switch (expression) {
		case "player":
			return player.getEntityName();
		case "uuid":
			return player.getUuidAsString();
		}

		return null;
	}

}
