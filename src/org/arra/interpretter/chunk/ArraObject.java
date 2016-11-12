package org.arra.interpretter.chunk;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.arra.error.ErrorFactory;

public class ArraObject {

	protected Map<String, ArraFunc> map = new HashMap<>();
	protected Map<String, ArraObject> objmap = new HashMap<>();

	public static ArraObject fromClass(final Class<?> clazz) {
		try {
			final ArraObject obj = new ArraObject();
			for (final Method m : clazz.getMethods()) {
				final int mod = m.getModifiers();
				if (Modifier.isStatic(mod) && Modifier.isPublic(mod)) {
					final ArraValue[] vals = new ArraValue[] { ArraValue.of(m) };
					final ArraArgs[] args = new ArraArgs[] { new ArraArgs() };
					obj.map.put(m.getName(), new ArraFunc(vals, args));
				}
			}
			return obj;
		} catch (final Exception e) {
			new ErrorFactory().internalError("COULD NOT CONVERT CLASS: " + (clazz == null ? "null" : clazz.getName()));
		}
		return null;
	}

	public void put(final String key, final ArraFunc func) {
		map.put(key, func);
	}

	public ArraFunc get(final String key) {
		return map.get(key);
	}

	public void putObject(final String key, final ArraObject obj) {
		objmap.put(key, obj);
	}

	public ArraObject getObject(final String key) {
		return objmap.get(key);
	}

	public int size() {
		return map.size();
	}

	public Map<String, ArraFunc> asMap() {
		return map;
	}

	public void clear() {
		map.clear();
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
