/*
 * ImplyingN0neListener.java
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

import java.util.Random;

public class RandomN0neImplicationsListener extends ChannelMessagesPipeline.MessageMiddleware {
	public static final String GLOBAL_ENABLED = "n0ne_implications";
	public static final String GLOBAL_PROBABILITY = "n0ne_implications_prob";

	public RandomN0neImplicationsListener(Bundle globals) {
		super(globals);
		getGlobals().put(GLOBAL_PROBABILITY, 32);
	}

	@Override
	public boolean onMessage(MessageEvent event) throws Throwable {
		if (!getBooleanGlobal(GLOBAL_ENABLED, true)) {
			return false;
		}

		if (event.getUser().getNick().toLowerCase().contains("n0ne")) {
			Random r = new Random(System.currentTimeMillis());
			if (r.nextInt() % getIntGlobal(GLOBAL_PROBABILITY, 32) == 0) {
				event.getChannel().send().message("\u00033>implying n0ne is not a faggot");
			}
		}

		return false;
	}
}
