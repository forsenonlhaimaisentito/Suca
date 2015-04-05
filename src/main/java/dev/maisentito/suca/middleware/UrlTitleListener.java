/*
 * UrlTitleListener.java
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

package dev.maisentito.suca.middleware;

import dev.maisentito.suca.Main;
import dev.maisentito.suca.listeners.ChannelMessagesPipeline;
import dev.maisentito.suca.util.Bundle;
import dev.maisentito.suca.util.Patterns;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.pircbotx.hooks.events.MessageEvent;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

public class UrlTitleListener extends ChannelMessagesPipeline.MessageMiddleware {
	public UrlTitleListener(Bundle globals) {
		super(globals);
	}

	@Override
	public boolean onMessage(MessageEvent event) throws Throwable {
		Matcher urlMatcher = Patterns.WEB_URL.matcher(event.getMessage());


		if (urlMatcher.find()) {
			try {
				// Big response DoS fix
				HttpURLConnection conn = (HttpURLConnection) new URL(urlMatcher.group()).openConnection();
				conn.setRequestMethod("HEAD");
				conn.addRequestProperty("User-Agent", getStringGlobal(Main.GLOBAL_USERAGENT, ""));
				conn.addRequestProperty("Referrer", "http://www.google.com");
				conn.connect();
				if ((conn.getResponseCode() >= 200) && (conn.getResponseCode() < 400)) {
					if (conn.getContentLengthLong() > 512000) {
						return false;
					}
				}

				Document doc = Jsoup.connect(urlMatcher.group())
						.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
						.referrer("http://www.google.com")
						.get();
				String title = doc.title();
				if (title.length() > 0) {
					event.getChannel().send().message(title);
				}
			} catch (MalformedURLException e) {
			} catch (UnsupportedMimeTypeException e) {
			}
		}

		return false;
	}
}
