package io.toadlabs.quilt2cord.config.placeholder;

public final class ParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParseException(String message, Placeholder context) {
		super(message + " - at col " + context.cursor());
	}

}
