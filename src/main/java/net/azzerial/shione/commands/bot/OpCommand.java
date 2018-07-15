package net.azzerial.shione.commands.bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.database.Permissions;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class OpCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2 && args.length != 3) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		JDA api = Shione.getAPI();
		
		if (args.length == 2
		&& args[1].equalsIgnoreCase("list")) {
			ArrayList<String> ops = Permissions.getOps();
			Collections.sort(ops);
			String ops_string = "";
			
			for (String op : ops) {
				ops_string += "`" + event.getGuild().getMember(api.getUserById(op)).getEffectiveName() + "`, ";
			}
			ops_string = ops_string.substring(0, ops_string.length() - 2);
			ops_string += ".";
			sendCommandMessage(channel, author, self, "The following users are ops:\n" + ops_string, colorCommand);
			return ("<List> Displayed the current Ops list.");
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
			if (!Permissions.addOp(user.getId())) {
				sendCommandMessage(channel, author, self, "`" + username + "` is already op.", colorError);
				return ("!<Add> " + user.getName() + " is already op.");
			}
			sendCommandMessage(channel, author, self, "`" + username + "` has been added to the Ops list.", colorCommand);
			return ("<Add> " + user.getName() + " has been added to the Ops list.");
		}
		
		if (args.length == 3
		&& args[1].equalsIgnoreCase("remove")) {
			User user = null;
			
			if (!event.getMessage().getMentionedUsers().isEmpty()) {
				user = event.getMessage().getMentionedUsers().get(0);
			}
			if (user == null) {
				sendCommandMessage(channel, author, self, "`" + args[2] + "` isn't a valid parameter.", colorError);
				return ("!<Remove> User fed an invalid paramater. (" + args[2] + ")");
			}
			String username = event.getGuild().getMember(user).getEffectiveName();
			if (!Permissions.removeOp(user.getId())) {
				if (user.getId().equals(ShioneInfo.BOT_AUTHOR_ID)) {
					sendCommandMessage(channel, author, self, "`" + username + "` is the bot's author.\nYou can't remove him from the op list.", colorError);
					return ("!<Remove> User tried to remove the bot's author from the Ops list.");
				} else {
					sendCommandMessage(channel, author, self, "`" + username + "` isn't op.", colorError);
					return ("!<Remove> " + user.getName() + " isn't op.");
				}
			}
			sendCommandMessage(channel, author, self, "`" + username + "` has been removed from the Ops list.", colorCommand);
			return ("<Remove> " + user.getName() + " has been removed from the Ops list.");
		}
		
		sendSubCommandMessage(channel, author, self);
		return ("!Unknown.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"op",
			"ops"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this commands to edit the bot's ops.**");
	}

	@Override
	public String getHelpDescription() {
		return ("Edits the ops list.");
	}

	@Override
	public String getName() {
		return ("Op Command");
	}

	@Override
	public String getType() {
		return (BOT_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"op list",
			"op add <user>",
			"op remove <user>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "op list` - View the actual ops.",
			"`" + guildPrefix() + "op add @Shione` - Adds Shione to the ops list.",
			"`" + guildPrefix() + "op remove @Shione` - Removes Shione from the ops list."
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
