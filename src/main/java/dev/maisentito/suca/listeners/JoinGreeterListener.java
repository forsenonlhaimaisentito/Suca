/*
 * JoinGreeterListener.java
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

package dev.maisentito.suca.listeners;

import dev.maisentito.suca.Main;
import dev.maisentito.suca.util.Bundle;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;

public class JoinGreeterListener extends ListenerAdapter<PircBotX> {
	private Bundle mGlobals;

	public JoinGreeterListener(Bundle globals) {
		mGlobals = globals;
	}

	@Override
	public void onJoin(JoinEvent event) throws Exception {
		if (event.getUser().getNick().equals(mGlobals.getString(Main.GLOBAL_OWNER, ""))) {
			event.respond("Heil, master " + mGlobals.getString(Main.GLOBAL_OWNER, "") + "!");
		} else if (!event.getBot().getNick().equals(event.getUser().getNick())) {
			event.respond("welcome");
		} else {
			event.getChannel().send().message("hi everybody!");
		}
	}
}
