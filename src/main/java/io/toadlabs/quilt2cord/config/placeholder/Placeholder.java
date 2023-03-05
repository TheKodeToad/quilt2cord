package io.toadlabs.quilt2cord.config.placeholder;

/**
 * "Basic" placeholder replacement.
 *
 * @author me
 *
 */
public final class Placeholder {

	public static String process(String input) throws ParseException {
		Placeholder placeholder = new Placeholder(input);
		placeholder.process();
		return placeholder.result.toString();
	}

	// internal

	private final String input;
	private int cursor = 0;
	private final StringBuilder result = new StringBuilder();
	private final StringBuilder expression = new StringBuilder();

	private Placeholder(String input) {
		this.input = input;
	}

	int cursor() {
		return cursor;
	}

	private char current() {
		return input.charAt(cursor);
	}

	private char peek() {
		if (cursor + 1 >= input.length())
			return '\0';

		return input.charAt(cursor + 1);
	}

	private char read() {
		if (!hasRemaining())
			return '\0';

		return input.charAt(++cursor);
	}

	private boolean hasRemaining() {
		return cursor < input.length();
	}

	private void passthrough() {
		result.append(current());
	}

	private void process() throws ParseException {
		cursor--;

		while (hasRemaining()) {
			// wait for dollar
			while (hasRemaining() && read() != '$' && peek() != '{') {
				passthrough();
				continue;
			}

			if (!hasRemaining()) {
				passthrough();
				break;
			}

			while (read() != '}') {
				if (current() == '\\') {
					switch (read()) {
						case '\\':
							expression.append("\\");
							break;
						case '}':
							expression.append("}");
							break;
						case 'n':
							// would you need this?
							expression.append('n');
							break;
						default:
							throw new ParseException("Invalid escape, expected one of the following: '\\', ''', '}'", this);
					}
				}
			}
		}

	}

}
