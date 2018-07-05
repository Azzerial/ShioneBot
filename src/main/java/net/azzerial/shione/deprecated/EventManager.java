package net.azzerial.shione.deprecated;

import java.util.ArrayList;

import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@SuppressWarnings("unused")
public class EventManager extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// JDA relative infos
		JDA api = event.getJDA();
		long response = event.getResponseNumber();
		
		// Message relative infos
		ChannelType channel_type = event.getChannelType();
		Message message = event.getMessage();
		MessageChannel channel = event.getChannel();
		User author = event.getAuthor();
		User self = event.getJDA().getSelfUser();
		
		// Message content relative infos
		String msg = event.getMessage().getContentDisplay();
		String raw = event.getMessage().getContentRaw();
		String content = event.getMessage().getContentStripped();
		String[] args = content.split(" ");
		
		if (author != self && !author.isBot()) {
			if (content.equals("shutdown")
			&& Shione.getPermissions().isOp(author)) {
				channel.sendMessage("Janē!").queue();
				System.out.println("[Core/EventManager]: Janē!");
				api.shutdown();
				System.exit(Shione.NORMAL_SHUTDOWN);
			}
			
			if (content.equals("ops")
			&& Shione.getPermissions().isOp(author)) {
				ArrayList<String> ops = Shione.getPermissions().getOps();
				String ops_string = "";
				
				for (String op : ops) {
					ops_string += api.getUserById(op).getName() + ", ";
				}
				ops_string = ops_string.substring(0, ops_string.length() - 2);
				ops_string += ".";
				channel.sendMessage("This is the current Ops list: " + ops_string).queue();
				return;
			}
			
			if (args[0].equals("addop") && args.length == 2
			&& Shione.getPermissions().isOp(author)) {
				User user = null;
				
				// Gets user by @mention.
				if (!event.getMessage().getMentionedUsers().isEmpty()) {
					user = event.getMessage().getMentionedUsers().get(0);
				}
				// Gets user by Id.
				if (args[1].matches("[0-9]+") && api.getUserById(args[1]) != null) {
					user = api.getUserById(args[1]);
				}
				// Checks if user has been modified.
				if (user == null) {
					channel.sendMessage("[Ops]: \'" + args[1] + "\' is an invalid parameter. Either @mention the user, or write his Id.").queue();
					return;
				}
				// Intend to add the user to the Ops database.
				if (!Shione.getPermissions().addOps(user.getId())) {
					channel.sendMessage("[Ops]: " + user.getName() + " is already op.").queue();
					return;
				}
				channel.sendMessage("[Ops]: " + user.getName() + " has been added to the Ops list.").queue();
				return;
			}
			
			if (args[0].equals("removeop") && args.length == 2
			&& Shione.getPermissions().isOp(author)) {
				User user = null;
				
				// Gets user by @mention.
				if (!event.getMessage().getMentionedUsers().isEmpty()) {
					user = event.getMessage().getMentionedUsers().get(0);
				}
				// Gets user by Id.
				if (args[1].matches("[0-9]+") && api.getUserById(args[1]) != null) {
					user = api.getUserById(args[1]);
				}
				// Checks if user has been modified.
				if (user == null) {
					channel.sendMessage("[Ops]: \'" + args[1] + "\' is an invalid parameter. Either @mention the user, or write his Id.").queue();
					return;
				}
				// Intend to remove the user to the Ops database.
				if (!Shione.getPermissions().removeOps(user.getId())) {
					if (user.getId().equals(ShioneInfo.BOT_AUTHOR_ID)) {
						channel.sendMessage("[Ops]: " + user.getName() + " is the bot's author. You can't remove him from the op list.").queue();
					} else {
						channel.sendMessage("[Ops]: " + user.getName() + " isn't op.").queue();
					}
					return;
				}
				channel.sendMessage("[Ops]: " + user.getName() + " has been removed from the Ops list.").queue();
				return;
			}
			
//			if (event.isFromType(ChannelType.TEXT)) {
//				// In Guild infos
//				Guild guild = event.getGuild();
//				TextChannel text_channel = event.getTextChannel();
//				Member member = event.getMember();
//				
//				text_channel.sendMessage("This is a text channel!").queue();
//			} else if (event.isFromType(ChannelType.PRIVATE)) {
//				// In Private Messages infos
//				PrivateChannel private_channel = event.getPrivateChannel();
//
//				private_channel.sendMessage("This is a private channel!").queue();
//			} else if (event.isFromType(ChannelType.GROUP)) {
//				// In Group chat infos
//				Group group = event.getGroup();
//				String group_name = group.getName() == null ? "" : event.getGroup().getName();
//
//				group.sendMessage("This is a group!").queue();
//			} else {
//				api.shutdown();
//				System.exit(Shione.UNKNOWN_CASE);
//			}
		}
	}
}
