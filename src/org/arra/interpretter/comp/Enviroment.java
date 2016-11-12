package org.arra.interpretter.comp;

import org.arra.interpretter.chunk.ArraObject;
import org.arra.lib.ILiblary;

public class Enviroment {

	private static ArraObject Env = new ArraObject();

	static {
		loadDefaultEnviroment();
	}

	public static void setEnviroment(final ArraObject obj) {
		Env = obj;
	}

	public static ArraObject getEnviroment() {
		return Env;
	}

	public static void clearEnviroment() {
		Env.clear();
	}

	public static void loadDefaultEnviroment() {
		Env = ILiblary.getLiblary("STD").dynamicGenerate();
	}
}
