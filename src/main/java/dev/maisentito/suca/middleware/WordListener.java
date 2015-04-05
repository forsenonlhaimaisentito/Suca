/*
 * WordListener.java
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

package dev.maisentito.suca.middleware;

import dev.maisentito.suca.Main;
import dev.maisentito.suca.listeners.ChannelMessagesPipeline;
import dev.maisentito.suca.util.Bundle;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.Map;

public class WordListener extends ChannelMessagesPipeline.MessageMiddleware {
	private final Map<String, String> sResponses = new HashMap<>();

	public WordListener(Bundle globals) {
		super(globals);

		sResponses.put("god",
				"God? Did you mean " + getStringGlobal(Main.GLOBAL_OWNER, "nothing") + "?");
	}

	@Override
	public boolean onMessage(MessageEvent event) throws Throwable {
		for (String sub : sResponses.keySet()) {
			if (event.getMessage().toLowerCase().contains(sub)) {
				event.getChannel().send().message(sResponses.get(sub));
				return true;
			}
		}

		return false;
	}
}
