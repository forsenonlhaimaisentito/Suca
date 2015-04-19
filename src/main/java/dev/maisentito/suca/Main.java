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
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {
	public static final String GLOBAL_OWNER = "owner_nick";
	public static final String GLOBAL_VERSION = "version";
	public static final String GLOBAL_USERAGENT = "user_agent";
	public static final String HOME = (System.getenv("HOME") == null) ? "./" : System.getenv("HOME");
	public static final File CONFIG = new File(HOME, ".sucarc");

	public static BotConfig config;

	public static void main(String[] args) throws Exception {
		if (CONFIG.exists()) {
			config = BotConfig.fromJson(new FileReader(CONFIG));
		} else {
			config = new BotConfig();
		}

		Bundle globals = new Bundle();
		globals.put(GLOBAL_OWNER, config.owner);
		globals.put(GLOBAL_VERSION, "1.0");
		globals.put(GLOBAL_USERAGENT, "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

		HangmanCommandHandler hangman = new HangmanCommandHandler(globals);
		SeenCommandHandler seen = new SeenCommandHandler(globals);

		BotCommands commands = new BotCommands(globals);
		commands.addCommandHandler(new AdminCommandHandler(globals));
		commands.addCommandHandler(new QuoteCommandHandler(globals));
		commands.addCommandHandler(new UrbanCommandHandler(globals));
		commands.addCommandHandler(new FactCommandHandler(globals));
		commands.addCommandHandler(hangman);
		commands.addCommandHandler(new ItenCommandHandler(globals));
		commands.addCommandHandler(new EnitCommandHandler(globals));
		commands.addCommandHandler(new EvalCommandHandler(globals));
		commands.addCommandHandler(new FourChanCommandHandler(globals));
		commands.addCommandHandler(new TimerCommandHandler(globals));
		commands.addCommandHandler(new FizzBuzzCommandHandler(globals));
		commands.addCommandHandler(seen);

		// commands.addCommandHandler("define", new DefineCommandHandler(globals));
		SimpleCommands.addAll(commands, globals);

		ChannelMessagesPipeline messagesPipeline = new ChannelMessagesPipeline();
		messagesPipeline.addMessageMiddleware(commands);
		messagesPipeline.addMessageMiddleware(hangman.getListener());
		messagesPipeline.addMessageMiddleware(new UrlTitleListener(globals));
		messagesPipeline.addMessageMiddleware(new ImplyingN0neListener(globals));
		messagesPipeline.addMessageMiddleware(new RandomN0neImplicationsListener(globals));
		messagesPipeline.addMessageMiddleware(new WordListener(globals));

		if (config.password == null) {
			System.out.print("Password: ");
			char[] pass = Password.readPassword(System.in);
			config.password = String.valueOf(pass);
		}

		@SuppressWarnings("unchecked")
		Configuration.Builder builder = new Configuration.Builder()
				.setName(config.nick)
				.setRealName(config.realname)
				.setLogin(config.username)
				.setNickservPassword(config.password)
						// .addCapHandler(new SASLCapHandler(NICK, String.valueOf(pass)))
				.setVersion("Suca " + globals.getString(GLOBAL_VERSION, "unknown"))
				.setEncoding(Charset.forName("UTF-8"))
				.setServerHostname(config.server)
				.setServerPort(config.port)
				.addListener(messagesPipeline)
				.addListener(new JoinGreeterListener(globals))
				.addListener(new KickRejoinListener())
				.addListener(new KickRevengeListener())
				.addListener(seen.getUpdater())
				.setMessageDelay(1500);

		if (config.ssl) {
			builder.setSocketFactory(SSLSocketFactory.getDefault());
		}

		if (config.wait_cloak > 0) {
			builder.addListener(new CloakListener(config.wait_cloak, config.channels));
		} else {
			for (String channel : config.channels) {
				builder.addAutoJoinChannel(channel);
			}
		}

		@SuppressWarnings("unchecked") PircBotX bot = new PircBotX(builder.buildConfiguration());
		bot.startBot();
	}

	public static void closeQuietly(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {
		}
	}
}
