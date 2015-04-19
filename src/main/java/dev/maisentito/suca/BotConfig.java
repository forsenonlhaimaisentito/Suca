/*
 * BotConfig.java
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

package dev.maisentito.suca;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;

public class BotConfig {
	public String nick = "Suca";
	public String realname = "Suca";
	public String username = "legit";
	public String password;
	public String server = "irc.freenode.net";
	public int port = 6697;
	public boolean ssl;
	public int wait_cloak;
	public String[] channels = { "#legit" };
	public String owner = "DiZero";
	public boolean verifyOwner = true;

	public void toJson(Writer out) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		gson.toJson(this, out);
		Main.closeQuietly(out);
	}

	public static BotConfig fromJson(Reader in) {
		Gson gson = new Gson();
		BotConfig config = gson.fromJson(in, BotConfig.class);
		Main.closeQuietly(in);
		return config;
	}
}
