package net.azzerial.shione.commands.browse;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.commands.user.RegisterCommand;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.menus.dialogs.PageDialog;
import net.azzerial.sc2d.entities.enums.AvatarFormat;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.shione.utils.EmoteUtil;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class WhoisCommand extends Command {
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}

		if (event.getMessage().getMentionedUsers().isEmpty() && event.getGuild().getMembersByEffectiveName(args[1],true).isEmpty()) {
			sendCommandMessage(channel, author, self, "There are no users named that way in this guild. Maybe you should try mentioning a user instead.", colorError);
			return ("!No user named that way in the guild.");
		}
		User user = (!event.getMessage().getMentionedUsers().isEmpty() ? (event.getMessage().getMentionedUsers().get(0)) : (event.getGuild().getMembersByEffectiveName(args[1],true).get(0).getUser()));

		if (!RegisterCommand.isRegistered(author)) {
			sendCommandMessage(channel, author, self, "You need to register in order to user this commands.", colorError);
			return ("!User wasn't registered.");
		}
		if (!RegisterCommand.isRegistered(user)) {
			if (event.getGuild().isMember(user)) {
				sendCommandMessage(channel, author, self, "`" + event.getGuild().getMember(user).getEffectiveName() + "` isn't registered.", colorError);
			} else {
				sendCommandMessage(channel, author, self, "`" + user.getName() + "` isn't registered.", colorError);
			}
			return ("!Mentioned user wasn't registered.");
		}
		Artist artist = RegisterCommand.getUserSoundCloudArtist(user);
		
		PageDialog dialog = new PageDialog.Builder()
			.setEventWaiter(Shione.getEventWaiter())
			.addUsers(author)
			.setAuthor(author)
			.setSelf(self)
			.setTitle(EmoteUtil.INFORMATION  +" Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?")
			.setLink(artist.getPermalinkUrl())
			.setThumbnail(artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl())
			.setDescription(getInformationDescription(artist))
			.setImage(artist.getVisualDefaultUrl())
			.setColor(colorCommand)
			.addPage(EmoteUtil.INFORMATION, (m, e) -> {
				MessageUtil.editEmbedMessage(m,
					EmoteUtil.INFORMATION + " Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPermalinkUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getInformationDescription(artist),
					artist.getVisualDefaultUrl(),
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.addPage(EmoteUtil.BAR_CHART, (m, e) -> {
				MessageUtil.editEmbedMessage(m,
					EmoteUtil.BAR_CHART + " Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPermalinkUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getStatisticsDescription(artist),
					null,
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.addPage(EmoteUtil.LINK, (m, e) -> {
				MessageUtil.editEmbedMessage(m,
					EmoteUtil.LINK + " Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPermalinkUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getLinksDescription(artist),
					null,
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.addPage(EmoteUtil.HEAVY_MULTIPLICATION_X, (m, e) -> {
				m.delete().queue();
			})
			.build();
		dialog.display(channel);

		return ("Displayed the user's SoundCloud page.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"whois",
			"soundcloud",
			"info"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this command to get the SoundCloud information of a user.**\n"
			+ "Note that the mentioned user has to be registered.");
	}

	@Override
	public String getHelpDescription() {
		return ("Shows the SoundCloud account.");
	}
	
	@Override
	public String getName() {
		return ("Whois Command");
	}

	@Override
	public String getType() {
		return (BROWSING);
	}
	
	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"whois <user>"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "whois @Azzerial` - Displays *Azzerial*'s SoundCloud account in the channel."
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
	
	private String getInformationDescription(Artist artist) {
		String description = "";
		
		description += "__Username:__ `" + artist.getUsername() + "`\n";
		if (artist.getFullName() != null) {
			description += "__Name:__ `" + artist.getFullName() + "`\n";
		}
		if (artist.getCity() != null) {
			description += "__From:__ `" + artist.getCity() + "`";
			if (artist.getCountry() != null) {
				description += ", `" + artist.getCountry() + "`\n";
			} else {
				description += "\n";
			}
		} else if (artist.getCountry() != null) {
			description += "__From:__ `" + artist.getCountry() + "`\n";
		}
		String day = (artist.getCreationDate().get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + artist.getCreationDate().get(Calendar.DAY_OF_MONTH);
		String month = (artist.getCreationDate().get(Calendar.MONTH) < 10 ? "0" : "") + (artist.getCreationDate().get(Calendar.MONTH) + 1);
		String year = "" + artist.getCreationDate().get(Calendar.YEAR);
		description += "__Member since:__ `" + day + "/" + month + "/" + year + "` (DD/MM/YYYY)\n\n";
		description += "__Description:__\n```\n" + (artist.getDescription() == null ? "" : artist.getDescription()) + "\n```";
		return (description);
	}
	
	private String getStatisticsDescription(Artist artist) {
		String description = "";
		
		description += "`" + artist.getUsername() + "` statistics:\n\n";
		description += "__Tracks:__ `" + artist.getTracksCount() + "`\n";
		description += "__Playlists:__ `" + artist.getPlaylistsCount() + "`\n";
		description += "__Followers:__ `" + artist.getFollowersCount() + "`\n";
		description += "__Followings:__ `" + artist.getFollowingsCount() + "`\n";
		description += "__Likes:__ `" + artist.getLikesCount() + "` (T: `" + artist.getTracksLikesCount() + "`, A/P: `" + artist.getPlaylistsLikesCount() + "`)\n";
		description += "__Reposts:__ `" + artist.getRepostsCount() + "`\n";
		description += "__Comments:__ `" + artist.getCommentsCount() + "`";
		return (description);
	}
	
	private String getLinksDescription(Artist artist) {
		String description = "";

		description += "SoundCloud links:\n";
		description += " • [SoundCloud Home Page](" + artist.getPermalinkUrl() + ")\n";
		description += " • [SoundCloud Avatar](" + artist.getAvatarDefaultUrl() + ")\n";
		description += " • [SoundCloud Visual](" + artist.getVisualDefaultUrl() + ")";
		return (description);
	}

	@SuppressWarnings("unused")
	private String getClosedDescription(Artist artist) {
		String description = "";
	
		description += "" + artist.getPermalinkUrl() + "\n\n";
		description += "__Followers:__`" + artist.getFollowersCount() + "`\n";
		description += "__Tracks:__`" + artist.getTracksCount() + "`";
		return (description);
	}
	
}
