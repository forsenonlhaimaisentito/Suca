/*
 * QuoteCommandHandler.java
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

import dev.maisentito.suca.Main;
import dev.maisentito.suca.util.Bundle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pircbotx.hooks.events.MessageEvent;

public class QuoteCommandHandler extends BotCommands.CommandHandler {
	public QuoteCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		Document doc = Jsoup.connect("http://www.quotationspage.com/random.php3")
				.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
				.referrer("http://www.google.com/")
				.get();
		String quote = doc.body().select("dt.quote:nth-child(1) > a:nth-child(1)").text().trim().replace("\n", "");
		String author = doc.body().select("dd.author:nth-child(2) > b:nth-child(2) > a:nth-child(1)").text().trim().replace("\n", "");
		event.getChannel().send().message(String.format("\u201C%s\u201D \u2014 %s", quote, author));
	}

	@Override
	public String getHelp(MessageEvent event, String[] args) {
		return "fetches a random quote from the internet";
	}
}
