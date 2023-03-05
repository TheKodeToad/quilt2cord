package io.toadlabs.quilt2cord.config;

import java.io.IOException;

import org.quiltmc.json5.JsonReader;
import org.quiltmc.json5.JsonWriter;

public interface ConfigPart {

	void read(JsonReader reader) throws IOException;

	void write(JsonWriter writer) throws IOException;

}
