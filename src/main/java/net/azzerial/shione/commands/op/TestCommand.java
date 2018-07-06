package net.azzerial.shione.commands.op;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.azzerial.sc2d.entities.Comment;
import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.entities.Server;
import net.azzerial.shione.listeners.GuildEvent;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestCommand extends Command {

	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2) {
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
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
		content += "Comment Timestamp: `" + secondsToMSFormat(c.getTimestampSeconds()) + "`\n";
		content += "Comment Timestamp Long: `" + c.getTimestamp() + "`\n";
		content += "Track Duration: `" + secondsToMSFormat(c.getTrackDurationSeconds()) + "`\n";
		content += "Track Duration Long: `" + c.getTrackDuration() + "`\n";
		content += "Track Id: `" + c.getTrackId() + "`\n";
		content += "Track Permalink: `" + c.getTrackPermalink() + "`\n";
		content += "Track Title: `" + c.getTrackTitle() + "`";

		sendCommandMessage(channel, author, self, content, colorInformation);
		
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
