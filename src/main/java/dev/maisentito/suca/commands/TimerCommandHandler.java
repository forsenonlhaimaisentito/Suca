/*
 * TimerCommandHandler.java
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

import dev.maisentito.suca.util.Bundle;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.*;

public class TimerCommandHandler extends BotCommands.CommandHandler {
	private Timer mTimer;
	private List<UserTimer> mTasks;

	public TimerCommandHandler(Bundle globals) {
		super(globals);
		mTimer = new Timer(TimerCommandHandler.class.getSimpleName());
		mTasks = Collections.synchronizedList(new LinkedList<UserTimer>());
	}

	@Override
	public synchronized void handleCommand(final MessageEvent event, String[] args) throws Throwable {
		if (args.length < 2) {
			event.respond("timer: missing argument(s)");
			return;
		}

		long ms;
		try {
			ms = Long.parseLong(args[0]) * 1000;
		} catch (NumberFormatException e) {
			event.respond("timer: invalid delay");
			return;
		}

		for (UserTimer task : mTasks) {
			if (task.getOwner().equals(event.getUser())) {
				task.cancel();
				mTasks.remove(task);
				event.respond("previous timer cancelled");
			}
		}

		UserTimer task = new UserTimer(event.getUser(), event.getChannel(), StringUtils.join(args, ' ', 1, args.length));
		mTimer.schedule(task, ms);
		mTasks.add(task);
		event.respond("timer started");
	}

	@Override
	public String getHelp(MessageEvent event, String[] args) {
		return "sets a timer, usage (DELAY is in seconds): !timer DELAY MESSAGE";
	}

	class UserTimer extends TimerTask {
		private User mOwner;
		private Channel mChannel;
		private String mMessage;

		public UserTimer(User owner, Channel channel, String message) {
			super();
			mOwner = owner;
			mChannel = channel;
			mMessage = message;
		}

		public User getOwner() {
			return mOwner;
		}

		@Override
		public void run() {
			TimerCommandHandler.this.mTasks.remove(this);
			mChannel.send().message(mOwner, "timer: " + mMessage);
		}
	}
}
