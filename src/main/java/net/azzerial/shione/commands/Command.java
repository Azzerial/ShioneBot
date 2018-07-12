package net.azzerial.shione.commands;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import net.azzerial.shione.core.Permissions;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.listeners.GuildEvent;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter {

	public String INVALID_AMOUNT_OF_AGRUMENTS = "!Amount of arguments was incorrect. Showing SubCommands page.";
	public String UNKNOWN = "!Unknown command return.";
	
	public static Color colorInformation = new Color(77, 195, 255); //LIGHT BLUE
	public static Color colorCommand = new Color(255, 102, 179); //PINK
	public static Color colorError = new Color(255, 128, 128); //LIGHT RED
	
	public abstract String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self);
	public abstract List<String> getAliases();
	public abstract String getDescription();
	public abstract String getHelpDescription();
	public abstract String getName();
	public abstract List<String> getSubCommands();
	public abstract List<String> getUsageExamples();
	public abstract boolean isAdminRequired();
	public abstract boolean isOpRequired();
	public abstract String getType();

	public String BROWSING = "BROWSING";
	public String FUN = "FUN";
	public String MUSIC = "MUSIC";
	public String USER_SETTINGS = "USER_SETTINGS";
	public String GUILD_SETTINGS = "GUILD_SETTINGS";
	public String BOT_SETTINGS = "BOT_SETTINGS";

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot() || !event.isFromType(ChannelType.TEXT)) {
			return;
		}
		if (containsCommand(event.getMessage()) && isOpRequired()) {
			if (!Permissions.isOp(event.getAuthor())) {
				System.out.println(ShioneInfo.getTime() + "[" + getName() + "]: [" + event.getAuthor().getName() + "](" + event.getAuthor().getId() + ") tried to run the command but wasn't Op.");
				sendMissingMessage(event.getTextChannel(), event.getAuthor(), event.getJDA().getSelfUser(), true);
				return;
			}
		}
		if (containsCommand(event.getMessage()) && isAdminRequired()) {
			if (!GuildEvent.getServer(event.getGuild().getId()).isAdmin(event.getAuthor())) {
				System.out.println(ShioneInfo.getTime() + "[" + getName() + "]: [" + event.getAuthor().getName() + "](" + event.getAuthor().getId() + ") tried to run the command but wasn't Admin.");
				sendMissingMessage(event.getTextChannel(), event.getAuthor(), event.getJDA().getSelfUser(), false);
				return;
			}
		}
		if (containsCommand(event.getMessage())) {
			System.out.println(ShioneInfo.getTime() + "[" + getName() + "]: [" + event.getAuthor().getName() + "](" + event.getAuthor().getId() + ") executed the command.");
			String output = onCommand(event, commandArgs(event.getMessage()), event.getTextChannel(), event.getAuthor(), event.getJDA().getSelfUser());
			System.out.println("[" + getName() + "]" +
			(output.startsWith("!") ? "(FAILURE): " + output.substring(1) : "(SUCCESS): " + output));
		}
	}
	
	protected boolean containsCommand(Message message) {
		String string = commandArgs(message)[0];
		
		if (string.startsWith(ShioneInfo.PREFIX)) {
			string = string.substring(ShioneInfo.PREFIX.length());
		} else {
			return (false);
		}
		return (getAliases().contains(string.toLowerCase()));
	}
	
	protected String[] commandArgs(Message message) {
		return (message.getContentRaw().split(" "));
	}
	
	private void sendMissingMessage(TextChannel channel, User author, User self, boolean opCase) {
		MessageUtil.sendEmbedMessage(channel, author, self,
			getName(),
			(opCase ? Permissions.OP_REQUIRED_MESSAGE : GuildEvent.ADMIN_REQUIRED_MESSAGE),
			colorError);
	}
	
	public String getSubCommandsCodeBlock() {
		String string = "__SubCommands:__\n```md\n";
		
		for (String subCommand : getSubCommands()) {
			string += "* " + subCommand + "\n";
		}
		string += "```";
		return (string);
	}
	
	public String getUsageExamplesBlock() {
		String string = "__Examples:__\n";
		
		for (String usageExample : getUsageExamples()) {
			string += usageExample + "\n";
		}
		return (string);
	}
	
	public String getAliasesBlock() {
		String string = "__Aliases:__\n";
		List<String> aliases = getAliases();
		
		Collections.sort(aliases);
		for (String alias : aliases) {
			string += "`" + alias + "`, ";
		}
		string = string.substring(0, string.length() - 2);
		string += ".";
		return (string);
	}
	
	public void sendSubCommandMessage(MessageChannel channel, User author, User self) {
		MessageUtil.sendEmbedMessage(channel,
			getName(), null, self.getAvatarUrl(),
			null,
			null, null,
			"__Description:__\n" + getDescription() + "\n\n" + getSubCommandsCodeBlock() + "\n" + getUsageExamplesBlock() + "\n" + getAliasesBlock(),
			null,
			"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
			colorInformation);
	}
	
	public void sendCommandMessage(MessageChannel channel, User author, User self, String content, Color color) {
		MessageUtil.sendEmbedMessage(channel,
			getName(), null, self.getAvatarUrl(),
			null,
			null, null,
			content,
			null,
			"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
			color);
	}
}
