package io.toadlabs.quilt2cord.handler;

import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;

import net.minecraft.server.MinecraftServer;

public class MinecraftServerCaptureHandler {

	public static MinecraftServer server;

	public static void register() {
		ServerLifecycleEvents.STARTING.register(MinecraftServerCaptureHandler::onServerStart);
	}

	public static void onServerStart(MinecraftServer server) {
		MinecraftServerCaptureHandler.server = server;
	}

}
