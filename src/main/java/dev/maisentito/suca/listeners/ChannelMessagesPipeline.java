/*
 * ChannelMessagesPipeline.java
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

import dev.maisentito.suca.util.GlobalsUser;
import dev.maisentito.suca.util.Bundle;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

public class ChannelMessagesPipeline extends ListenerAdapter<PircBotX> {
	private final List<MessageMiddleware> mMessageMiddleware = new ArrayList<>();

	/**
	 * Abstract class for channel message listeners that allows a listener to consume the event.
	 */
	public static abstract class MessageMiddleware extends GlobalsUser {
		public MessageMiddleware(Bundle globals) {
			super(globals);
		}

		/**
		 * This method is called whenever a channel message is received.
		 *
		 * @param event the original {@link org.pircbotx.hooks.events.MessageEvent}
		 * @return <code>true</code> if the event has been consumed, <code>false</code> otherwise
		 * @throws Throwable
		 */
		public abstract boolean onMessage(MessageEvent event) throws Throwable;
	}

	public void addMessageMiddleware(MessageMiddleware middleware) {
		if (middleware != null) {
			mMessageMiddleware.add(middleware);
		}
	}

	public void removeMessageMiddleware(MessageMiddleware middleware) {
		if (middleware != null) {
			mMessageMiddleware.remove(middleware);
		}
	}

	@Override
	public void onMessage(MessageEvent event) throws Exception {
		for (MessageMiddleware middleware : mMessageMiddleware) {
			try {
				if (middleware.onMessage(event)) {
					break;
				}
			} catch (Throwable t) {
				event.getChannel().send().message(
						String.format("%s: %s",
								t.getClass().getName(),
								t.getMessage()));
				t.printStackTrace();
			}
		}
	}
}
