package org.arra.interpretter.comp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.arra.interpretter.chunk.ArraValue;
import org.arra.interpretter.chunk.Special;
import org.arra.main.Main;

public class Stack {

	private final Map<String, ArraValue> map = new HashMap<String, ArraValue>();
	private final List<ArraValue> stack = new LinkedList<ArraValue>();
	// private final ErrorFactory errfac = new ErrorFactory();
	private static final Stack instance = new Stack();

	private Stack() {
		loadEnviroment();
	}

	public void add(final ArraValue v) {
		if (v != null) {
			if (!v.equals(Special.SYS_NULL))
				stack.add(v);
		}
	}

	public void add(final String s, final ArraValue v) {
		if (v != null) {
			if (!v.equals(Special.SYS_NULL))
				map.put(s, v);
		}
	}

	public ArraValue get(final String key) {
		update();
		if (key == null) {
			return Special.SYS_NULL;
		} else if (key.startsWith("%")) {
			return relativeGet(key);
		} else if (key.startsWith("[") && key.endsWith("]")) {
			if (map.containsKey(key.substring(1, key.length() - 1))) {
				return map.get(key.substring(1, key.length() - 1));
			} else {
				return stackGet(key);
			}
		}
		return ArraValue.of(key);
	}

	public int stackSize() {
		return stack.size();
	}

	public int mapSize() {
		return map.size();
	}

	public Map<String, ArraValue> getVariableMap() {
		return new HashMap<>(map);
	}

	public List<ArraValue> getVariableList() {
		return new LinkedList<>(stack);
	}

	public void clearEnviroment() {
		map.clear();
		stack.clear();
	}

	public void loadEnviroment() {
		map.put("SYS_OUT", Special.SYS_OUT);
		map.put("SYS_IN", Special.SYS_IN);
		map.put("SYS_ERR", Special.SYS_ERR);
		map.put("SYS_ARGS",
				ArraValue.of(Main.ARGUMENTS.toString().substring(1, Main.ARGUMENTS.toString().length() - 1)));
		for (int i = 0; i < Main.ARGUMENTS.size(); i++) {
			map.put("SYS_ARGS" + i, Main.ARRAARGUMENTS.get(i));
		}
		map.put("STACK_SIZE", ArraValue.of(0));
		map.put("MAP_SIZE", ArraValue.of(0));
		map.put("FUNC_ARGS", ArraValue.of(""));

	}

	public static Stack getInstance() {
		return instance;
	}

	private void update() {
		map.put("STACK_SIZE", ArraValue.of(stack.size()));
		map.put("MAP_SIZE", ArraValue.of(map.size()));
	}

	private ArraValue relativeGet(final String key) {
		int i = 1;
		while (true) {
			if (stack.get(stack.size() - i - Integer.valueOf(key.substring(1))).equals(Special.SYS_NULL))
				i++;
			else
				return stack.get(stack.size() - i - Integer.valueOf(key.substring(1)));
		}
	}

	private ArraValue stackGet(final String key) {
		try {
			int i = 0;
			if (stack.size() > (i = Integer.parseInt(key.substring(1, key.length() - 1))))
				return stack.get(i);
			else
				return Special.SYS_NULL;
			// errfac.nullError("\"" + key + "\" IS NULL");
		} catch (NumberFormatException e) {
			return Special.SYS_NULL;
			// errfac.nullError("\"" + key + "\" IS NULL");
		}
	}
}
