package net.azzerial.shione.commands.bot;

import java.util.Arrays;
import java.util.List;
import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.utils.EmoteUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 1) {
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}

		sendCommandMessage(channel, author, self, EmoteUtil.ROBOT_FACE, colorCommand);
		/*
		Track t = Shione.getSC2DAPI().getTrackById(args[1]);

		String content = "";
		content += "Artist Id: `" + t.getArtistId() + "`\n";
		content += "Artist Permalink: `" + t.getArtistPermalink() + "`\n";
		content += "Artist Username: `" + t.getArtistUsername() + "`\n";
		content += "Comments Count: `" + t.getCommentsCount() + "`\n";
		String day = (t.getCreationDate().get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + t.getCreationDate().get(Calendar.DAY_OF_MONTH);
		String month = (t.getCreationDate().get(Calendar.MONTH) < 10 ? "0" : "") + (t.getCreationDate().get(Calendar.MONTH) + 1);
		String year = "" + t.getCreationDate().get(Calendar.YEAR);
		content += "Creation Date: `" + day + "/" + month + "/" + year + "`\n";
		content += "Description: `" + t.getDescription() + "`\n";
		content += "Download Count: `" + t.getDownloadCount() + "`\n";
		content += "Download Url: `" + t.getDownloadUrl() + "`\n";
		content += "Duration: `" + t.getDuration() + "`\n";
		content += "Duration Seconds: `" + t.getDurationSeconds() + "`\n";
		content += "Duration String: `" + t.getDurationString() + "`\n";
		content += "Genre: `" + t.getGenre() + "`\n";
		content += "Likes Count: `" + t.getLikesCount() + "`\n";
		content += "Permalink: `" + t.getPermalink() + "`\n";
		content += "Permalink Url: `" + t.getPermalinkUrl() + "`\n";
		content += "Playback Count: `" + t.getPlaybackCount() + "`\n";
		content += "Reposts count: `" + t.getRepostsCount() + "`\n";
		content += "Stream Preview Url: `" + t.getStreamPreviewUrl() + "`\n";
		content += "Stream Url: `" + t.getStreamUrl() + "`\n";
		content += "Tags: `";
		for (String tag : t.getTags()) {
			content += tag + ", ";
		}
		content = content.substring(0, content.length() - 2);
		content += "`\n";
		content += "Title: `" + t.getTitle() + "`\n";
		content += "Id: `" + t.getTrackId() + "`\n";
		content += "Waveform Image Url: `" + t.getWaveformImageUrl() + "`\n";
		content += "Is Commentable: `" + t.isCommentable() + "`\n";
		content += "Is Downloadable: `" + t.isDownloadable() + "`\n";
		content += "Is Public: `" + t.isPublic() + "`\n";
		content += "Is Streamable: `" + t.isStreamable() + "`\n";

		sendCommandMessage(channel, author, self, content, colorInformation);
		*/
		/* Comment
		Comment c = Shione.getSC2DAPI().getCommentById(args[1]);
		String content = "";
		content += "Artist Id: `" + c.getArtistId() + "`\n";
		content += "Artist Permalink: `" + c.getArtistPermalink() + "`\n";
		content += "Artist Username: `" + c.getArtistUsername() + "`\n";
		content += "Author Id: `" + c.getAuthorId() + "`\n";
		content += "Author Permalink: `" + c.getAuthorPermalink() + "`\n";
		content += "Author Username: `" + c.getAuthorUsername() + "`\n";
		content += "Comment Content: `" + c.getContentRaw() + "`\n";
		String day = (c.getCreationDate().get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + c.getCreationDate().get(Calendar.DAY_OF_MONTH);
		String month = (c.getCreationDate().get(Calendar.MONTH) < 10 ? "0" : "") + (c.getCreationDate().get(Calendar.MONTH) + 1);
		String year = "" + c.getCreationDate().get(Calendar.YEAR);
		content += "Comment Creation Date: `" + day + "/" + month + "/" + year + "`\n";
		content += "Comment Id: `" + c.getId() + "`\n";
		content += "Comment Timestamp: `" + c.getTimestampString() + "`\n";
		content += "Comment Timestamp Long: `" + c.getTimestamp() + "`\n";
		content += "Track Duration: `" + c.getTrackDurationString() + "`\n";
		content += "Track Duration Long: `" + c.getTrackDuration() + "`\n";
		content += "Track Id: `" + c.getTrackId() + "`\n";
		content += "Track Permalink: `" + c.getTrackPermalink() + "`\n";
		content += "Track Title: `" + c.getTrackTitle() + "`";
		sendCommandMessage(channel, author, self, content, colorInformation);
		*/
		return ("Tested.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"test"
		));
	}

	@Override
	public String getDescription() {
		return ("**Testing stuff.**\n"
			+ "Sometimes, magical things happen.");
	}

	@Override
	public String getHelpDescription() {
		return ("Testing stuff.");
	}

	@Override
	public String getName() {
		return ("Test Command");
	}

	@Override
	public String getType() {
		return (BOT_SETTINGS);
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
			"`" + ShioneInfo.PREFIX + "test` - Unleashes unicorns."
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

	public static String secondsToMSFormat(int seconds) {
		String time = "";
		time += (seconds / 60);
		time += ":";
		time += (seconds % 60);
		return (time);
	}

}
