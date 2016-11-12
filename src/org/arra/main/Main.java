package org.arra.main;

import java.util.ArrayList;
import java.util.List;

import org.arra.interpretter.chunk.ArraValue;
import org.arra.interpretter.comp.Interpretter;

public final class Main {

	public final static double VERSION = 0.1;
	public static final List<String> ARGUMENTS = new ArrayList<>();
	public static final List<ArraValue> ARRAARGUMENTS = new ArrayList<>();

	public static final void main(final String[] args) {
		List<String> argsl = convertArgs(args);
		if (argsl.isEmpty()) {
			help();
			System.exit(0);
		}

		if (argsl.contains("-args")) {
			argsl.forEach((e) -> {
				if (argsl.indexOf("-args") < argsl.indexOf(e)) {
					ARGUMENTS.add(e);
				}
			});
			argsl.forEach((e) -> {
				ARRAARGUMENTS.add(ArraValue.of(e));
			});
		}

		int c;
		if ((c = argsl.indexOf("-in")) != -1) {
			Interpretter.getInstance().execfile(argsl.get(c + 1));
		}

		if (argsl.get(0).equals("-i")) {
			Interpretter.getInstance().repl();
		}

	}

	private static final List<String> convertArgs(final String[] args) {
		List<String> OutList = new ArrayList<String>();
		for (String str : args) {
			OutList.add(str);
		}
		return OutList;
	}

	public static final void help() {
		System.out.println(" ARRA INTERPRETTER v" + VERSION + " ______________________________________\n"
				+ "| -i  start REPL                                              |\n"
				+ "| -in <inputfile> speciffy input file                         |\n"
				+ "| -out <outputfile> speciffy output file                      |\n"
				+ "| -args <args> speciffy command line arguments                |\n"
				+ "| -c <extension> <args> compiles a file                       |\n"
				+ "|_____________________________________________________________|\n");
	}
}