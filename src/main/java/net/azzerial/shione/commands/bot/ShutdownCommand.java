package net.azzerial.shione.commands.bot;

import java.util.Arrays;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.menus.dialogs.VerficationDialog;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 1) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		
		VerficationDialog dBuilder = new VerficationDialog.Builder()
			.setEventWaiter(Shione.getEventWaiter())
			.addUsers(author)
			.setAuthor(author)
			.setSelf(self)
			.setTitle(getName())
			.setDescription("Are you sure you want to shut down bot?")
			.setValidateAction(m -> {
				m.clearReactions().queue();
				MessageUtil.editEmbedMessage(m, author, self, getName(), "じゃね〜 / Ja nē!", colorCommand);
				System.out.println(ShioneInfo.getTime() + "[Command/ShutdownCommand]: じゃね〜 / Ja nē!");
				Shione.getAPI().shutdown();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.exit(Shione.NORMAL_SHUTDOWN);
			})
			.setCancelAction(m -> {
				m.clearReactions().queue();
				MessageUtil.editEmbedMessage(m, author, self, getName(), "Your request has been canceled.", colorCommand);
				System.out.println(ShioneInfo.getTime() + "[Command/ShutdownCommand]: Request has been canceled.");
			})
			.build();
		dBuilder.display(channel);
		
		return ("Successfully sent the shutdown request.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"shutdown",
			"bye",
			"sleep",
			"rest",
			"goodnight",
			"gn",
			"die",
			"kill",
			"kys"
		));
	}

	@Override
	public String getDescription() {
		return ("**Shuts down the bot.**");
	}

	@Override
	public String getHelpDescription() {
		return ("Shuts down the bot.");
	}

	@Override
	public String getName() {
		return ("Shutdown Command");
	}

	@Override
	public String getType() {
		return (BOT_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"shutdown"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "shutdown` - Shuts down the bot."
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
