/*
 * Main.java
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

package dev.maisentito.suca;

import dev.maisentito.suca.commands.*;
import dev.maisentito.suca.listeners.*;
import dev.maisentito.suca.middleware.ImplyingN0neListener;
import dev.maisentito.suca.middleware.RandomN0neImplicationsListener;
import dev.maisentito.suca.middleware.UrlTitleListener;
import dev.maisentito.suca.middleware.WordListener;
import dev.maisentito.suca.util.Bundle;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import sun.security.util.Password;

import javax.net.ssl.SSLSocketFactory;
import java.nio.charset.Charset;
import java.util.Arrays;

public class Main {
	public static final String NICK = "Suca";
	public static final String NAME = "Suca";
	public static final String USER = "legit";

	public static final String GLOBAL_OWNER = "owner_nick";
	public static final String GLOBAL_VERSION = "version";
	public static final String GLOBAL_USERAGENT = "user_agent";

	public static void main(String[] args) throws Exception {
		Bundle globals = new Bundle();
		globals.put(GLOBAL_OWNER, "DiZero");
		globals.put(GLOBAL_VERSION, "1.0");
		globals.put(GLOBAL_USERAGENT, "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

		HangmanCommandHandler hangman = new HangmanCommandHandler(globals);

		BotCommands commands = new BotCommands(globals);
		commands.addCommandHandler("admin", new AdminCommandHandler(globals));
		commands.addCommandHandler("quote", new QuoteCommandHandler(globals));
		commands.addCommandHandler("urban", new UrbanCommandHandler(globals));
		commands.addCommandHandler("fact", new FactCommandHandler(globals));
		commands.addCommandHandler("hangman", hangman);
		commands.addCommandHandler("iten", new ItenCommandHandler(globals));
		commands.addCommandHandler("enit", new EnitCommandHandler(globals));
		commands.addCommandHandler("eval", new EvalCommandHandler(globals));
		commands.addCommandHandler("4chan", new FourChanCommandHandler(globals));
		// commands.addCommandHandler("define", new DefineCommandHandler(globals));
		SimpleCommands.addAll(commands, globals);

		ChannelMessagesPipeline messagesPipeline = new ChannelMessagesPipeline();
		messagesPipeline.addMessageMiddleware(commands);
		messagesPipeline.addMessageMiddleware(hangman.getListener());
		messagesPipeline.addMessageMiddleware(new UrlTitleListener(globals));
		messagesPipeline.addMessageMiddleware(new ImplyingN0neListener(globals));
		messagesPipeline.addMessageMiddleware(new RandomN0neImplicationsListener(globals));
		messagesPipeline.addMessageMiddleware(new WordListener(globals));

		System.out.print("Password: ");
		char[] pass = Password.readPassword(System.in);

		@SuppressWarnings("unchecked")
		Configuration configuration = new Configuration.Builder()
				.setName(NICK)
				.setRealName(NAME)
				.setLogin(USER)
				.setNickservPassword(String.valueOf(pass))
						// .addCapHandler(new SASLCapHandler(NICK, String.valueOf(pass)))
				.setVersion("Suca " + globals.getString(GLOBAL_VERSION, "unknown"))
				.setEncoding(Charset.forName("UTF-8"))
				.setServerHostname("irc.freenode.net")
				.setServerPort(6697)
				.setSocketFactory(SSLSocketFactory.getDefault())
				.addListener(messagesPipeline)
				.addListener(new CloakListener(Arrays.asList("#legit")))
				.addListener(new JoinGreeterListener(globals))
				.addListener(new KickRejoinListener())
				.addListener(new KickRevengeListener())
				.setMessageDelay(1500)
				.buildConfiguration();

		PircBotX bot = new PircBotX(configuration);
		bot.startBot();
	}
}
