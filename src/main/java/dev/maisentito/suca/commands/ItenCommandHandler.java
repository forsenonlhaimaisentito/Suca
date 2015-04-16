/*
 * ItenCommandHandler.java
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
import dev.maisentito.suca.util.BotCommand;
import dev.maisentito.suca.util.Bundle;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pircbotx.hooks.events.MessageEvent;

@BotCommand(name = "iten", minArgc = 1, help = "translates a word from italian to english")
public class ItenCommandHandler extends BotCommands.CommandHandler {
	public ItenCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		Document doc = Jsoup.connect("http://www.wordreference.com/iten/" + StringUtils.join(args, ' '))
				.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
				.referrer("http://www.google.com/")
				.get();
		Elements row = doc.body().select("table.WRD:nth-child(2) > tbody:nth-child(1) > tr:nth-child(2)");
		row.select(".tooltip").remove();
		String def = row.text().trim().replace("\n", "");
		event.respond(def);
	}
}
