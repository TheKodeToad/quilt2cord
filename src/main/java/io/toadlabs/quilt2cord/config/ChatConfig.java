package io.toadlabs.quilt2cord.config;

import java.io.IOException;

import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

public final class ChatConfig implements ConfigPart {

	public String channel = "";
	public String discordFormat = "<${player}> ${message}";

	@Override
	public void read(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (key.equals("channel"))
				channel = reader.nextString();
			else if (key.equals("discord_format"))
				discordFormat = reader.nextString();
		}
		reader.endObject();
	}

	@Override
	public void write(JsonWriter writer) throws IOException {
		writer.beginObject();

		writer.comment("The Discord channel ID to bridge");
		writer.name("channel");
		writer.value(channel);

		writer.comment("The format of the message to send to Discord");
		writer.name("discord_format");
		writer.value(discordFormat);

		writer.endObject();
	}

}
