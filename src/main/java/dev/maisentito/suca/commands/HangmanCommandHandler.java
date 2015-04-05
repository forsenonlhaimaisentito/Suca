/*
 * HangmanCommandHandler.java
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
import dev.maisentito.suca.util.Bundle;
import org.jsoup.Jsoup;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

public class HangmanCommandHandler extends BotCommands.CommandHandler {
	private String mWord;
	private Hangman mGame;
	private User mLastGuess;
	private HangmanGuessListener mListener;

	public HangmanCommandHandler(Bundle globals) {
		super(globals);
		mListener = new HangmanGuessListener(getGlobals());
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		if (mGame == null) {
			mWord = Jsoup.connect("http://www.wordgenerator.net/application/p.php?type=2&id=dictionary_words&spaceflag=false")
					.userAgent(getStringGlobal(Main.GLOBAL_USERAGENT, ""))
					.referrer("http://www.wordgenerator.net/random-word-generator.php")
					.get().body().html().split("<br>")[0].trim();
			if ((args.length == 1) && "impossible".equals(args[0])) {
				mGame = new Hangman(mWord, 1);
			} else {
				mGame = new Hangman(mWord);
			}
			event.getChannel().send().message(mGame.getStatusLine());
		} else {
			event.respond("hangman: already playing!");
		}
	}

	public HangmanGuessListener getListener() {
		return mListener;
	}

	public class HangmanGuessListener extends ChannelMessagesPipeline.MessageMiddleware {
		public HangmanGuessListener(Bundle globals) {
			super(globals);
		}

		@Override
		public synchronized boolean onMessage(MessageEvent event) throws Throwable {
			if (event.getMessage().trim().startsWith("!guess") && (mGame != null)) {
				if (event.getUser().equals(mLastGuess)) {
					event.respond("!guess: not your turn!");
					return true;
				}

				String[] args = BotCommands.parseCommandArgs(event.getMessage());
				if (args.length == 0) {
					event.respond("!guess: not enough arguments");
				} else {
					String guess = args[0];
					if (guess.trim().length() == 1) {
						if (!Character.isAlphabetic(guess.charAt(0))) {
							event.respond("!guess: not a letter");
						} else if (mGame.isGuessed(guess.charAt(0))) {
							event.respond("!guess: letter " + guess + " already guessed");
						} else if (mGame.guess(guess.charAt(0))) {
							event.respond("!guess: letter " + guess + " present");
							mLastGuess = event.getUser();
						} else {
							event.respond("!guess: letter " + guess + " NOT present");
							mLastGuess = event.getUser();
						}
					} else if (guess.trim().length() > 1) {
						if (mGame.guess(guess)) {
							event.respond("!guess: right word");
						} else {
							event.respond("!guess: wrong word");
						}
						mLastGuess = event.getUser();
					} else {
						event.respond("!guess: invalid argument");
					}
				}

				if (mGame.isGameOver()) {
					event.getChannel().send().message("Game Over!");
					event.getChannel().send().message(mGame.getStatusLine());
					mGame = null;
				} else {
					event.getChannel().send().message(mGame.getStatusLine());
				}

				return true;
			}

			return false;
		}
	}
}
