package org.arra.interpretter.chunk;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.arra.error.ErrorFactory;
import org.arra.interpretter.comp.Enviroment;
import org.arra.interpretter.comp.Stack;

public class ArraValue implements Serializable {

	private static final long serialVersionUID = 11111111112L;
	private ArraArgs ArgsBuf = new ArraArgs();
	private String toStr = null;
	private Object Obj;

	private ArraValue() {
	}

	public static ArraValue of(final Object Obj) {
		ArraValue val = new ArraValue();
		if (Obj instanceof ArraValue)
			val.Obj = ((ArraValue) Obj).toJava();
		else if (Obj == null)
			val.Obj = Special.SYS_NULL;
		else
			val.Obj = Obj;
		return val;
	}

	public static ArraValue ofFunction(final String cmd) {
		final ArraValue out = new ArraValue();
		final String[] arg = cmd.split(" ");

		int i = 0;
		ArraObject env = Enviroment.getEnviroment();
		while (out.Obj == null) {
			try {
				arg[i].length();
			} catch (IndexOutOfBoundsException e) {
				new ErrorFactory().syntaxError("CANNOT RESOLVE '" + cmd + "': OUT OF BOUNDS");
			}
			if (env.map.containsKey(arg[i])) {
				out.Obj = env.get(arg[i]);
			} else if (env.objmap.containsKey(arg[i])) {
				env = env.getObject(arg[i]);
				i++;
			} else {
				new ErrorFactory().syntaxError("CANNOT RESOLVE '" + cmd + "'");
			}

		}

		out.toStr = cmd;
		out.ArgsBuf = initargs(arg, i);
		return out;
	}

	public ArraValue call() {
		return call(ArgsBuf);
	}

	public ArraValue call(final ArraArgs args) {
		try {
			final Class<?> clazz = javaType();
			if (clazz.equals(ArraFunc.class))
				return ((ArraFunc) Obj).call(new ArraArgs[] { args });
			if (clazz.equals(Method.class))
				return (ArraValue) ((Method) Obj).invoke(null, args);
		} catch (Exception e) {
			new ErrorFactory().internalError("CANNOT CALL METHOD '" + Obj + ": " + e);
		}
		return Special.SYS_NULL;
	}

	public void setArgument(ArraArgs argsbuf) {
		this.ArgsBuf = argsbuf;
	}

	public ArraArgs getArguments() {
		return ArgsBuf;
	}

	public Object toJava() {
		return Obj;
	}

	public Class<?> javaType() {
		if (Obj instanceof Double || Obj instanceof Integer) {
			return Double.class;
		}

		try {
			Double.parseDouble((String) Obj);
			return Double.class;
		} catch (Exception e) {
		}

		if (Obj instanceof String) {
			return String.class;
		}

		if (Obj instanceof ArraFunc) {
			return ArraFunc.class;
		}

		if (Obj instanceof Method) {
			return Method.class;
		}

		return Object.class;
	}

	public ArraValue resolve() {
		return Stack.getInstance().get(this.toString());
	}

	public ArraValue add(final ArraValue v) {
		if (this.javaType().equals(v.javaType()) == false)
			return v;
		if (v.javaType().equals(Double.class))
			return ArraValue.of(toDouble(Obj) + toDouble(v.toJava()));
		else
			return v;
	}

	public ArraValue sub(final ArraValue v) {
		if (this.javaType().equals(v.javaType()) == false)
			return v;
		if (v.javaType().equals(Double.class))
			return ArraValue.of(toDouble(Obj) - toDouble(v.toJava()));
		else
			return v;
	}

	public ArraValue div(final ArraValue v) {
		if (this.javaType().equals(v.javaType()) == false)
			return v;
		if (v.javaType().equals(Double.class))
			return ArraValue.of(toDouble(Obj) / toDouble(v.toJava()));
		else
			return v;
	}

	public ArraValue mul(final ArraValue v) {
		if (this.javaType().equals(v.javaType()) == false)
			return v;
		if (v.javaType().equals(Double.class))
			return ArraValue.of(toDouble(Obj) * toDouble(v.toJava()));
		else
			return v;
	}

	public ArraValue mod(final ArraValue v) {
		if (this.javaType().equals(v.javaType()) == false)
			return v;
		if (v.javaType().equals(Double.class))
			return ArraValue.of(toDouble(Obj) % toDouble(v.toJava()));
		else
			return v;
	}

	public double toDouble() {
		return toDouble(Obj);
	}

	private double toDouble(final Object obj) {
		switch (obj.getClass().getSimpleName()) {
		case "Double":
			return ((Double) obj).doubleValue();
		case "Float":
			return ((Float) obj).doubleValue();
		case "Integer":
			return ((Integer) obj).doubleValue();
		case "Short":
			return ((Short) obj).doubleValue();
		case "Long":
			return ((Long) obj).doubleValue();
		case "String":
			return Double.parseDouble((String) obj);
		default:
			new ErrorFactory().syntaxError("CANNOT CONVERT TO DOUBLE: " + Obj);
			return 0.0;
		}
	}

	@Override
	public boolean equals(final Object o) {
		try {
			return ((ArraValue) o).Obj.equals(Obj);
		} catch (Exception e) {
		}
		try {
			return Obj.equals(o);
		} catch (Exception e) {
		}
		try {
			if (((ArraValue) o).Obj == Obj)
				return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public String toString() {
		if (toStr != null)
			return toStr;
		if (Obj != null)
			return Obj.toString();
		else
			return null;
	}

	private static ArraArgs initargs(final String[] arr, final int itr) {
		final Stack stack = Stack.getInstance();
		final ArraArgs ArgsBuf = new ArraArgs();
		for (int i = 1 + itr; i < arr.length; i++) {
			ArgsBuf.add(stack.get(arr[i]));
		}
		String buffer = "";
		int mark = -1;
		for (int i = 0; i < ArgsBuf.size(); i++) {
			ArraValue a = ArgsBuf.get(i);
			if (a == null) {
				continue;
			}
			if (a.javaType().equals(String.class)) {
				if (mark != -1) {
					buffer += " " + a.toString();
					if (a.toString().endsWith("\"")) {
						buffer = buffer.substring(1, buffer.length() - 1);
						ArgsBuf.set(mark, ArraValue.of(buffer));
						mark = -1;
					}
				} else if (a.toString().startsWith("\"")) {
					mark = i;
					buffer = a.toString();
				}
			}
		}
		return ArgsBuf;
	}
}
