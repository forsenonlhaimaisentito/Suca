/*
 * SimpleCommands.java
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
import org.jsoup.Jsoup;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Random;

public class SimpleCommands {
	public static void addAll(BotCommands commands, Bundle globals) {
		commands.addCommandHandler(new CoinCommandHandler(globals));
		commands.addCommandHandler(new DiceCommandHandler(globals));
		commands.addCommandHandler(new EgoCommandHandler(globals));
	}

	@BotCommand(name = "coin", help = "flips a coin")
	public static class CoinCommandHandler extends BotCommands.CommandHandler {
		public CoinCommandHandler(Bundle globals) {
			super(globals);
		}

		@Override
		public void handleCommand(MessageEvent event, String[] args) throws Throwable {
			event.respond((new Random().nextBoolean()) ? "Head" : "Tail");
		}
	}

	@BotCommand(name = "dice", help = "extracts a random number from 1 to 6")
	public static class DiceCommandHandler extends BotCommands.CommandHandler {
		public DiceCommandHandler(Bundle globals) {
			super(globals);
		}

		@Override
		public void handleCommand(MessageEvent event, String[] args) throws Throwable {
			event.respond(Integer.toString(new Random().nextInt(6) + 1));
		}
	}

	@BotCommand(name = "ego", help = "just try it")
	public static class EgoCommandHandler extends BotCommands.CommandHandler {
		public EgoCommandHandler(Bundle globals) {
			super(globals);
		}

		@Override
		public void handleCommand(MessageEvent event, String[] args) throws Throwable {
			if (!event.getUser().getNick().toLowerCase().equals("n0ne")) {
				String msg = Jsoup.connect("http://stupidstuff.org/main/complimentor.htm")
						.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
						.referrer("http://www.google.com/")
						.get()
						.select("body > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(7) " +
								"> tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > table:nth-child(4) > tbody:nth-child(1) " +
								"> tr:nth-child(1) > td:nth-child(1) > font:nth-child(1)")
						.text().trim();

				event.respond(msg);
			} else {
				event.respond("You are a faggot.");
			}
		}
	}
}
