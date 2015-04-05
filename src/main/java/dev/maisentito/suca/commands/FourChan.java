/*
 * FourChan.java
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

package dev.maisentito.suca.commands;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FourChan {
	public static final List<String> BOARDS;

	public static Catalog getCatalog(String board) throws IOException {
		URLConnection conn = new URL("https://a.4cdn.org/" + board + "/catalog.json").openConnection();
		Gson gson = new Gson();
		Catalog catalog = new Catalog();
		catalog.pages = gson.fromJson(new InputStreamReader(conn.getInputStream()), Catalog.Page[].class);
		return catalog;
	}

	public static class Catalog {
		public Page[] pages;

		public static class Page {
			int page;
			public ThreadPreview[] threads;
		}

		public static class ThreadPreview {
			// Only command-relevant attributes
			public String no;
			public String com;
		}
	}


	static {
		BOARDS = Collections.unmodifiableList(
				Arrays.asList(
						"a", "b", "c", "d", "e", "f", "g", "gif", "h", "hr", "k", "m", "o", "p", "r", "s", "t", "u",
						"v", "vg", "vr", "w", "wg", "i", "ic", "r9k", "s4s", "cm", "hm", "lgbt", "y", "3", "adv", "an",
						"asp", "biz", "cgl", "ck", "co", "diy", "fa", "fit", "gd", "hc", "int", "jp", "lit", "mlp",
						"mu", "n", "out", "po", "pol", "sci", "soc", "sp", "tg", "toy", "trv", "tv", "vp", "wsg", "x"));
	}
}
