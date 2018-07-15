package net.azzerial.shione.commands.guild;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.database.GuildsManager;
import net.azzerial.shione.database.entities.Guilds;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GuildCommand extends Command {

	List<String> subcommands = Arrays.asList("prefix", "setprefix", "addrole", "clearroles", "listroles", "removerole");
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length == 1) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		if (!subcommands.contains(args[1])) {
			return (UNKNOWN_CASE);
		}

		JDA api = event.getJDA();

	// Prefix
		// View prefix
		if (args[1].equalsIgnoreCase("prefix")) {
			if (guild.getPrefix().isEmpty()) {
				sendCommandMessage(channel, author, self, "No custom prefix has been set.", colorError);
				return ("<prefix> There is no prefix set.");
			}
			sendCommandMessage(channel, author, self, "The actual prefix is `" + guild.getPrefix() + "`.", colorCommand);
			return ("<prefix> Sent actual prefix.");
		}
		// Change prefix
		if (args[1].equalsIgnoreCase("setprefix")) {
			if (args.length != 3) {
				sendCommandMessage(channel, author, self, "You need to provide the new prefix.", colorError);
				return ("!<setprefix> Missing string.");
			}
			if (GuildsManager.updateGuildColumn(event.getGuild().getId(), Guilds.PREFIX, args[2])) {
				sendCommandMessage(channel, author, self, "The guild's prefix has been changed to: `" + args[2] + "`", colorCommand);
				return ("<setprefix> Changed the guild's prefix.");
			}
		}

	// Free Roles
		// Add free role
		if (args[1].equalsIgnoreCase("addrole") && args.length >= 3) {
			Role role = null;
			String roleName = "";
			for (int i = 2; i < args.length; i += 1) {
				roleName += args[i] + " ";
			}
			roleName = roleName.substring(0, roleName.length() - 1);

			if (!event.getMessage().getMentionedRoles().isEmpty()) {
				role = event.getMessage().getMentionedRoles().get(0);
			}
			if (role == null && !event.getGuild().getRolesByName(roleName, true).isEmpty()) {
				role = event.getGuild().getRolesByName(roleName, true).get(0);
			}
			if (role == null) {
				sendCommandMessage(channel, author, self, "`" + roleName + "` isn't a valid parameter.", colorError);
				return ("!<addrole> Role fed is an invalid paramater. (" + roleName + ")");
			}
			if (!guild.addFreeRole(role.getId())) {
				sendCommandMessage(channel, author, self, "`" + role.getName() + "` is already a free role.", colorError);
				return ("!<addrole> " + role.getName() + " is already a free role.");
			}
			sendCommandMessage(channel, author, self, "`" + role.getName() + "` has been added to the free roles list.", colorCommand);
			return ("<addrole> " + role.getName() + " has been added to the free roles list.");
		}
		// Clear free role
		if (args[1].equalsIgnoreCase("clearroles")) {
			if (GuildsManager.updateGuildColumn(event.getGuild().getId(), Guilds.ROLES, "")){
				sendCommandMessage(channel, author, self, "The free roles list has been cleared.", colorCommand);
				return ("<clearroles> Cleared the free roles list.");
			}
			sendCommandMessage(channel, author, self, "An unexpected error has occurred.", colorError);
			return (DATABASE_ERROR);
		}
		// List free role
		if (args[1].equalsIgnoreCase("listroles")) {
			List<String> roles = guild.getFreeRolesId();
			String roles_string = "";

			// Check for empty list
			if (roles == null || roles.isEmpty() || roles.size() == 0) {
				sendCommandMessage(channel, author, self, "There are currently no free roles.", colorError);
				return ("<listroles> There are no roles set.");
			} else {
				Collections.sort(roles);

				System.out.println("size: " + roles.size());

				for (String role : roles) {
					System.out.println("role: "+ role);
					roles_string += "`" + event.getGuild().getRoleById(role).getName() + "`, ";
				}
				roles_string = roles_string.substring(0, roles_string.length() - 2);
				roles_string += ".";
			}

			sendCommandMessage(channel, author, self, "The following roles are free:\n" + roles_string, colorCommand);
			return ("<listroles> Displayed the free roles list.");
		}
		// Remove free role
		if (args[1].equalsIgnoreCase("removerole") && args.length >= 3) {
			Role role = null;
			String roleName = "";
			for (int i = 2; i < args.length; i += 1) {
				roleName += args[i] + " ";
			}
			roleName = roleName.substring(0, roleName.length() - 1);

			if (!event.getMessage().getMentionedRoles().isEmpty()) {
				role = event.getMessage().getMentionedRoles().get(0);
			}
			if (role == null && !event.getGuild().getRolesByName(roleName, true).isEmpty()) {
				role = event.getGuild().getRolesByName(roleName, true).get(0);
			}
			if (role == null) {
				sendCommandMessage(channel, author, self, "`" + roleName + "` isn't a valid parameter.", colorError);
				return ("!<removerole> Role fed is an invalid parameter. (" + roleName + ")");
			}
			if (!guild.removeFreeRole(role.getId())) {
				sendCommandMessage(channel, author, self, "`" + role.getName() + "` isn't a free role.", colorError);
				return ("!<removerole> " + role.getName() + " isn't a free role.");
			}
			sendCommandMessage(channel, author, self, "`" + role.getName() + "` has been removed from the free roles list.", colorCommand);
			return ("<Remove> " + role.getName() + " has been removed from the free roles list.");
		}

		return (UNKNOWN_CASE);
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
			"guild prefix",
			"guild setprefix <string>",
			"guild addrole <role>",
			"guild clearroles",
			"guild listroles",
			"guild removerole <role>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "guild prefix` - View the current prefix for Shione's commands.",
			"`" + guildPrefix() + "guild setprefix //` - Sets Shione's commands prefix to `//`.",
			"`" + guildPrefix() + "guild addrole @member` - Adds the `Member` role to the free roles list.",
			"`" + guildPrefix() + "guild addrole member` - Adds the `Member` role to the free roles list.",
			"`" + guildPrefix() + "guild clearroles` - Empties the free roles list.",
			"`" + guildPrefix() + "guild listroles` - Lists the current free roles list.",
			"`" + guildPrefix() + "guild removerole @member` - Removes the `Member` role from the free roles list.",
			"`" + guildPrefix() + "guild removerole member` - Removes the `Member` role from the free roles list."
//			"`" + guildPrefix() + "guild settings` - Opens the bot guild's settings menu."
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
