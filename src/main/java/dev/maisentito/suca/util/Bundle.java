/*
 * Bundle.java
 *
 * Copyright (c) 2015  forsenonlhaimaisentito
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.maisentito.suca.util;

import java.util.HashMap;
import java.util.Map;

public class Bundle extends HashMap<String, Object> {

	public Bundle() {
		super();
	}

	public Bundle(int initialCapacity) {
		super(initialCapacity);
	}

	public Bundle(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public Bundle(Map<? extends String, ?> m) {
		super(m);
	}

	/**
	 * Checks if an entry associated with the key <code>key</code> of type <code>clazz</code> exists.
	 *
	 * @param key   The key of the entry to search for
	 * @param clazz The type of the entry to search for
	 * @return <code>true</code> if the entry exists as an instance of <code>clazz</code> and is not <code>null</code>,
	 * <code>false</code> otherwise.
	 */
	public boolean has(String key, Class clazz) {
		return clazz.isInstance(get(key));
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Character}.
	 *
	 * @param key      the key whose associated {@link java.lang.Character} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Character}
	 * @return the {@link java.lang.Character} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Character}
	 */
	public char getChar(String key, char defValue) {
		return (has(key, Character.class)) ? (Character) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Byte}.
	 *
	 * @param key      the key whose associated {@link java.lang.Byte} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Byte}
	 * @return the {@link java.lang.Byte} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Byte}
	 */
	public byte getByte(String key, byte defValue) {
		return (has(key, Byte.class)) ? (Byte) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Short}.
	 *
	 * @param key      the key whose associated {@link java.lang.Short} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Short}
	 * @return the {@link java.lang.Short} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Short}
	 */
	public short getShort(String key, short defValue) {
		return (has(key, Short.class)) ? (Short) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Integer}.
	 *
	 * @param key      the key whose associated {@link java.lang.Integer} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Integer}
	 * @return the {@link java.lang.Integer} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Integer}
	 */
	public int getInt(String key, int defValue) {
		return (has(key, Integer.class)) ? (Integer) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Long}.
	 *
	 * @param key      the key whose associated {@link java.lang.Long} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Long}
	 * @return the {@link java.lang.Long} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Long}
	 */
	public long getLong(String key, long defValue) {
		return (has(key, Long.class)) ? (Long) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Float}.
	 *
	 * @param key      the key whose associated {@link java.lang.Float} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Float}
	 * @return the {@link java.lang.Float} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Float}
	 */
	public float getFloat(String key, float defValue) {
		return (has(key, Float.class)) ? (Float) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Double}.
	 *
	 * @param key      the key whose associated {@link java.lang.Double} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Double}
	 * @return the {@link java.lang.Double} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Double}
	 */
	public double getDouble(String key, double defValue) {
		return (has(key, Double.class)) ? (Double) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.Boolean}.
	 *
	 * @param key      the key whose associated {@link java.lang.Boolean} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.Boolean}
	 * @return the {@link java.lang.Boolean} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.Boolean}
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return (has(key, Boolean.class)) ? (Boolean) get(key) : defValue;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping
	 * for the key or if the mapping for the key is not an
	 * instance of {@link java.lang.String}.
	 *
	 * @param key      the key whose associated {@link java.lang.String} value is to be returned
	 * @param defValue the value to return if this Bundle contains no mapping for the key of type {@link java.lang.String}
	 * @return the {@link java.lang.String} value to which the specified key is mapped,
	 * or <code>defValue</code> if this Bundle contains no mapping for the key or if the
	 * mapping for the key is not an instance of {@link java.lang.String}
	 */
	public String getString(String key, String defValue) {
		return (has(key, String.class)) ? (String) get(key) : defValue;
	}
}
