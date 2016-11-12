package org.arra.interpretter.chunk;

import org.arra.main.Main;

public class Special {
	public static final ArraValue SYS_NULL = ArraValue.of(null);
	public static final ArraValue SYS_OUT = ArraValue.of(System.out);
	public static final ArraValue SYS_ERR = ArraValue.of(System.err);
	public static final ArraValue SYS_IN = ArraValue.of(System.in);
	public static final ArraArgs SYS_ARGS = new ArraArgs(Main.ARRAARGUMENTS);
	public static final ArraArgs FUNC_ARGS = new ArraArgs();
}
