package net.azzerial.shione.menus.dialogs;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.azzerial.shione.menus.EmbedMenu;
import net.azzerial.shione.menus.EventWaiter;
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

public class PageDialog extends EmbedMenu {

	private final User author;
	private final User self;
	private final Color color;
	private final String title;
	private final String link;
	private final String thumbnail;
	private final String description;
	private final String image;
	private final LinkedHashMap<String, BiConsumer<Message, ReactionEmote>> pages;
	private final Consumer<Message> timeoutAction;
	
	PageDialog(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit,
			   User author, User self, Color color, String title, String link, String thumbnail, String description, String image,
			   LinkedHashMap<String, BiConsumer<Message, ReactionEmote>> pages, Consumer<Message> timeoutAction) {
		super(waiter, users, roles, timeout, unit);
		this.author = author;
		this.self = self;
		this.color = color;
		this.title = title;
		this.link = link;
		this.thumbnail = thumbnail;
		this.description = description;
		this.image = image;
		this.pages = pages;
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
		List<String> emotes = new ArrayList<String>(pages.keySet());
		
		restAction.queue(m -> {
			for (int i = 0; i < emotes.size(); i += 1) {
				if (i + 1 < emotes.size()) {
					m.addReaction(emotes.get(i)).queue();
				} else {
					m.addReaction(emotes.get(i)).queue(a -> createEvent(m, emotes));
				}
			}
		});
	}
	
	private void createEvent(Message message, List<String> emotes) {
		waiter.addNewEvent(MessageReactionAddEvent.class,
			(event) -> {
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
				if (!emotes.contains(event.getReactionEmote().getName())) {
					return;
				}
				pages.get(event.getReactionEmote().getName()).accept(message, event.getReactionEmote());
				event.getReaction().removeReaction(author).queue();
				createEvent(message, emotes);
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
	
	public static class Builder extends EmbedMenu.Builder<Builder, PageDialog> {

		private User author;
		private User self;
		private Color color;
		private String title;
		private String link;
		private String thumbnail;
		private String description;
		private String image;
		private final LinkedHashMap<String, BiConsumer<Message, ReactionEmote>> pages = new LinkedHashMap<String, BiConsumer<Message, ReactionEmote>>();
		private Consumer<Message> timeoutAction = m -> {
			m.clearReactions().queue();
		};
		
		@Override
		public PageDialog build() {
			if (pages.keySet().size() != pages.values().size()) {
				return (null);
			}
			return (new PageDialog(waiter, users, roles, timeout, unit, author, self, color, title, link, thumbnail, description, image, pages, timeoutAction));
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
		
		public Builder addPage(String emote, BiConsumer<Message, ReactionEmote> action) {
			this.pages.put(emote, action);
			return (this);
		}
		
		public Builder addPages(List<String> emotes, List<BiConsumer<Message, ReactionEmote>> actions) {
			for (int i = 0; i< emotes.size(); i += 1) {
				this.pages.put(emotes.get(i), actions.get(i));
			}
			return (this);
		}
		
		public Builder setPage(String emote, BiConsumer<Message, ReactionEmote> action) {
			this.pages.clear();
			this.pages.put(emote, action);
			return (this);
		}
		
		public Builder setPages(List<String> emotes, List<BiConsumer<Message, ReactionEmote>> actions) {
			this.pages.clear();
			for (int i = 0; i< emotes.size(); i += 1) {
				this.pages.put(emotes.get(i), actions.get(i));
			}
			return (this);
		}
		
		public Builder setTimeoutAction(Consumer<Message> timeoutAction) {
			this.timeoutAction = timeoutAction;
			return (this);
		}
		
	}
	
}
