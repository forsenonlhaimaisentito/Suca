/*
 * ToCommandHandler.java
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

import dev.maisentito.suca.util.BotCommand;
import dev.maisentito.suca.util.Bundle;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NickChangeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@BotCommand(name = "to", minArgc = 2, help = "seen USER MESSAGE, shows MESSAGE when USER joins")
public class ToCommandHandler extends BotCommands.CommandHandler {
	private Map<String, Map<String, String>> messages;
	private ToJoinListener listener;

	public ToCommandHandler(Bundle globals) {
		super(globals);
		messages = new HashMap<>();
		listener = new ToJoinListener();
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		String userHost = formatUserHost(event.getUser());

		if (!messages.containsKey(args[0].toLowerCase())) {
			messages.put(args[0].toLowerCase(), new HashMap<String, String>());
		}

		messages.get(args[0].toLowerCase()).put(userHost, StringUtils.join(Arrays.copyOfRange(args, 1, args.length), ' '));

		event.respond("message added to queue");
	}

	public ToJoinListener getListener() {
		return new ToJoinListener();
	}

	public class ToJoinListener extends ListenerAdapter<PircBotX> {
		@Override
		public void onJoin(JoinEvent<PircBotX> event) throws Exception {
			handleUser(event.getUser().getNick().toLowerCase(), event);
		}

		@Override
		public void onNickChange(NickChangeEvent<PircBotX> event) throws Exception {
			handleUser(event.getNewNick().toLowerCase(), event);
		}

		public void handleUser(String nick, Event event) {
			if (messages.containsKey(nick)) {
				for (String sender : messages.get(nick).keySet()) {
					event.respond(String.format("message from %s: %s", sender, messages.get(nick).get(sender)));
					messages.get(nick).remove(sender);
				}
			}
		}
	}


	public static String formatUserHost(User user) {
		return String.format("%s [%s@%s]", user.getNick(), user.getLogin(), user.getHostmask());
	}
}
