package org.arra.interpretter.chunk;

import java.util.ArrayList;
import java.util.List;

import org.arra.interpretter.comp.Stack;

public class ArraArgs {
	private final List<ArraValue> Val = new ArrayList<ArraValue>();

	public ArraArgs(final ArraValue... val) {
		for (ArraValue v : val) {
			Val.add(v);
		}
	}

	public ArraArgs(List<ArraValue> val) {
		Val.addAll(val);
	}

	public List<ArraValue> toJavaList() {
		return Val;
	}

	public ArraValue getRaw(final int i) {
		return Val.get(i);
	}

	public ArraValue get(final int i) {
		return Stack.getInstance().get(Val.get(i).toString());
	}

	public void add(final ArraValue v) {
		Val.add(v);
	}

	public void set(final int i, final ArraValue v) {
		Val.set(i, v);
	}

	public int size() {
		return Val.size();
	}
}
