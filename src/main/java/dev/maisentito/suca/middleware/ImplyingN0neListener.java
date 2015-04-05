/*
 * ImplyingListener.java
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

import dev.maisentito.suca.listeners.ChannelMessagesPipeline;
import dev.maisentito.suca.util.Bundle;
import org.pircbotx.hooks.events.MessageEvent;

public class ImplyingN0neListener extends ChannelMessagesPipeline.MessageMiddleware {
	public ImplyingN0neListener(Bundle globals) {
		super(globals);
	}

	@Override
	public boolean onMessage(MessageEvent event) throws Throwable {
		if (event.getMessage().toLowerCase().contains("mply")) {
			if (event.getUser().getNick().equals("n0ne")) {
				event.getChannel().send().message("\u00033>implying n0ne is not a faggot");
				return true;
			}
		}
		return false;
	}
}
