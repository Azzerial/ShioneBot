package net.azzerial.shione.commandsguild;

import java.util.Arrays;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GuildCommand extends Command {

	List<String> subcommands = Arrays.asList("prefix");
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length < 2 || args.length > 3) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		if (!subcommands.contains(args[1])) {
			return (UNKNOWN);
		}
		
		if (args[1].equalsIgnoreCase("prefix")) {
			if (args.length != 3) {
				sendCommandMessage(channel, author, self, "You need to provide the new prefix.", colorError);
				return ("<prefix> Missing string.");
			}
			sendCommandMessage(channel, author, self, "Caught: `" + args[2] + "`", colorError);
		}
		
		return (UNKNOWN);
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"guild",
			"server"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this commands to edit the bot's guild settings of this server.**");
	}

	@Override
	public String getHelpDescription() {
		return ("Edits the guild settings.");
	}

	@Override
	public String getName() {
		return ("Guild Command");
	}

	@Override
	public String getType() {
		return (GUILD_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"guild prefix <string>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "guild prefix //` - Sets Shione's commands prefix to `//`."
//			"`" + ShioneInfo.PREFIX + "guild settings` - Opens the bot guild's settings menu."
		));
	}

	@Override
	public boolean isAdminRequired() {
		return (true);
	}

	@Override
	public boolean isOpRequired() {
		return (false);
	}
	
}
