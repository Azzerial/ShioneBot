package net.azzerial.shione.commands.guild;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.listeners.GuildEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AdminCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2 && args.length != 3) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		JDA api = Shione.getAPI();
		
		if (args.length == 2
		&& args[1].equalsIgnoreCase("list")) {
			List<String> admins = GuildEvent.getServer(event.getGuild().getId()).getAdmins();
			Collections.sort(admins);
			String admins_string = "";
			
			for (String admin : admins) {
				admins_string += "`" + event.getGuild().getMember(api.getUserById(admin)).getEffectiveName() + "`, ";
			}
			admins_string = admins_string.substring(0, admins_string.length() - 2);
			admins_string += ".";
			sendCommandMessage(channel, author, self, "The following users are admins:\n" + admins_string, colorCommand);
			return ("<List> Displayed the current admins list.");
		}
		
		if (args.length == 3
		&& args[1].equalsIgnoreCase("add")) {
			User user = null;
			
			if (!event.getMessage().getMentionedUsers().isEmpty()) {
				user = event.getMessage().getMentionedUsers().get(0);
			}
			if (user == null) {
				sendCommandMessage(channel, author, self, "`" + args[2] + "` isn't a valid parameter.", colorError);
				return ("!<Add> User fed an invalid paramater. (" + args[2] + ")");
			}
			String username = event.getGuild().getMember(user).getEffectiveName();
			if (!GuildEvent.getServer(event.getGuild().getId()).addAdmin(user.getId())) {
				sendCommandMessage(channel, author, self, "`" + username + "` is already admin.", colorError);
				return ("!<Add> " + user.getName() + " is already admin.");
			}
			sendCommandMessage(channel, author, self, "`" + username + "` has been added to the admins list.", colorCommand);
			return ("<Add> " + user.getName() + " has been added to the admins list.");
		}
		
		if (args.length == 3
		&& args[1].equalsIgnoreCase("remove")) {
			User user = null;
			
			if (!event.getMessage().getMentionedUsers().isEmpty()) {
				user = event.getMessage().getMentionedUsers().get(0);
			}
			if (user == null) {
				sendCommandMessage(channel, author, self, "`" + args[2] + "` isn't a valid parameter.", colorError);
				return ("!<Remove> User fed an invalid parameter. (" + args[2] + ")");
			}
			String username = event.getGuild().getMember(user).getEffectiveName();
			if (!GuildEvent.getServer(event.getGuild().getId()).removeAdmin(user.getId())) {
				if (event.getGuild().getOwner().getUser().getId().equals(user.getId())) {
					sendCommandMessage(channel, author, self, "`" + username + "` is the guild's owner.\nYou can't remove him from the admins list.", colorError);
					return ("!<Remove> User tried to remove the guild's owner from the admins list.");
				} else {
					sendCommandMessage(channel, author, self, "`" + username + "` isn't admin.", colorError);
					return ("!<Remove> " + user.getName() + " isn't admin.");
				}
			}
			sendCommandMessage(channel, author, self, "`" + username + "` has been removed from the admins' list.", colorCommand);
			return ("<Remove> " + user.getName() + " has been removed from the admins' list.");
		}
		
		sendSubCommandMessage(channel, author, self);
		return ("!Unknown.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"admin",
			"admins"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this command to edit the bot's admins of this server.**");
	}

	@Override
	public String getHelpDescription() {
		return ("Edits the bot's admins list.");
	}

	@Override
	public String getName() {
		return ("Admin Command");
	}

	@Override
	public String getType() {
		return (GUILD_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"admin list",
			"admin add <user>",
			"admin remove <user>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "admin list` - View the list of the bot's admins of this server.",
			"`" + ShioneInfo.PREFIX + "admin add @Shione` - Adds Shione to the list of the bot's admins of this server.",
			"`" + ShioneInfo.PREFIX + "admin remove @Shione` - Removes Shione from the list of the bot's admins of this server."
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
