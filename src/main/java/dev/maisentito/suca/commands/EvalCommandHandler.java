/*
 * EvalCommandHandler.java
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

import dev.maisentito.suca.util.BotCommand;
import dev.maisentito.suca.util.Bundle;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

@BotCommand(name = "eval", minArgc = 1, help = "Evaluates an arithmetic expression, constants e and pi available")
public class EvalCommandHandler extends BotCommands.CommandHandler {
	public EvalCommandHandler(Bundle globals) {
		super(globals);
	}

	@Override
	public void handleCommand(MessageEvent event, String[] args) throws Throwable {
		double result = new ExpressionBuilder(StringUtils.join(args, ' '))
				.variables("pi", "e")
				.build()
				.setVariable("pi", Math.PI)
				.setVariable("e", Math.E)
				.evaluate();

		event.respond("eval: result: " + result);
	}
}
