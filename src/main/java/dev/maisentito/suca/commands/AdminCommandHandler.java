/*
 * AdminCommandHandler.java
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
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;

@BotCommand(name = "admin", minArgc = 1)
public class AdminCommandHandler extends BotCommands.CommandHandler {
	public AdminCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		if (event.getUser().isVerified() &&
				event.getUser().getNick().equals(getStringGlobal(Main.GLOBAL_OWNER, ""))) {
			if (args.length < 2) {
				if (args.length == 1) {
					if (getGlobals().has(args[0], Object.class)) {
						event.respond(String.format("!admin: %s = %s", args[0], getGlobals().get(args[0]).toString()));
					} else {
						event.respond(String.format("!admin: %s = null", args[0]));
					}
					return;
				} else {
					event.respond("!admin: not enough arguments");
					return;
				}
			} else if (args[1].length() < 3) {
				event.respond("!admin: invalid value");
				return;
			}

			String key = args[0];
			String full = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ' ');
			Object value;

			switch (args[1].charAt(0)) {
				case 'c':
					value = args[1].charAt(2);
					break;
				case 'b':
					value = Byte.parseByte(args[1].substring(2));
					break;
				case 's':
					value = Short.parseShort(args[1].substring(2));
					break;
				case 'i':
					value = Integer.parseInt(args[1].substring(2));
					break;
				case 'l':
					value = Long.parseLong(args[1].substring(2));
					break;
				case 'f':
					value = Float.parseFloat(args[1].substring(2));
					break;
				case 'd':
					value = Double.parseDouble(args[1].substring(2));
					break;
				case 'z':
					value = Boolean.parseBoolean(args[1].substring(2));
					break;
				case 'a':
					value = full.substring(2);
					break;
				default:
					event.respond("!admin: invalid type");
					return;
			}

			getGlobals().put(key, value);
			event.respond("success");

		} else {
			event.respond("nope");
		}
	}
}
