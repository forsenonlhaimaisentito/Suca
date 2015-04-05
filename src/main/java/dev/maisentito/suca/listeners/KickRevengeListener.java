/*
 * KickRevengeListener.java
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
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.OpEvent;

public class KickRevengeListener extends ListenerAdapter<PircBotX> {
	private User mKickRevenge;

	@Override
	public void onKick(KickEvent event) throws Exception {
		if (!event.getRecipient().equals(event.getBot().getUserBot()) ||
				event.getUser().equals(event.getBot().getUserBot())) {
			return;
		}

		mKickRevenge = event.getUser();
	}

	@Override
	public void onOp(OpEvent event) throws Exception {
		if (event.isOp() && event.getRecipient().equals(event.getBot().getUserBot())) {
			if (mKickRevenge != null) {
				event.getChannel().send().kick(mKickRevenge);
				mKickRevenge = null;
			}
		}
	}
}
