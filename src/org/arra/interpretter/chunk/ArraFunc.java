package org.arra.interpretter.chunk;

import org.arra.error.ErrorFactory;
import org.arra.interpretter.comp.Stack;

public class ArraFunc {

	private final ArraValue[] list;
	private final ArraArgs[] args;
	private final Stack STACK = Stack.getInstance();

	public ArraFunc(final ArraValue[] list, final ArraArgs[] args) {
		this.list = list;
		this.args = args;
		if (list.length != args.length) {
			new ErrorFactory().internalError("INVALID ARGUMENT LENGTH");
		}
	}

	public ArraFunc(final ArraValue list, final ArraArgs args) {
		this(new ArraValue[] { list }, new ArraArgs[] { args });
	}

	public void call() {
		call(args);
	}

	public ArraValue call(ArraArgs[] args) {
		for (int i = 0; i < list.length; i++) {
			if (list[i].toString().startsWith("RET ") && list[i].toString().split(" ").length == 2)
				return ArraValue.of(list[i].toString().split(" ")[1]).resolve();
			else
				STACK.add(list[i].call(args[i]));
		}
		return Special.SYS_NULL;
	}

	public ArraValue[] toArray() {
		return list;
	}

	public Action toAction() {
		return (args) -> {
			for (int i = 0; i < list.length; i++) {
				if (list[i].toString().startsWith("RET ") && list[i].toString().split(" ").length == 2)
					return ArraValue.of(list[i].toString().split(" ")[1]).resolve();
				else
					STACK.add(list[i].call(this.args[i]));
			}
			return Special.SYS_NULL;
		};
	}
}
