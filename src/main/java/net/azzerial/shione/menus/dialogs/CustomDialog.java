package net.azzerial.shione.menus.dialogs;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.menus.EmbedMenu;
import net.azzerial.shione.menus.EventWaiter;
import net.azzerial.shione.utils.MessageUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.RestAction;

public class CustomDialog extends EmbedMenu {

	private final User author;
	private final User self;
	private final Color color;
	private final String title;
	private final String link;
	private final String thumbnail;
	private final String description;
	private final String image;
	private final List<String> emotes;
	private final BiConsumer<Message, ReactionEmote> action;
	private final Consumer<Message> timeoutAction;
	
	CustomDialog(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit,
				 User author, User self, Color color, String title, String link, String thumbnail, String description, String image,
				 List<String> emotes, BiConsumer<Message, ReactionEmote> action, Consumer<Message> timeoutAction) {
		super(waiter, users, roles, timeout, unit);
		this.author = author;
		this.self = self;
		this.color = color;
		this.title = title;
		this.link = link;
		this.thumbnail = thumbnail;
		this.description = description;
		this.image = image;
		this.emotes = emotes;
		this.action = action;
		this.timeoutAction = timeoutAction;
	}

	@Override
	public void display(MessageChannel channel) {
		initialize(channel.sendMessage(getMessage()));
	}

	@Override
	public void update(Message message) {
		initialize(message.editMessage(getMessage()));
	}
	
	private void initialize(RestAction<Message> restAction) {
		restAction.queue(m -> {
			for (int i = 0; i < emotes.size(); i += 1) {
				if (i + 1 < emotes.size()) {
					m.addReaction(emotes.get(i)).queue();
				} else {
					m.addReaction(emotes.get(i)).queue(a -> createEvent(m));
				}
			}
		});
	}
	
	private void createEvent(Message message) {
		waiter.addNewEvent(MessageReactionAddEvent.class,
			event -> {
				if (!event.getMessageId().equals(message.getId())) {
					return (false);
				}
				if (event.getReaction().getReactionEmote().isEmote()) {
					event.getReaction().removeReaction().queue();
					return (false);
				}
				if (!emotes.contains(event.getReaction().getReactionEmote().getName())) {
					event.getReaction().removeReaction().queue();
					return (false);
				}
				return (canUserInteract(event.getUser(), event.getGuild()));
			},
			(MessageReactionAddEvent event) -> {				
				action.accept(message, event.getReactionEmote());
			},
			timeout, unit, () -> timeoutAction.accept(message));
	}
	
	private Message getMessage() {
		EmbedBuilder builder = new EmbedBuilder();
		if (title != null && link != null) {
			builder.setAuthor(title, link, self.getAvatarUrl());
		} else if (title != null) {
			builder.setAuthor(title, null, self.getAvatarUrl());
		}
		if (thumbnail != null) {
			builder.setThumbnail(thumbnail);
		}
		if (description != null) {
			builder.setDescription(description);
		}
		if (image != null) {
			builder.setImage(image);
		}
		builder.setFooter("Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl());
		builder.setTimestamp(Instant.now());
		if (color != null) {
			builder.setColor(color);
		}
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		return (mBuilder.build());
	}
	
	public static class Builder extends EmbedMenu.Builder<Builder, CustomDialog> {

		private User author;
		private User self;
		private Color color;
		private String title;
		private String link;
		private String thumbnail;
		private String description;
		private String image;
		private List<String> emotes = new ArrayList<String>();
		private BiConsumer<Message, ReactionEmote> action = (m, re) -> {};
		private Consumer<Message> timeoutAction = m -> {
			m.clearReactions().queue();
			MessageUtils.editEmbedMessage(m,
				title, null, self.getAvatarUrl(),
				null,
				null, null,
				"Sorry, you took too long.\nThe request has been canceled.",
				null,
				"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
				Command.colorError);
		};
		
		@Override
		public CustomDialog build() {
			return (new CustomDialog(waiter, users, roles, timeout, unit, author, self, color, title, link, thumbnail, description, image, emotes, action, timeoutAction));
		}
		
		public Builder setAuthor(User author) {
			this.author = author;
			return (this);
		}
		
		public Builder setSelf(User self) {
			this.self = self;
			return (this);
		}
		
		public Builder setColor(Color color) {
			this.color = color;
			return (this);
		}
		
		public Builder setTitle(String title) {
			this.title = title;
			return (this);
		}
		
		public Builder setLink(String link) {
			this.link = link;
			return (this);
		}
		
		public Builder setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
			return (this);
		}
		
		public Builder setDescription(String description) {
			this.description = description;
			return (this);
		}
		
		public Builder setImage(String image) {
			this.image = image;
			return (this);
		}
		
		public Builder addEmote(String emote) {
			this.emotes.add(emote);
			return (this);
		}
		
		public Builder addEmotes(String... emotes) {
			this.emotes.addAll(Arrays.asList(emotes));
			return (this);
		}
		
		public Builder setEmote(String emote) {
			this.emotes.clear();
			this.emotes.add(emote);
			return (this);
		}
		
		public Builder setEmotes(String... emotes) {
			this.emotes.clear();
			this.emotes.addAll(Arrays.asList(emotes));
			return (this);
		}
		
		public Builder setAction(BiConsumer<Message, ReactionEmote> action) {
			this.action = action;
			return (this);
		}
		
		public Builder setTimeoutAction(Consumer<Message> timeoutAction) {
			this.timeoutAction = timeoutAction;
			return (this);
		}
		
	}
	
}
