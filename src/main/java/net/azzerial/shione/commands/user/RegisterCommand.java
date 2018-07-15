package net.azzerial.shione.commands.user;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.database.UsersManager;
import net.azzerial.shione.database.entities.Users;
import net.azzerial.shione.menus.dialogs.VerficationDialog;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.shione.utils.MessageUtil;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RegisterCommand extends Command {

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private final File usersDir = new File("./Users");
	
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		if (args.length != 2) {
			sendSubCommandMessage(channel, author, self);
			return (INVALID_AMOUNT_OF_AGRUMENTS);
		}
		Users user = UsersManager.getUser(author.getId());

		if (args[1].equalsIgnoreCase("unregister") && user.isRegistered()) {
			VerficationDialog dBuilder = new VerficationDialog.Builder()
				.setEventWaiter(Shione.getEventWaiter())
				.addUsers(author)
				.setAuthor(author)
				.setSelf(self)
				.setTitle(getName())
				.setDescription("Are you sure to unregister your current SoundCloud account? (An identity thief might steal it from you.)")
				.setValidateAction(m -> {
					user.removeSoundCloudAccount();
					m.clearReactions().queue();
					MessageUtil.editEmbedMessage(m, author, self, getName(), "Your SoundCloud account has successfully been unregistered.", colorCommand);
				})
				.build();
			dBuilder.display(channel);
			return ("Registration remove request sent.");
		} else if (args[1].equals("unregister")) {
			sendCommandMessage(channel, author, self, "You first need to register before unregistering!\n"
				+ "Type `./register` for more information.", colorError);
			return ("!User hasn't registered yet.");
		}
		
		if (user.isRegistered()) {
			sendCommandMessage(channel, author, self, "You have already registered!\n"
				+ "If you want to change your account, you have to unregister to register again.\n"
				+ "Type `./register` for more information.", colorError);
			return ("!User has already registered.");
		}
		if (args[1].length() <= 23 && (args[1].startsWith("https://") || args[1].startsWith("<https://"))) {
			sendCommandMessage(channel, author, self, "Invalid link!", colorError);
			return ("!Link too short.");
		}
		
		String link = "";
		if (args[1].startsWith("<") && args[1].endsWith(">")) {
			link = args[1].substring(1, args[1].length() - 1);
		} else {
			link = args[1];
		}

		/*
		 * When using the command, people might only want to type
		 * the artist itself, so we might as well check if it's a link,
		 * and use it as a permalink instead if it isn't.
		 *
		 */

		String permalink = "";
		if (!link.startsWith("https://soundcloud.com/")) {
			if (!link.startsWith("https://")) {
				permalink=link;
			} else {
				sendCommandMessage(channel, author, self, "Invalid link!", colorError);
				return ("!Link isn't from soundcloud.");
			}
		}else {
			permalink = link.substring(23);
		}
		String userId = null;
		userId = Shione.getSC2DAPI().getArtistIdFromPermalink(permalink);
		if (userId == null) {
			sendCommandMessage(channel, author, self, "This user doesn't exist!", colorError);
			return ("!User doesn't exists.");
		}
		Artist artist = Shione.getSC2DAPI().getArtistById(userId);
		
		if (!UsersManager.isSoundCloudAccountAvailable(artist.getId())) {
			User owner = event.getJDA().getUserById(UsersManager.getUserFromSoundCloudId(artist.getId()).getId());
			sendCommandMessage(channel, author, self, "This account has already been registered by `" + owner.getName() + "#" + owner.getDiscriminator() + "`!", colorError);
			return ("!AccountDeprecated (" + permalink + ") is already owner by " + owner.getName() + "#" + owner.getDiscriminator() + ".");
		}
		
		VerficationDialog dBuilder = new VerficationDialog.Builder()
			.setEventWaiter(Shione.getEventWaiter())
			.addUsers(author)
			.setAuthor(author)
			.setSelf(self)
			.setTitle(getName())
			.setThumbnail(artist.getAvatarDefaultUrl())
			.setDescription("Is this you?\n\n"
				+ "__Username:__ `" + artist.getUsername() + "`" + (artist.getPlanSubscription().equals("Free") ? "" : "✪") +"\n"
				+ (artist.getFullName() != null ? "__Name:__ `" + artist.getFullName() + "`\n" : "")
				+ (artist.getCountry() != null ? "__Country:__ `" + artist.getCountry() + "`\n" : "")
				+ (artist.getCity() != null ? "__City:__ `" + artist.getCity() + "`\n" : "")
				+ "__Followers:__ `" + artist.getFollowersCount() + "`\n"
				+ "__Followings:__ `" + artist.getFollowingsCount() + "`")
			.setImage(artist.getVisualDefaultUrl())
			.setValidateAction(m -> {
				m.clearReactions().queue();
				if (user.addSoundCloudAccount(artist)) {
					MessageUtil.editEmbedMessage(m, author, self, getName(), "You have registered [" + artist.getUsername() + "](" + artist.getPermalinkUrl() + ") as your SoundCloud account.", colorCommand);
				} else {
					MessageUtil.editEmbedMessage(m, author, self, getName(), "An error has occurred while trying to register your account.", colorError);
				}
			})
			.build();
		dBuilder.display(channel);
		
		/*
		 * register <link>
		 * 
		 * 1. Check if the user already has been registered.
		 * 2. Check if the link works or if it belongs to someone else.
		 * 3. If it's his 1st account then put it as his main account. Else, add it as a secondary account.
		 * 4. Really to go.
		 * 
		 * You can only store up to 5 accounts.
		 * Accounts order can be changed with settings commands.
		 * [1][2][3][4][5][⬆][⬇] -> clickable reaction emojis to change the order
		 */
		
		return ("Registration request sent.");
	}

	@Override
	public List<String> getAliases() {
		return (Arrays.asList(
			"register"
		));
	}

	@Override
	public String getDescription() {
		return ("**Use this commands to register your SoundCloud account in Shione's Database.** " + "\n"
			+ "Registering your account will enable Shione to link your SoundCloud account to Discord.\n"
//			+ "You can register multiple accounts if needed, but you need to register at least one in order to use Shione's commands, which can be viewed by typing `./help`.\n"
			+ "If your account is already registered to someone else, please PM **@"+ShioneInfo.BOT_AUTHOR+"**.");
	}

	@Override
	public String getHelpDescription() {
		return ("Links your SoundCloud account.");
	}
	
	@Override
	public String getName() {
		return ("Register Command");
	}

	@Override
	public String getType() {
		return (USER_SETTINGS);
	}

	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"register <soundcloud_link>",
			"register <soundcloud_name>",
			"register unregister"
		));
	}

	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + ShioneInfo.PREFIX + "register https://soundcloud.com/alexis` - Links *Alexis Fellenius* SoundCloud account to your Discord account.",
			"`" + ShioneInfo.PREFIX + "register kovenuk` - registers the Account from `https://soundcloud.com/kovenuk` to your Discord Account",
            "`" + ShioneInfo.PREFIX + "register unregister` - Unregisters *Alexis Fellenius* SoundCloud account from your account."
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
