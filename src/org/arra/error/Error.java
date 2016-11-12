package org.arra.error;

import java.io.PrintStream;

public class Error {
	private PrintStream ps;
	private String msg;
	private Integer exit;

	protected Error() {
		this("Arra: INTERNAL ERROR", System.err, -1);
	}

	protected Error(String msg) {
		this(msg, System.err, -1);
	}

	protected Error(String msg, PrintStream ps, Integer exit) {
		this.msg = msg;
		this.ps = ps;
		this.exit = exit;
	}

	public void exec() {
		ps.print(msg);
		if (exit != null)
			System.exit(exit);
	}
}
