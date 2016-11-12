package org.arra.error;

import java.io.PrintStream;

public class ErrorFactory {

	private final String SYNTAX_ERROR = "Arra: SYNTAX ERROR";
	private final String INTERNAL_ERROR = "Arra: INTERNAL ERROR";
	private final String NULL_ERROR = "Arra: NULL ERROR";
	private final String COMPILATION_ERROR = "Arra: COMPILATION ERROR";
	private final String MATH_ERROR = "Arra: MATH ERROR";
	// private final String CHUCK_NORRIS_ERROR = "Arra: CHUCK NORRIS ERROR\n
	// Wait, what?";

	public Error createCustomError(String msg, PrintStream ps, Integer exit) {
		return new Error(msg, ps, exit);
	}

	public void customError(String msg, PrintStream ps, Integer exit) {
		new Error(msg, ps, exit).exec();
	}

	public void syntaxError(String msg) {
		if (msg == null || msg == "")
			new Error(SYNTAX_ERROR, System.err, -1).exec();
		else
			new Error(SYNTAX_ERROR + ": " + msg, System.err, -1).exec();
	}

	public void internalError(String msg) {
		if (msg == null || msg == "")
			new Error(INTERNAL_ERROR, System.err, -1).exec();
		else
			new Error(INTERNAL_ERROR + ": " + msg, System.err, -1).exec();
	}

	public void nullError(String msg) {
		if (msg == null || msg == "")
			new Error(NULL_ERROR, System.err, -1).exec();
		else
			new Error(NULL_ERROR + ": " + msg, System.err, -1).exec();
	}

	public void compilationError(String msg) {
		if (msg == null || msg == "")
			new Error(COMPILATION_ERROR, System.err, -1).exec();
		else
			new Error(COMPILATION_ERROR + ": " + msg, System.err, -1).exec();
	}

	public void mathError(String msg) {
		if (msg == null || msg == "")
			new Error(MATH_ERROR, System.err, -1).exec();
		else
			new Error(MATH_ERROR + ": " + msg, System.err, -1).exec();
	}
}
