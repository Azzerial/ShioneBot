package net.azzerial.shione.commands.user;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.listeners.GuildEvent;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {
	
	public static TreeMap<String, Command> commands;
	
	public HelpCommand() {
		commands = new TreeMap<>();
	}
	
	public Command registerCommand(Command command) {
		commands.put(command.getAliases().get(0), command);
		return (command);
	}
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length > 2) {
			sendHelpMessage(event, channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		if (args.length == 1) {
			sendHelpMessage(event, channel, author, self);
			return ("Displayed the help menu correctly.");
		}
		
		Object[] keys = commands.keySet().toArray();
		for (int i = 0; i < keys.length; i += 1) {
			Command cmd = commands.get(keys[i]);
			
			if (cmd.isOpRequired() && !Shione.getPermissions().isOp(author)) {
				continue;
			}
			if (cmd.isAdminRequired() && !GuildEvent.getServer(event.getGuild().getId()).isAdmin(author)) {
				continue;
			}
			if (args[1].equalsIgnoreCase("help")) {
				sendCommandMessage(channel, author, self, "Hoy!", colorCommand);
				sendHelpMessage(event, channel, author, self);
				return ("Displayed the help menu correctly.");
			}
			if (cmd.getAliases().contains(args[1].toLowerCase())) {
				MessageUtil.sendEmbedMessage(channel,
					cmd.getName(), null, self.getAvatarUrl(),
					null,
					null, null,
					"__Description:__\n" + cmd.getDescription() + "\n\n" + cmd.getSubCommandsCodeBlock() + "\n" + cmd.getUsageExamplesBlock() + "\n" + cmd.getAliasesBlock(),
					null,
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorInformation);
				return ("Displayed the " + cmd.getName() + " subcommand menu correctly.");
			}
		}
		return ("!There are no commands with this alias.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"help"
		));
	}

	@Override
	public String getDescription() {
		return ("Shows the commands list.");
	}

	@Override
	public String getHelpDescription() {
		return ("Shows the commands list.");
	}

	@Override
	public String getName() {
		return ("Help Command");
	}
	
	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"help"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "help` - Shows Shione's commands help page.",
			"`" + ShioneInfo.PREFIX + "help register` - Shows the register command's help page."
		));
	}

	@Override
	public boolean isAdminRequired() {
		return (false);
	}

	@Override
	public boolean isOpRequired() {
		return (false);
	}
	
	private void sendHelpMessage(MessageReceivedEvent event, TextChannel channel, User author, User self) {
		Object[] keys = commands.keySet().toArray();
		String msgContent = "__Description:__\n"
			+ "**The following list contains all the commands Shione will reply to.**\n"
			+ "\n"
			+ "__Commands:__\n"
			+ "```md\n";
		
		for (int i = 0; i < keys.length; i += 1) {
			Command cmd = commands.get(keys[i]);
			
			if (cmd.isOpRequired() && !Shione.getPermissions().isOp(author)) {
				continue;
			}
			if (cmd.isAdminRequired() && !GuildEvent.getServer(event.getGuild().getId()).isAdmin(author)) {
				continue;
			}
			msgContent += "* " + cmd.getAliases().get(0) + " - " + cmd.getHelpDescription() + "\n";
		}
		msgContent += "```\n"
			+ "__SubCommands:__\n"
			+ "```md\n"
			+ "* help <commands>"
			+ "```\n"
			+ getUsageExamplesBlock() + "\n"
			+ getAliasesBlock();
		sendCommandMessage(channel, author, self, msgContent, colorInformation);
	}

}
