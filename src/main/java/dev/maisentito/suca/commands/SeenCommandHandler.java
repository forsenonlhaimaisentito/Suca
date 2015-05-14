/*
 * SeenCommandHandler.java
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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@BotCommand(name = "seen", minArgc = 1, help = "shows the last message sent by an user")
public class SeenCommandHandler extends BotCommands.CommandHandler {
	private SeenDataUpdater mUpdater;
	private Map<String, SeenData> mSeenUsers;

	public SeenCommandHandler(Bundle globals) {
		super(globals);
		mSeenUsers = Collections.synchronizedMap(new HashMap<String, SeenData>());
		mUpdater = new SeenDataUpdater();
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		synchronized (this) {
			if (mSeenUsers.containsKey(args[0].toLowerCase())) {
				SeenData seenData = mSeenUsers.get(args[0].toLowerCase());
				String formatted;

				if (DateUtils.isSameDay(seenData.time, new Date())) {
					formatted = DateFormatUtils.ISO_TIME_NO_T_TIME_ZONE_FORMAT.format(seenData.time);
				} else {
					formatted = DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT.format(seenData.time);
				}

				event.respond(String.format("seen: %s <%s> %s", formatted, seenData.nick, seenData.message));
			} else {
				event.respond("seen: user " + args[0] + " not seen");
			}
		}
	}

	public SeenDataUpdater getUpdater() {
		return mUpdater;
	}

	public static class SeenData {
		public String nick;
		public Date time;
		public String message;
	}

	public class SeenDataUpdater extends ListenerAdapter<PircBotX> {
		private SeenDataUpdater() {
		}

		@Override
		public void onMessage(MessageEvent<PircBotX> event) throws Exception {
			SeenData seenData;
			String key = event.getUser().getNick().toLowerCase();

			synchronized (this) {
				if (mSeenUsers.containsKey(key)) {
					seenData = mSeenUsers.get(key);
				} else {
					seenData = new SeenData();
					seenData.nick = event.getUser().getNick();
					mSeenUsers.put(key, seenData);
				}

				seenData.time = new Date(event.getTimestamp());
				seenData.message = event.getMessage();
			}
		}
	}
}
