package net.azzerial.shione.commands.bot;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Permissions;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class EvalCommand extends Command {

	private ScriptEngine engine;
	
	public EvalCommand() {
		engine = new ScriptEngineManager().getEngineByName("nashorn");
		
		try {
			engine.eval("var imports = new JavaImporter(" +
				"java.io," +
				"java.lang," +
				"java.util," +
				"java.awt," +
				"Packages.net.dv8tion.jda.core," +
				"Packages.net.dv8tion.jda.core.entities," +
				"Packages.net.dv8tion.jda.core.entities.impls," +
				"Packages.net.dv8tion.jda.core.managers," +
				"Packages.net.dv8tion.jda.core.managers.impls," +
				"Packages.net.dv8tion.jda.core.utils," +
				"Packages.net.azzerial.shione.commands," +
				"Packages.net.azzerial.shione.core," +
				"Packages.net.azzerial.shione.deprecated," +
				"Packages.net.azzerial.shione.menus," +
				"Packages.net.azzerial.shione.entities," +
				"Packages.net.azzerial.shione.listeners," +
				"Packages.net.azzerial.shione.sc2d," +
				"Packages.net.azzerial.shione.sc2d.builders," +
				"Packages.net.azzerial.shione.sc2d.entities," +
				"Packages.net.azzerial.shione.sc2d.entities.impls," +
				"Packages.net.azzerial.shione.utils" +
				");"
			);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length == 1) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		String output = "Executed without error.";
		
		if (event.getMessage().getContentDisplay().contains("exit")) {
			sendCommandMessage(channel, author, self, "You can't use the `exit` statement.", colorError);
			return ("!User tried to use the exit statement.");
		}
		
		try {
			engine.put("event", event);
			engine.put("args", args);
			engine.put("message", event.getMessage());
			engine.put("channel", event.getChannel());
			engine.put("author", event.getAuthor());
			engine.put("self", event.getJDA().getSelfUser());
			engine.put("jda", event.getJDA());
			engine.put("sc2d", Shione.getSC2DAPI());
			engine.put("permissions", Permissions.getPermissions());
			if (event.isFromType(ChannelType.TEXT)) {
				engine.put("guild", event.getGuild());
				engine.put("member", event.getMember());
			}
			
			Object out = engine.eval(
				"(function() {" +
					"with (imports) {" +
						event.getMessage().getContentDisplay().substring(args[0].length() + 1) +
					"}" +
				"})();"
			);
			
			sendCommandMessage(channel, author, self, (out == null ? "Executed without error. :thumbsup:" : out.toString()), colorCommand);
			if (out != null) {
				output = out.toString();
			}
		} catch (Exception e) {
			sendCommandMessage(channel, author, self, "Eval returned:\n```java\n" + e.getMessage() + "\n```", colorCommand);
		}
		return ("\nUser typed:"  +event.getMessage().getContentDisplay().substring(args[0].length()) + "\nUser got: " + output);
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"eval",
			"shione",
			"sc2d",
			"bot",
			"java",
			"cli",
			"do"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this commands to execute given Java code.** "
			+ "Use it wisely as it can destroy the bot. All use of this commands is stored in a log file (to prevent abuse or accidents).\n"
			+ "The following variables can be accessed:\n"
			+ "`event`, `args`, `message`, `channel`, `author`, `self`, `jda`, `sc2d`, `permissions`.\n"
			+ "And if typed in a guild: `guild`, `member`.");
	}
	
	@Override
	public String getHelpDescription() {
		return ("Executes Java code.");
	}

	@Override
	public String getName() {
		return ("Eval Command");
	}

	@Override
	public String getType() {
		return (BOT_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"eval <java_code>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "eval return (\"\" + 2 + 2);` - Writes 4 in the channel.",
			"`" + ShioneInfo.PREFIX + "eval return (\"\" + self.getName());` - Writes Shione in the channel."
		));
	}

	@Override
	public boolean isAdminRequired() {
		return (false);
	}

	@Override
	public boolean isOpRequired() {
		return (true);
	}

}
