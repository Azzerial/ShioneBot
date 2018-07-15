import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.menus.dialogs.PageDialog;
import net.azzerial.sc2d.entities.enums.AvatarFormat;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.shione.utils.EmotesUtils;
import net.azzerial.shione.utils.MessageUtils;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ExampleCommand extends Command { // All commands must extend the Command class.

	/**
	 * This is the method where the command code is ran.
	 *
	 * The method provides 5 parameters:
	 * 	- event: the JDA MessageReceivedEvent event contains multiple information from the Discord
	 * 		message containing the command sent by the user.
	 * 	- args: the String[] arguments of the message sent by the user.
	 * 	- channel: the JDA TextChannel channel in which the message has been sent.
	 * 	- author: the JDA User author who sent the message.
	 * 	- self: the JDA User self who represents the bot himself.
	 *
	 * The method needs a String in return. This string will be displayed in the Console when ever the command is typed.
	 * If the String doesn't start with a ! then the command has been successfully executed. If ever the String starts
	 * with a ! then displays in the Console that there has been an error with the command.
	 * This is only meant to understand where the error occured when debbuging and to see what did the user tried to do with the command.
	 */
	@Override
	public String onCommand(MessageReceivedEvent event, String[] args, TextChannel channel, User author, User self) {
		// First we verify is the number of arguments of the command matches the needed ones.
		if (args.length != 2) { // Here we need two arguments: the bot's prefix + command and the command's parameter.
			sendSubCommandMessage(channel, author, self); // This function sends a generated help message for this comand.
			return (INVALID_AMOUNT_OF_AGRUMENTS); // Returns a default error String for an invalid amount of arguments.
		}

		User user = null; // Create a empty discord user.

		// Here we are changing the value of the beforehand created discord user.
		if (!event.getMessage().getMentionedUsers().isEmpty()) { // Check if the message sent contains @mentions.
			user = event.getMessage().getMentionedUsers().get(0);	// If it's the case then set the user variable to the mentionned user.
		}								// If not, the user variable stays null.

		// We check if the value has been changed.
		if (user == null) { // If it's still at null then it means that there were no mentionned user.
			return ("!There is no mentionned user!"); // Returns an error String starting by a "!".
		}

		// Now we will send the greeting message as we know which user we have to greet.
		sendCommandMessage(channel, author, self, user.getName() + ", you have " + author.getName() + "'s greetings.", colorCommand); // Send a message in the channel where the command was typed.

		return ("Successfully greeted " + user.getName() + "(" + user.getId() + ")."); // Message sent, we return a success message.
	}

	/*
	 * This method sets the command's keywords. (Must contain at least one keyword.)
	 * Any of these keywords will execute this command when a recieved message starts by one of them.
	 *
	 * Note that only the first one of the list will be used in generated examples.
	 */
	@Override
	public List<String> getAliases() {
		return (Arrays.asList( // Here both "hello" and "hi" will trigger the command.
			"greet",
			"hello",
			"hi"
		));
	}

	/*
	 * This method sets the command's full description.
	 * This description will be displayed in the generated help message.
	 */
	@Override
	public String getDescription() {
		return ("**Use this commands to greet a friend.**\n"
			+ "Note that the mentioned user must have registered.");
	}

	/*
	 * This method sets the command's short description.
	 * This description (made up to 7 words) will be displayed in the help command's message.
	 */
	@Override
	public String getHelpDescription() {
		return ("Greet somebody.");
	}

	/*
	 * This method sets the command's name.
	 * The command's name is displayed in each bot's message about from command.
	 *
	 * Note that by convention the command's name is only made of 2 words:
	 * The first one being the command's goal and the seconds the words "Command".
	 * Generally the command's name is the same as the class's name.
	 *
	 * If ever you really need to use more than 2 words (but won't ever be more than 3):
	 * 	- either concat (without caps) the 2 words as for the "Whois Command".
	 * 	- or concat (with cas) the words as for example "UserAccount Command".
	 */
	@Override
	public String getName() {
		return ("Greet Command");
	}

	/*
	 * This method sets the command's sub-commands list.
	 * Sub-Commands are all the ways using the command.
	 *
	 * There are some conventions for the way of writing a sub-command:
	 * 	- mandatory words are written normally.
	 * 	- paramters must be between angle brackets.
	 *
	 * These are the general types of arguments:
	 * <user>	A mentionned discord user.
	 * <channel>	A mentionned discord channel.
	 * <url>	A html URL.
	 * <string>	A String type single word.
	 * <"string">	A String type sentence.
	 * <value>	A int type value.
	 *
	 * If needed are if it makes more sence, you can write a custom named argument type.
	 */
	@Override
	public List<String> getSubCommands() {
		return (Arrays.asList(
			"greet <user>"
		));
	}

	/*
	 * This method sets an example of how to use the command and it's sub-commands.
	 * These examples will be displayed in the generated help message.
	 *
	 * Always start the example by the bot's prefix (which can be get in the ShioneInfo class).
	 * And always surround the command syntax part by two ` .
	 * Seperate the command syntax part and the description part with " - ".
	 * Add to the command's example the description. Don't hesitate to bold or underline the text.
	 */
	@Override
	public List<String> getUsageExamples() {
		return (Arrays.asList(
			"`" + guildPrefix() + "greet @Azzerial` - Sends a greeting message in the channel for *Azzerial*."
		));
	}

	/*
	 * This method determines who is allowed to use this command.
	 *
	 * If set to:
	  * - true	Only the guild's admins (and the bot's ops) will be able to use the command.
	  * - false	Everyone in the guild is allowed to use the command.
	 */
	@Override
	public boolean isAdminRequired() {
		return (false);
	}

	/*
	 * This method determines who is allowed to use this command.
	 *
	 * If set to:
	 * - true	Only the bot's ops will be able to use the command.
	 * - false	Everyone is allowed to use the command.
	 */
	@Override
	public boolean isOpRequired() {
		return (false);
	}
	
}
