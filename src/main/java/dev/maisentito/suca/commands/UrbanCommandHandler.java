/*
 * UrbanCommandHandler.java
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

import dev.maisentito.liburban.Definition;
import dev.maisentito.liburban.UrbanDictionary;
import dev.maisentito.suca.util.Bundle;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.List;

public class UrbanCommandHandler extends BotCommands.CommandHandler {
	public UrbanCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		if (args.length > 0) {
			List<Definition> defs = UrbanDictionary.define(StringUtils.join(args, ' ')).getList();
			if (!defs.isEmpty()) {
				Definition def = defs.get(0);
				event.respond(String.format("%s: %s", def.getWord(), def.getDefinition()));
			} else {
				event.respond("no results");
			}
		} else {
			event.respond("not enough arguments");
		}
	}

	@Override
	public String getHelp(MessageEvent event, String[] args) {
		return "retrieves the first definition of the given term from Urban Dictionary";
	}
}
