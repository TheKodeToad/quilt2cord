package io.toadlabs.quilt2cord.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

public final class Config {

	public String token = "";

	public void load(Path file) throws IOException {
		if (!Files.exists(file))
			return;

		try (JsonReader reader = JsonReader.json5(file)) {
			reader.beginObject();
			while (reader.hasNext()) {
				String key = reader.nextName();
				if (key.equals("token"))
					token = reader.nextString();
			}
			reader.endObject();
		}

		if (token.isBlank())
			token = System.getProperty("io.toadlabs.quilt2cord.token", null);
		if (token == null)
			token = System.getenv("QUILT2CORD_TOKEN");
	}

	public void save(Path file) throws IOException {
		try (JsonWriter writer = JsonWriter.json5(file)) {
			writer.beginObject();
			writer.comment("The bot's token");
			writer.comment(
					"Either use java [...] -Dio.toadlabs.quilt2cord.token=token -jar [...] (system property) or QUILT2CORD_TOKEN=token java [...] (environment variable)");
			writer.comment("Your host hopefully provides a way to use either of these!");
			writer.name("token");
			writer.value(token);
			writer.endObject();
		}
	}

}
