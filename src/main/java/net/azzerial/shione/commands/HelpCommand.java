package net.azzerial.shione.commands;

import java.util.*;

import net.azzerial.shione.database.GuildsManager;
import net.azzerial.shione.database.Permissions;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.menus.dialogs.PageDialog;
import net.azzerial.shione.utils.EmoteUtil;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {
	
	public static TreeMap<String, Command> commands;

	private static List<Command> browsingCommand;
	private static List<Command> funCommand;
	private static List<Command> musicCommand;
	private static List<Command> userCommand;
	private static List<Command> guildCommand;
	private static List<Command> botCommand;

	private static HashMap<String, String> categoriesTitles;
	
	public HelpCommand() {
		commands = new TreeMap<String, Command>();

		browsingCommand = new ArrayList<Command>();
		funCommand = new ArrayList<Command>();
		musicCommand = new ArrayList<Command>();
		userCommand = new ArrayList<Command>();
		guildCommand = new ArrayList<Command>();
		botCommand = new ArrayList<Command>();

		categoriesTitles = new HashMap<String, String>();
		categoriesTitles.put(BROWSING, EmoteUtil.EYE_IN_SPEECH_BUBBLE + " Browsing");
		categoriesTitles.put(FUN, EmoteUtil.DIRECT_HIT + " Fun");
		categoriesTitles.put(MUSIC, EmoteUtil.HEADPHONE + " Music");
		categoriesTitles.put(USER_SETTINGS, EmoteUtil.GEAR + " User Settings");
		categoriesTitles.put(GUILD_SETTINGS, EmoteUtil.CLASSICAL_BUILDING + " Guild Settings");
		categoriesTitles.put(BOT_SETTINGS, EmoteUtil.ROBOT_FACE + " Bot Settings");
	}
	
	public Command registerCommand(Command command) {
		commands.put(command.getAliases().get(0), command);

		switch (command.getType()) {
			case ("BROWSING"):
				browsingCommand.add(command);
				break;
			case ("FUN"):
				funCommand.add(command);
				break;
			case ("MUSIC"):
				musicCommand.add(command);
				break;
			case ("USER_SETTINGS"):
				userCommand.add(command);
				break;
			case ("GUILD_SETTINGS"):
				guildCommand.add(command);
				break;
			case ("BOT_SETTINGS"):
				botCommand.add(command);
				break;
		}
		return (command);
	}
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length > 2) {
			sendCommandMessage(channel, author, self, "Invalid argument.", colorError);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		Object[] keys = commands.keySet().toArray();

		// help
		if (args.length == 1) {
			sendHelpMessage(event, channel, author, self, keys);
			return ("Displayed the help menu correctly.");
		}

		// help <subcommand>
		for (int i = 0; i < keys.length; i += 1) {
			Command cmd = commands.get(keys[i]);
			
			if (cmd.isOpRequired() && !Permissions.isOp(author)) {
				continue;
			}
			if (cmd.isAdminRequired() && !GuildsManager.getGuild(event.getGuild().getId()).isAdmin(author)) {
				continue;
			}
			if (args[1].equalsIgnoreCase("help")) {
				sendHelpMessage(event, channel, author, self, keys);
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
		sendCommandMessage(channel, author, self, Permissions.PERMISSION_REQUIRED_MESSAGE, colorError);
		return ("!The user didn't have the permission to display that command.");
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
	public String getType() {
		return ("");
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "help` - Shows Shione's commands help page.",
			"`" + guildPrefix() + "help register` - Shows the register command's help page."
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

	private void sendHelpMessage(MessageReceivedEvent event, TextChannel channel, User author, User self, Object[] keys) {
		String msgContent = "__Description:__\n" +
		//	"**The following list contains all the commands Shione will reply to.**\n" +
			"**Shione's commands are classified by categories. The following list contains all of Shione's commands categories.**\n" +
			"Click on the reaction emotes to view the category's commands.\n" +
			"\n" +
			"__Categories:__\n" +
			"```md\n" +
			(browsingCommand.isEmpty() ? "" : "* [" + EmoteUtil.EYE_IN_SPEECH_BUBBLE + "] Browsing\n") +
			(funCommand.isEmpty() ? "" : "* [" + EmoteUtil.DIRECT_HIT + "] Fun\n") +
			(musicCommand.isEmpty() ? "" : "* [" + EmoteUtil.HEADPHONE + "] Music\n") +
			(userCommand.isEmpty() ? "" : "* [" + EmoteUtil.GEAR + "] User Settings\n") +
			(GuildsManager.getGuild(event.getGuild().getId()).isAdmin(author) ? (guildCommand.isEmpty() ? "" : "* [" + EmoteUtil.CLASSICAL_BUILDING + "] Guild Settings\n") : "") +
			(Permissions.isOp(author) ? (botCommand.isEmpty() ? "" : "* [" + EmoteUtil.ROBOT_FACE + "] Bot Settings\n") : "") +
			"```\n" +
			"__SubCommands:__\n" +
			"```md\n" +
			"* help <command>" +
			"```\n" +
			getUsageExamplesBlock() + "\n" +
			getAliasesBlock();

		PageDialog.Builder builder = new PageDialog.Builder()
			.setEventWaiter(Shione.getEventWaiter())
			.addUsers(author)
			.setAuthor(author)
			.setSelf(self)
			.setTitle(getName())
			.setDescription(msgContent)
			.setColor(colorInformation)
			.addPage(EmoteUtil.INFORMATION, (m, e) -> {
				MessageUtil.editEmbedMessage(m, author, self,
					getName(),
					msgContent,
					colorInformation);
			});
		addCategoryPage(builder, EmoteUtil.EYE_IN_SPEECH_BUBBLE, categoriesTitles.get(BROWSING), browsingCommand, author ,self);
		addCategoryPage(builder, EmoteUtil.DIRECT_HIT, categoriesTitles.get(FUN), funCommand, author ,self);
		addCategoryPage(builder, EmoteUtil.HEADPHONE, categoriesTitles.get(MUSIC), musicCommand, author ,self);
		addCategoryPage(builder, EmoteUtil.GEAR, categoriesTitles.get(USER_SETTINGS), userCommand, author ,self);
		if (GuildsManager.getGuild(event.getGuild().getId()).isAdmin(author)) {
			addCategoryPage(builder, EmoteUtil.CLASSICAL_BUILDING, categoriesTitles.get(GUILD_SETTINGS), guildCommand, author ,self);
		}
		if (Permissions.isOp(author)) {
			addCategoryPage(builder, EmoteUtil.ROBOT_FACE, categoriesTitles.get(BOT_SETTINGS), botCommand, author, self);
		}
		PageDialog dialog = builder
			.addPage(EmoteUtil.HEAVY_MULTIPLICATION_X, (m, e) -> {
				m.delete().queue();
			})
			.build();
		dialog.display(channel);
	}

	private void addCategoryPage(PageDialog.Builder builder, String emote, String title, List<Command> cmds,
			User author, User self) {
		if (cmds.isEmpty()) {
			return;
		}

		builder.addPage(emote, (m, e) -> {
			MessageUtil.editEmbedMessage(m, author, self,
				title,
				getCategoryDescription(cmds),
				colorInformation);
		});
	}

	private String getCategoryDescription(List<Command> cmds) {
		String msgContent = "__Commands:__\n" +
			"```md\n";
		for (Command cmd : cmds) {
			String name = cmd.getName();
			name = name.substring(0, name.length() - 8);

			msgContent += "* " + name + " - " + cmd.getHelpDescription() + "\n";
		}
		msgContent += "```";
		return (msgContent);
	}
}
