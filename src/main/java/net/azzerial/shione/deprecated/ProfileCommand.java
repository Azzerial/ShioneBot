package net.azzerial.shione.deprecated;

import java.util.Arrays;
import java.util.List;

import net.azzerial.shione.command.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.sc2d.SC2D;
import net.azzerial.shione.sc2d.entities.impl.Artist;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ProfileCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2) {
			return ("!Amount of arguments was incorrect.");
		}
		
		SC2D api = Shione.getSC2DAPI();
		String permalink = args[1];
		if (permalink.startsWith("<") && permalink.endsWith(">")) {
			permalink = permalink.substring(1, permalink.length() - 1);
		}
		if (permalink.startsWith("https://soundcloud.com/")) {
			permalink = permalink.substring("https://soundcloud.com/".length());
		}
		String id = api.getUserIdFromPermalink(permalink);
		Artist user = api.getUserById(id);
		
		if (user == null) {
			event.getChannel().sendMessage("User not found.").queue();
			return ("!Searched user (" + permalink + "|" + id + ") hasn't been found.");
		}
		event.getChannel().sendMessage(
			"Username" + (user.getPlanSubscription().equals("Free") ? "" : "☆") + ": " + user.getUsername() + "\n" +
			"AvatarUrl: <" + user.getAvatarDefaultUrl() + ">\n" +
			"VisualsUrl: <" + user.getVisualDefaultUrl() + ">\n" +
			"LikesCount: " + user.getLikesCount() + "\n" +
			"TracksCount: " + user.getTracksCount() + "\n" +
			"PlaylistsCount: " + user.getPlaylistsCount() + "\n" +
			"CommentsCount: " + user.getCommentsCount()
		).queue();
		return ("Searched user (" + permalink + "|" + id + ") has been found and correctly displayed.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"profile"
		));
	}

	@Override
	public String getDescription() {
		return ("Shows a SoundCloud's user profile.");
	}

	@Override
	public String getHelpDescription() {
		return ("");
	}

	@Override
	public String getName() {
		return ("Shione Profile");
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
			ShioneInfo.PREFIX + "profile <url>",
			ShioneInfo.PREFIX + "profile https://soundcloud.com/in-love-with-a-ghost"
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
