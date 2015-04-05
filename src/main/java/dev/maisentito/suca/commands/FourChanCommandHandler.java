/*
 * FourChanCommandHandler.java
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
import org.jsoup.Jsoup;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FourChanCommandHandler extends BotCommands.CommandHandler {
	public FourChanCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		if (args.length < 3) {
			event.respond("!4chan: not enough arguments");
			return;
		}

		if (!FourChan.BOARDS.contains(args[1])) {
			event.respond("!4chan: invalid board: " + args[1]);
			return;
		}

		switch (args[0]) {
			case "search":
				searchCommand(event, args);
				break;
			default:
				event.respond("!4chan: command not found: " + args[0]);
				return;
		}
	}

	@Override
	public String getHelp(MessageEvent event, String[] args) {
		return "usage: 4chan search BOARD REGEX";
	}

	private void searchCommand(MessageEvent event, String[] args) throws IOException {
		String seq = StringUtils.join(args, ' ', 2, args.length);
		Pattern pattern = null;
		try {
			pattern = Pattern.compile(seq, Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException e) {

		}

		for (FourChan.Catalog.Page page : FourChan.getCatalog(args[1]).pages) {
			for (FourChan.Catalog.ThreadPreview thread : page.threads) {
				if ((thread == null) || (thread.com == null)) {
					continue;
				}

				thread.com = Jsoup.parseBodyFragment(thread.com).text();

				if (pattern != null) {
					if (pattern.matcher(thread.com).find()) {
						event.respond("match found: " + threadUrl(args[1], thread.no));
						return;
					}
				} else {
					if (thread.com.contains(seq)) {
						event.respond("match found: " + threadUrl(args[1], thread.no));
						return;
					}
				}
			}
		}

		event.respond("no matches found");
	}

	public static String threadUrl(String board, String id) {
		return String.format("https://boards.4chan.org/%s/thread/%s", board, id);
	}
}
