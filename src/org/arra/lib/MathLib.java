package org.arra.lib;

import java.util.ArrayList;

import org.arra.error.ErrorFactory;
import org.arra.interpretter.chunk.ArraArgs;
import org.arra.interpretter.chunk.ArraObject;
import org.arra.interpretter.chunk.ArraValue;

public class MathLib extends ArraObject implements ILiblary {

	@Override
	public ArraObject dynamicGenerate() {
		map = fromClass(this.getClass()).asMap();
		map.remove("fromClass");
		map.remove("getLiblary");
		return this;
	}

	@Override
	public ArraObject staticGenerate() {
		throw new UnsupportedOperationException("Not supported yet");
	}

	public static ArraValue EVL(final ArraArgs args) {
		final String[] inputSequence = ((String) args.get(0).toJava()).split(" ");
		final ArrayList<String> ops = new ArrayList<>();
		final ArrayList<Double> vals = new ArrayList<>();

		for (final String s : inputSequence) {
			if (s.equals("("))
				;
			else if (s.equals("+"))
				ops.add(s);
			else if (s.equals("-"))
				ops.add(s);
			else if (s.equals("*"))
				ops.add(s);
			else if (s.equals("/"))
				ops.add(s);
			else if (s.equals("sqrt"))
				ops.add(s);
			else if (s.equals(")")) {
				final String op = ops.remove(ops.size() - 1);
				double v = vals.remove(vals.size() - 1);
				if (op.equals("+"))
					v = vals.remove(vals.size() - 1) + v;
				else if (op.equals("-"))
					v = vals.remove(vals.size() - 1) - v;
				else if (op.equals("*"))
					v = vals.remove(vals.size() - 1) * v;
				else if (op.equals("/"))
					v = vals.remove(vals.size() - 1) / v;
				else if (op.equals("sqrt"))
					v = Math.sqrt(v);
				vals.add(v);
			} else
				vals.add(Double.parseDouble(s));
		}
		return ArraValue.of(vals.get(vals.size() - 1));
	}

	public static ArraValue SIN(final ArraArgs args) {
		return ArraValue.of(Math.sin(args.get(0).toDouble()));
	}

	public static ArraValue COS(final ArraArgs args) {
		return ArraValue.of(Math.cos(args.get(0).toDouble()));
	}

	public static ArraValue TAN(final ArraArgs args) {
		return ArraValue.of(Math.tan(args.get(0).toDouble()));
	}

	public static ArraValue PI(final ArraArgs args) {
		return ArraValue.of(Math.PI);
	}

	public static ArraValue E(final ArraArgs args) {
		return ArraValue.of(Math.E);
	}

	public static ArraValue ABS(final ArraArgs args) {
		return ArraValue.of(Math.abs(args.get(0).toDouble()));
	}

	public static ArraValue MAX(final ArraArgs args) {
		return ArraValue.of(Math.max(args.get(0).toDouble(), args.get(1).toDouble()));
	}

	public static ArraValue MIN(final ArraArgs args) {
		return ArraValue.of(Math.min(args.get(0).toDouble(), args.get(1).toDouble()));
	}

	public static ArraValue LOG(final ArraArgs args) {
		return ArraValue.of(Math.log(args.get(0).toDouble()));
	}

	public static ArraValue ROUND(final ArraArgs args) {
		try {
			return ArraValue.of(Math.round(args.get(0).toDouble()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArraValue RANDOM(final ArraArgs args) {
		switch (args.size()) {
		case 2:
			final long min = Math.round(args.get(0).toDouble());
			final long max = Math.round(args.get(1).toDouble());
			if (min > max) {
				new ErrorFactory().mathError("MINIMUM IS BIGGER THAN MAXIMUM");
			}
			final long res = (long) (Math.random() * (max - min)) + min;
			return ArraValue.of(res);

		case 0:
		default:
			return ArraValue.of(Math.random());
		}
	}
}
