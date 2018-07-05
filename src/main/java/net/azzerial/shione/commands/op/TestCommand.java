package net.azzerial.shione.commands.op;

import java.util.Arrays;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.entities.Server;
import net.azzerial.shione.listeners.GuildEvent;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		Server server = GuildEvent.getServer(event.getGuild().getId());
		
		server.saveServerData();
		
		server.loadSettings();
		sendCommandMessage(channel, author, self, "Prefix: " + server.getPrefix(), colorCommand);
		String text = "";
		for (TextChannel ch : server.getAllowedChannels()) {
			text += ch.getName() + ", ";
		}
		text.substring(0, text.length() - 2);
		sendCommandMessage(channel, author, self, "Channels: " + text, colorCommand);
		
		return ("Tested.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"test"
		));
	}

	@Override
	public String getDescription() {
		return ("**Testing stuff.**\n"
			+ "Sometimes, magical things happen.");
	}

	@Override
	public String getHelpDescription() {
		return ("Testing stuff.");
	}

	@Override
	public String getName() {
		return ("Test Command");
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			""
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "test` - Unleashes unicorns."
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
