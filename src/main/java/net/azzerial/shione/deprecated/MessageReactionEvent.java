package net.azzerial.shione.deprecated;

//import java.util.HashMap;

import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReactionEvent extends ListenerAdapter {

//	private HashMap<Integer, String> reactions;
//	
//	public MessageReactionEvent() {
//		this.reactions = new HashMap<Integer, String>();
//	}
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
 		System.out.println(event.getUser().getName() + " added: " + event.getReactionEmote().getName() + ", on: " + event.getMessageId());
	}
 	
	@Override
	public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
 		System.out.println(event.getUser().getName() + " removed: " + event.getReactionEmote().getName() + ", from: " + event.getMessageId());
 	}
 		
 	@Override
 	public void onMessageReactionRemoveAll(MessageReactionRemoveAllEvent event) {
 		System.out.println("All reactions have been removed from: " + event.getMessageId());
 	}
 	
}
