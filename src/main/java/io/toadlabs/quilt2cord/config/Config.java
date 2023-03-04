package io.toadlabs.quilt2cord.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;

public final class Config {

	private final Jankson jankson = Jankson.builder().registerTypeFactory(Config.class, () -> this).build();

	public String token = "<PUT IT HERE>";

	public void load(Path file) throws SyntaxError, IOException {
		if (!Files.exists(file)) // if it's not there, write it!
			save(file);
		else
			jankson.fromJson(Files.readString(file), Config.class);
	}

	public void save(Path file) throws IOException {
		Files.writeString(file, jankson.toJson(this).toJson());
	}

}
