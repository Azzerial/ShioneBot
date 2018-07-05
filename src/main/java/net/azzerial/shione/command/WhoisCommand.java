package net.azzerial.shione.command;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.azzerial.shione.command.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.dialogs.PageDialog;
import net.azzerial.shione.sc2d.entities.AvatarFormat;
import net.azzerial.shione.sc2d.entities.impl.Artist;
import net.azzerial.shione.utils.EmotesUtils;
import net.azzerial.shione.utils.MessageUtils;
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
		
		if (event.getMessage().getMentionedUsers().isEmpty()) {
			sendCommandMessage(channel, author, self, "You need to mention a user.", colorError);
			return ("!No mentionned user.");
		}
		User user = event.getMessage().getMentionedUsers().get(0);

		if (!RegisterCommand.isRegistered(author)) {
			sendCommandMessage(channel, author, self, "You need to register in order to user this command.", colorError);
			return ("!User wasn't registered.");
		}
		if (!RegisterCommand.isRegistered(user)) {
			if (event.getGuild().isMember(user)) {
				sendCommandMessage(channel, author, self, "`" + event.getGuild().getMember(user).getEffectiveName() + "` isn't registered.", colorError);
			} else {
				sendCommandMessage(channel, author, self, "`" + user.getName() + "` isn't registered.", colorError);
			}
			return ("!Mentionned user wasn't registered.");
		}
		Artist artist = RegisterCommand.getUserSoundCloudArtist(user);
		
		PageDialog dialog = new PageDialog.Builder()
			.setEventWaiter(Shione.getEventWaiter())
			.addUsers(author)
			.setAuthor(author)
			.setSelf(self)
			.setTitle(EmotesUtils.INFORMATION  +" Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?")
			.setLink(artist.getPageUrl())
			.setThumbnail(artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl())
			.setDescription(getInformationDescription(artist))
			.setImage(artist.getVisualDefaultUrl())
			.setColor(colorCommand)
			.addPage(EmotesUtils.INFORMATION, (m, e) -> {
				MessageUtils.editEmbedMessage(m,
					EmotesUtils.INFORMATION + " Whois " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPageUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getInformationDescription(artist),
					artist.getVisualDefaultUrl(),
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.addPage(EmotesUtils.BAR_CHART, (m, e) -> {
				MessageUtils.editEmbedMessage(m,
					EmotesUtils.BAR_CHART + " Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPageUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getStatisticsDescription(artist),
					null,
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.addPage(EmotesUtils.LINK, (m, e) -> {
				MessageUtils.editEmbedMessage(m,
					EmotesUtils.LINK + " Who is " + (event.getGuild().isMember(user) ? event.getGuild().getMember(user).getEffectiveName() : user.getName()) + "?", artist.getPageUrl(), self.getAvatarUrl(),
					artist.getAvatar().setFormat(AvatarFormat.T200x200).getUrl(),
					null, null,
					getLinksDescription(artist),
					null,
					"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
					colorCommand);
			})
			.build();
		dialog.display(channel);
		
		/*
		 * To display:
		 * 
		 * --- Info:
		 * Title:
		 * 	Username + pro
		 * 	PageUrl
		 * Personal infos if provided:
		 * 	Names
		 * 	City, country
		 * 	Description
		 * Values:
		 * 	Posts, playlists, albums
		 * 	Followers / followings
		 * 	Total likes, reposts
		 * 
		 * --- Stats (+ rank in server):
		 * Title:
		 * 	Username + pro
		 * 	PageUrl
		 * Stats:
		 *   (YOU)
		 * 	Posts
		 * 	Playlists
		 * 	Albums
		 *   (SOCIAL)
		 * 	Total Likes
		 * 	Reposts
		 * 	Comments
		 *   (INTERACTIONS)
		 * 	Followers
		 * 	Followings
		 * 
		 * --- Timeout Action:
		 * Remove reactions but do not edit the message
		 */
		
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
		return ("**Use this command to get the SoundCloud informations of a user.**\n"
			+ "Note that the mentioned user must have registered.");
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
		String day = (artist.getRegistrationDate().get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + artist.getRegistrationDate().get(Calendar.DAY_OF_MONTH);
		String month = (artist.getRegistrationDate().get(Calendar.MONTH) < 10 ? "0" : "") + (artist.getRegistrationDate().get(Calendar.MONTH) + 1);
		String year = "" + artist.getRegistrationDate().get(Calendar.YEAR);
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
		
		description += "`" + artist.getUsername() + "` links:\n\n";
		description += "__SoundCloud links:__\n";
		description += " • [SoundCloud Home Page](" + artist.getPageUrl() + ")\n";
		description += " • [SoundCloud Avatar](" + artist.getAvatarDefaultUrl() + ")\n";
		description += " • [SoundCloud Visual](" + artist.getVisualDefaultUrl() + ")";
		return (description);
	}
	
}
