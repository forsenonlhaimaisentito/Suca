/*
 * Hangman.java
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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

class Hangman {
	private String mWord, mLowerWord;
	private int mTriesLeft;
	private List<Character> mPresentChars, mGuessedChars;


	public Hangman(String word) {
		this(word, (word.length() > 10) ? 8 : 6);
	}

	public Hangman(String word, int maxTries) {
		mWord = word;
		mLowerWord = word.toLowerCase();
		mTriesLeft = maxTries;
		mPresentChars = new ArrayList<>();
		mGuessedChars = new ArrayList<>();

		for (char c : mLowerWord.toCharArray()) {
			if (!mPresentChars.contains(c)) {
				mPresentChars.add(c);
			}
		}
	}

	public boolean isGameOver() {
		return (mGuessedChars.containsAll(mPresentChars) || mTriesLeft <= 0);
	}

	public boolean isGuessed(char letter) {
		return (mGuessedChars.contains(Character.toLowerCase(letter)));
	}

	public String getWord() {
		return mWord;
	}

	public int getTriesLeft() {
		return mTriesLeft;
	}

	public Character[] getGuessedChars() {
		return mGuessedChars.toArray(new Character[mGuessedChars.size()]);
	}

	public boolean guess(char letter) {
		letter = Character.toLowerCase(letter);

		if (!isGuessed(letter)) {
			mGuessedChars.add(letter);
			if (mPresentChars.contains(letter)) {
				return true;
			}

			mTriesLeft--;
			return false;
		}

		return false;
	}

	public boolean guess(String word) {
		if (mLowerWord.equals(word.toLowerCase()) && mTriesLeft > 0) {
			mGuessedChars.clear();
			mGuessedChars.addAll(mPresentChars);
			return true;
		}

		mTriesLeft--;
		return false;
	}

	public String getWordStatus() {
		if (!isGameOver()) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < mWord.length(); i++) {
				if (mGuessedChars.contains(mLowerWord.charAt(i))) {
					sb.append(mWord.charAt(i));
				} else {
					sb.append('_');
				}
			}

			return sb.toString();
		} else {
			return mWord;
		}
	}

	public String getStatusLine() {
		return String.format("%s   (len: %d)   (tries: %d)   [guessed: %s]",
				getWordStatus(),
				mWord.length(),
				getTriesLeft(),
				StringUtils.join(mGuessedChars, ", "));
	}
}
