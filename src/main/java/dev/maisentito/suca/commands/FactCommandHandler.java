/*
 * FactCommandHandler.java
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
import org.pircbotx.hooks.events.MessageEvent;

public class FactCommandHandler extends BotCommands.CommandHandler {
	public FactCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		event.getChannel().send().message(
				Jsoup.connect("http://stupidstuff.org/quoter/factoid.htm")
						.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
						.referrer("http://www.google.com/")
						.get().body()
						.select(".bkline > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1)").text().trim());
	}

	@Override
	public String getHelp(MessageEvent event, String[] args) {
		return "fetches a random fact from the internet";
	}
}
