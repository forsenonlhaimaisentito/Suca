/*
 * CloakListener.java
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

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ServerResponseEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CloakListener extends ListenerAdapter<PircBotX> {
	private int mCode;
	private List<String> mChannels;

	public CloakListener(String... channels) {
		this(396, channels);
	}

	public CloakListener(int code, String... channels) {
		mCode = code;
		if (channels.length == 1) {
			mChannels = Collections.singletonList(channels[0]);
		} else {
			mChannels = Arrays.asList(channels);
		}
	}

	@Override
	public void onServerResponse(ServerResponseEvent event) throws Exception {
		if (event.getCode() == mCode) {
			for (String channel : mChannels) {
				event.getBot().sendIRC().joinChannel(channel);
			}
		}
	}
}
