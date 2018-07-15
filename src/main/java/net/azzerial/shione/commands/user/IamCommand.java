package net.azzerial.shione.commands.user;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.Arrays;
import java.util.List;

public class IamCommand extends Command {
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length == 1) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}

		Role role = null;
		String roleName = "";
		for (int i = 1; i < args.length; i += 1) {
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
			return ("!Role fed is an invalid parameter. (" + roleName + ")");
		}
		if (!guild.isFreeRole(role)) {
			sendCommandMessage(channel, author, self, "`" + roleName + "` isn't a free role.", colorError);
			return ("!Role isn't a free role. (" + roleName + ")");
		}
		if (event.getGuild().getMember(author).getRoles().contains(role)) {
			sendCommandMessage(channel, author, self, "You already have the `" + roleName + "` role.", colorError);
			return ("!The user already have this role. (" + roleName + ")");
		}
		if (event.getGuild().getSelfMember().canInteract(role) || event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) {
			event.getGuild().getController().addSingleRoleToMember(event.getGuild().getMember(author), role).queue();
			sendCommandMessage(channel, author, self, "You now have the `" + role.getName() + "` role.", colorCommand);
			return ("The user now have the role: " + role.getName() + ".");
		} else {
			sendCommandMessage(channel, author, self, "I can't interact with the `" + role.getName() + "` role.", colorCommand);
			return ("Bot can't interact with the role. (" + role.getName() + ")");
		}
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"iam",
			"freerole",
			"getrole"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this commands in order to get a role in this guild.**");
	}

	@Override
	public String getHelpDescription() {
		return ("Gives you the wanted role.");
	}
	
	@Override
	public String getName() {
		return ("Iam Command");
	}

	@Override
	public String getType() {
		return (USER_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"iam <role>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "iam @member` - Gives you the `Member` role.",
			"`" + guildPrefix() + "iam member` - Gives you the `Member` role."
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

}
