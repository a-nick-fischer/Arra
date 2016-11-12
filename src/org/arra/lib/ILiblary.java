package org.arra.lib;

import org.arra.error.ErrorFactory;
import org.arra.interpretter.chunk.ArraObject;

public interface ILiblary {
	public static ILiblary getLiblary(final String s) {
		switch (s) {
		case "STD":
			return new StdLib();
		case "MATH":
			return new MathLib();
		default:
			new ErrorFactory().nullError("NO LIBLARY FOUND UNDER THE '" + s + "'");
			return null;
		}
	}

	public ArraObject dynamicGenerate();

	public ArraObject staticGenerate();
}
