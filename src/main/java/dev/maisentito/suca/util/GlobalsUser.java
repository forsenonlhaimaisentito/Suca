/*
 * GlobalsUser.java
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

public abstract class GlobalsUser {
	private Bundle mGlobals;

	/**
	 * @param globals The {@link Bundle} containing the global variables for this instance
	 * @throws java.lang.IllegalArgumentException if <code>globals</code> is null
	 */
	public GlobalsUser(Bundle globals) {
		if (globals == null) {
			throw new IllegalArgumentException("Globals Bundle must be non-null");
		}

		mGlobals = globals;
	}

	/**
	 * @return The {@link Bundle} containing the global variables for this instance
	 */
	public Bundle getGlobals() {
		return mGlobals;
	}

	/**
	 * Equivalent to <code>getGlobals().getChar(key, defValue)</code>
	 *
	 * @see Bundle#getChar(String, char)
	 */
	public char getCharGlobal(String key, char defValue) {
		return getGlobals().getChar(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getByte(key, defValue)</code>
	 *
	 * @see Bundle#getByte(String, byte)
	 */
	public byte getByteGlobal(String key, byte defValue) {
		return getGlobals().getByte(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getShort(key, defValue)</code>
	 *
	 * @see Bundle#getShort(String, short)
	 */
	public short getShortGlobal(String key, short defValue) {
		return getGlobals().getShort(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getInt(key, defValue)</code>
	 *
	 * @see Bundle#getInt(String, int)
	 */
	public int getIntGlobal(String key, int defValue) {
		return getGlobals().getInt(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getLong(key, defValue)</code>
	 *
	 * @see Bundle#getLong(String, long)
	 */
	public long getLongGlobal(String key, long defValue) {
		return getGlobals().getLong(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getFloat(key, defValue)</code>
	 *
	 * @see Bundle#getFloat(String, float)
	 */
	public float getFloatGlobal(String key, float defValue) {
		return getGlobals().getFloat(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getDouble(key, defValue)</code>
	 *
	 * @see Bundle#getDouble(String, double)
	 */
	public double getDoubleGlobal(String key, double defValue) {
		return getGlobals().getDouble(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getBoolean(key, defValue)</code>
	 *
	 * @see Bundle#getBoolean(String, boolean)
	 */
	public boolean getBooleanGlobal(String key, boolean defValue) {
		return getGlobals().getBoolean(key, defValue);
	}

	/**
	 * Equivalent to <code>getGlobals().getString(key, defValue)</code>
	 *
	 * @see Bundle#getString(String, String)
	 */
	public String getStringGlobal(String key, String defValue) {
		return getGlobals().getString(key, defValue);
	}
}
