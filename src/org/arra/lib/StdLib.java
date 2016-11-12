package org.arra.lib;

import org.arra.interpretter.chunk.ArraArgs;
import org.arra.interpretter.chunk.ArraObject;
import org.arra.interpretter.chunk.ArraValue;
import org.arra.interpretter.chunk.Special;
import org.arra.interpretter.comp.Enviroment;
import org.arra.interpretter.comp.Interpretter;
import org.arra.interpretter.comp.Stack;

public class StdLib extends ArraObject implements ILiblary {

	private static final Stack STACK = Stack.getInstance();
	private static final ArraObject ENVIROMENT = Enviroment.getEnviroment();

	protected StdLib() {
	}

	@Override
	public ArraObject dynamicGenerate() {
		map = fromClass(this.getClass()).asMap();
		map.remove("fromClass");
		map.remove("getLiblary");
		return this;
	}

	// TODO staticGenerate() method
	@Override
	@Deprecated
	public ArraObject staticGenerate() {
		/*
		 * map.put("ADD", new ArraFunc(ArraValue.of(((Action)(args) -> { return
		 * ADD(args); })))); map.put("SUB", new ArraFunc("SUB", new
		 * ArraChunk((args) -> { return SUB(args); }, ""))); map.put("MUL", new
		 * ArraFunc("MUL", new ArraChunk((args) -> { return MUL(args); }, "")));
		 * map.put("DIV", new ArraFunc("DIV", new ArraChunk((args) -> { return
		 * DIV(args); }, ""))); map.put("MOD", new ArraFunc("MOD", new
		 * ArraChunk((args) -> { return MOD(args); }, ""))); map.put("SET", new
		 * ArraFunc("SET", new ArraChunk((args) -> { return SET(args); }, "")));
		 * map.put("GET", new ArraFunc("GET", new ArraChunk((args) -> { return
		 * GET(args); }, ""))); map.put("PRT", new ArraFunc("PRT", new
		 * ArraChunk((args) -> { return PRT(args); }, ""))); map.put("APP", new
		 * ArraFunc("APP", new ArraChunk((args) -> { return APP(args); }, "")));
		 * map.put("EXT", new ArraFunc("EXT", new ArraChunk((args) -> { return
		 * EXT(args); }, ""))); map.put("NOP", new ArraFunc("NOP", new
		 * ArraChunk((args) -> { return NOP(args); }, ""))); map.put("USE", new
		 * ArraFunc("USE", new ArraChunk((args) -> { return USE(args); }, "")));
		 * map.put("OBJ", new ArraFunc("OBJ", new ArraChunk((args) -> { return
		 * OBJ(args); }, ""))); return this;
		 */
		throw new UnsupportedOperationException("In deverlopment, " + "use dynamicGenerate() instead");
	}

	public static ArraValue ADD(final ArraArgs args) {
		return args.get(0).add(args.get(1));
	}

	public static ArraValue SUB(final ArraArgs args) {
		return args.get(0).sub(args.get(1));
	}

	public static ArraValue MUL(final ArraArgs args) {
		return args.get(0).mul(args.get(1));
	}

	public static ArraValue DIV(final ArraArgs args) {
		return args.get(0).div(args.get(1));
	}

	public static ArraValue MOD(final ArraArgs args) {
		return args.get(0).mod(args.get(1));
	}

	public static ArraValue SET(final ArraArgs args) {
		STACK.add(args.get(0).toString(), args.get(1));
		return Special.SYS_NULL;
	}

	public static ArraValue GET(final ArraArgs args) {
		return args.get(0);
	}

	public static ArraValue PRT(final ArraArgs args) {
		if (args.size() != 0) {
			System.out.println(args.get(0).toString());
		} else {
			System.out.println();
		}
		return Special.SYS_NULL;
	}

	public static ArraValue APP(final ArraArgs args) {
		System.out.print(args.get(0).toString());
		return Special.SYS_NULL;
	}

	public static ArraValue EXT(final ArraArgs args) {
		final Double d = args.get(0).javaType().equals(Double.class) ? (double) args.get(0).toJava() : 0d;
		System.exit(d.intValue());
		return Special.SYS_NULL;
	}

	public static ArraValue NOP(final ArraArgs args) {
		return Special.SYS_NULL;
	}

	public static ArraValue RFL(final ArraArgs args) {
		Interpretter.getInstance().execfile(args.get(0).toString());
		return Special.SYS_NULL;
	}

	public static ArraValue EVL(final ArraArgs args) {
		Interpretter.getInstance().eval(args.get(0).toString());
		return Special.SYS_NULL;
	}

	public static ArraValue OBJ(final ArraArgs args) {
		final ArraObject obj = new ArraObject();
		args.toJavaList().forEach((e) -> {
			if (ENVIROMENT.get(e.toString()) != null)
				obj.put(e.toString(), ENVIROMENT.get(e.toString()));
			if (ENVIROMENT.getObject(e.toString()) != null)
				obj.putObject(e.toString(), ENVIROMENT.getObject(e.toString()));
		});
		return ArraValue.of(obj);
	}

	public static ArraValue USE(final ArraArgs args) {
		final ArraObject obj = ILiblary.getLiblary(args.get(0).toString()).dynamicGenerate();
		Enviroment.getEnviroment().putObject(args.get(0).toString(), obj);
		return Special.SYS_NULL;
	}
}
