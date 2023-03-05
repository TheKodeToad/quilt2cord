package io.toadlabs.quilt2cord.config;

import static io.toadlabs.quilt2cord.Quilt2cord.LOGGER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

public final class Config implements ConfigPart {

	public String token = "";
	public ChatConfig chat = new ChatConfig();

	public void load(Path file) throws IOException {
		if (!Files.exists(file))
			return;

		try {
			try (JsonReader reader = JsonReader.json5(file)) {
				read(reader);
			}
		} catch (IOException | IllegalStateException error) {
			LOGGER.warn("Could not read config", error);
		}

		if (token.isBlank())
			token = System.getProperty("io.toadlabs.quilt2cord.token", null);
		if (token == null)
			token = System.getenv("QUILT2CORD_TOKEN");
	}

	public void save(Path file) throws IOException {
		try (JsonWriter writer = JsonWriter.json5(file)) {
			write(writer);
		}
	}

	@Override
	public void read(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (key.equals("token"))
				token = reader.nextString();
			else if (key.equals("chat"))
				chat.read(reader);
		}
		reader.endObject();
	}

	@Override
	public void write(JsonWriter writer) throws IOException {
		writer.beginObject();
		writer.comment("The bot's token");
		writer.comment(
				"Either use java [...] -Dio.toadlabs.quilt2cord.token=token -jar [...] (system property) or QUILT2CORD_TOKEN=token java [...] (environment variable)");
		writer.comment("Your host hopefully provides a way to use either of these!");
		writer.name("token");
		writer.value(token);
		writer.comment("The chat configuration");
		writer.name("chat");
		chat.write(writer);
		writer.endObject();
	}

}
