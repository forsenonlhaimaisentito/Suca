/*
 * BotCommands.java
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

import dev.maisentito.suca.Main;
import dev.maisentito.suca.listeners.ChannelMessagesPipeline;
import dev.maisentito.suca.util.BotCommand;
import dev.maisentito.suca.util.Bundle;
import dev.maisentito.suca.util.GlobalsUser;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.*;

public class BotCommands extends ChannelMessagesPipeline.MessageMiddleware {
	public static final String COMMAND_PREFIX = "!";

	/**
	 * Abstract base class for command listeners.
	 */
	public static abstract class CommandHandler extends GlobalsUser {
		public CommandHandler(Bundle globals) {
			super(globals);
		}

		/**
		 * This method is called when the command associated with this listener is issued.
		 *
		 * @param event the original {@link org.pircbotx.hooks.events.MessageEvent}
		 * @param args  the text following the command name, split by <code>"\\s+"</code>
		 * @throws Throwable
		 */
		public abstract void handleCommand(MessageEvent event, String[] args) throws Throwable;

		/**
		 * This method is called when the help message for this command is requested, it should return a String without
		 * newlines or <code>null</code> if not help is available for this command.
		 * The default implementation returns <code>null</code>.
		 *
		 * @param event the original {@link org.pircbotx.hooks.events.MessageEvent}
		 * @param args  the text following the !help command, split by <code>"\\s+"</code>
		 * @return the one-line help message or <code>null</code>
		 */
		public String getHelp(MessageEvent event, String[] args) {
			BotCommand attrs;
			if ((attrs = getClass().getAnnotation(BotCommand.class)) != null) {
				return attrs.help();
			}

			return null;
		}
	}

	private final List<String> mAvailableCommands = new ArrayList<>();
	private final Map<String, CommandHandler> mCommandHandlers = new HashMap<>();

	public BotCommands(Bundle globals) {
		super(globals);
		// Built-in commands
		addCommandHandler("quit", new CommandHandler(getGlobals()) {
			@Override
			public void handleCommand(MessageEvent event, String[] args) throws Throwable {
				if (event.getUser().isVerified() &&
						event.getUser().getNick().equals(getStringGlobal(Main.GLOBAL_OWNER, ""))) {
					event.getBot().stopBotReconnect();
					event.getBot().sendIRC().quitServer(
							"I'm quitting, master " + getStringGlobal(Main.GLOBAL_OWNER, ""));
				} else {
					event.respond("nope");
				}
			}
		});

		addCommandHandler("help", new CommandHandler(getGlobals()) {
			@Override
			public void handleCommand(MessageEvent event, String[] args) throws Throwable {
				if (args.length == 0) {
					event.respond("Available commands: " + StringUtils.join(mAvailableCommands, ", "));
					return;
				}

				String command = args[0].replace(COMMAND_PREFIX, "");
				String help;

				if (!mCommandHandlers.containsKey(command)) {
					event.respond("command not found: " + args[0]);
				} else if ((help = mCommandHandlers.get(command).getHelp(event, args)) != null) {
					event.respond("help for command " + args[0] + ": " + help);
				} else {
					event.respond("no help available for command " + args[0]);
				}
			}
		});
	}

	public void addCommandHandler(CommandHandler handler) {
		BotCommand attrs;
		if ((handler != null) && ((attrs = handler.getClass().getAnnotation(BotCommand.class)) != null)) {
			addCommandHandler(attrs.name(), handler);
		}
	}

	public void addCommandHandler(String command, CommandHandler handler) {
		if ((command != null) && (handler != null)) {
			mCommandHandlers.put(command, handler);
			mAvailableCommands.add(COMMAND_PREFIX + command);
		}
	}

	public void removeCommandHandler(String command) {
		if (command != null) {
			mCommandHandlers.remove(command);
			mAvailableCommands.remove(COMMAND_PREFIX + command);
		}
	}

	public static String[] parseCommandArgs(String message) {
		String[] parts = message.trim().substring(1).split("\\s+");
		if (parts.length > 1) {
			return Arrays.copyOfRange(parts, 1, parts.length);
		} else {
			return new String[0];
		}
	}

	@Override
	public boolean onMessage(MessageEvent event) throws Throwable {
		if (event.getMessage().startsWith(COMMAND_PREFIX) && !event.getUser().equals(event.getBot().getUserBot())) {
			String[] parts = event.getMessage().substring(1).split("\\s+");
			if ((parts.length > 0) && (mCommandHandlers.containsKey(parts[0]))) {
				CommandHandler handler = mCommandHandlers.get(parts[0]);

				BotCommand attrs = handler.getClass().getAnnotation(BotCommand.class);
				if ((attrs != null) && (parts.length <= attrs.minArgc())) {
					event.respond(COMMAND_PREFIX + parts[0] + ": not enough arguments");
					return true;
				}

				if (parts.length > 1) {
					handler.handleCommand(event, Arrays.copyOfRange(parts, 1, parts.length));
				} else {
					handler.handleCommand(event, new String[]{});
				}
				return true;
			}
		}
		return false;
	}
}
