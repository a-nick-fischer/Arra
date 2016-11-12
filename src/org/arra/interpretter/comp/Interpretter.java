package org.arra.interpretter.comp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.arra.error.ErrorFactory;
import org.arra.interpretter.chunk.ArraArgs;
import org.arra.interpretter.chunk.ArraFunc;
import org.arra.interpretter.chunk.ArraValue;
import org.arra.main.Main;

public class Interpretter {

	private static final Interpretter INSTANCE = new Interpretter();
	private final Stack stack = Stack.getInstance();
	protected long repeat = 1;
	private String funcname = null;
	private ArrayList<ArraValue> funcbuf = null;
	private ArrayList<ArraArgs> funcargs = null;

	private Interpretter() {
	}

	public void repl() {
		int iter = 1;
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("ARRA REPL v" + Main.VERSION);
			while (true) {
				System.out.print(iter + ">");
				eval(sc.nextLine());
				iter++;
			}
		}
	}

	public void execfile(final String file) {
		File f = new File(file);
		try (Scanner sc = new Scanner(f)) {
			while (sc.hasNextLine())
				eval(sc.nextLine());

		} catch (FileNotFoundException e) {
			new ErrorFactory().nullError(file + " DO NOT EXIST");
		}
	}

	public void eval(final String line) {
		for (int i = 1; i <= repeat; i++) {
			final String[] arr = line.split(" ");

			if (line.startsWith("FUN") && arr.length == 2) {
				funcname = arr[1];
				funcbuf = new ArrayList<>();
				funcargs = new ArrayList<>();
				return;
			} else

			if (line.startsWith("END") && arr.length == 2) {
				if (funcname.equals(arr[1])) {
					final ArraValue[] vals = funcbuf.toArray(new ArraValue[0]);
					final ArraArgs[] args = funcargs.toArray(new ArraArgs[0]);
					Enviroment.getEnviroment().put(funcname, new ArraFunc(vals, args));
					funcbuf = null;
					funcname = null;
					funcargs = null;
					return;
				} else {
					new ErrorFactory().syntaxError("CANNOT CLOSE " + line.split(" ")[1]);
				}
			} else

			if (funcname != null) {
				final ArraValue val = ArraValue.ofFunction(line);
				funcbuf.add(val);
				funcargs.add(val.getArguments());
			} else {
				final ArraValue val = ArraValue.ofFunction(line);
				stack.add(val.call());
			}
		}
		if (repeat == 0) {
			repeat = 1;
		}
	}

	public static Interpretter getInstance() {
		return INSTANCE;
	}
}
