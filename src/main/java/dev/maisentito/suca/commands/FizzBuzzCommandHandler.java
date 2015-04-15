/*
 * FizzBuzzCommandHandler.java
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

import dev.maisentito.suca.util.Bundle;
import org.pircbotx.hooks.events.MessageEvent;

public class FizzBuzzCommandHandler extends BotCommands.CommandHandler {
	public FizzBuzzCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		if (args.length == 0) {
			event.respond("!fizzbuzz: not enough arguments");
		} else {
			long n;
			try {
				n = Long.parseLong(args[0]);
			} catch (NumberFormatException e) {
				event.respond("!fizzbuzz: invalid argument");
				return;
			}

			event.respond("!fizzbuzz: " + args[0] + ": " + fizzBuzz(n));
		}
	}

	public static String fizzBuzz(long n) {
		String s = "";
		s += (n % 3 == 0) ? "Fizz" : "";
		s += (n % 5 == 0) ? "Buzz" : "";
		return (s.isEmpty()) ? Long.toString(n) : s;
	}
}
