package net.azzerial.shione.dialogs;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import net.azzerial.shione.commands.Command;
import net.azzerial.shione.utils.EmotesUtils;
import net.azzerial.shione.utils.MessageUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.RestAction;

public class VerficationDialog extends EmbedDialog {

	private final User author;
	private final User self;
	private final Color color;
	private final String title;
	private final String thumbnail;
	private final String description;
	private final String image;
	private final Consumer<Message> action;
	private final Consumer<Message> cancel;
	private final Consumer<Message> timeoutAction;
	
	VerficationDialog(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit,
			User author, User self, Color color, String title, String thumbnail, String description, String image,
			Consumer<Message> action, Consumer<Message> cancel, Consumer<Message> timeoutAction) {
		super(waiter, users, roles, timeout, unit);
		this.author = author;
		this.self = self;
		this.color = color;
		this.title = title;
		this.thumbnail = thumbnail;
		this.description = description;
		this.image = image;
		this.action = action;
		this.cancel = cancel;
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
		List<String> emotes = new ArrayList<String>();
		emotes.add(EmotesUtils.CHECK_MARK);
		emotes.add(EmotesUtils.CROSS_MARK);
		
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
			event -> {
				if (!event.getMessageId().equals(message.getId())) {
					return (false);
				}
				if (event.getReaction().getReactionEmote().isEmote()) {
					return (false);
				}
				if (!emotes.contains(event.getReaction().getReactionEmote().getName())) {
					return (false);
				}
				return (canUserInteract(event.getUser(), event.getGuild()));
			},
			(MessageReactionAddEvent event) -> {				
				if (event.getReactionEmote().getName().equals(EmotesUtils.CHECK_MARK)) {
					action.accept(message);
					return;
				}
				if (event.getReactionEmote().getName().equals(EmotesUtils.CROSS_MARK)) {
					cancel.accept(message);
					return;
				}
			},
			timeout, unit, () -> timeoutAction.accept(message));
	}
	
	private Message getMessage() {
		EmbedBuilder builder = new EmbedBuilder();
		if (title != null) {
			builder.setAuthor(title, null, self.getAvatarUrl());
		} else {
			builder.setAuthor("Are you sure?", null, self.getAvatarUrl());
		}
		if (thumbnail != null) {
			builder.setThumbnail(thumbnail);
		}
		if (description != null) {
			builder.setDescription(description + "\n\n"
				+ "Either press " + EmotesUtils.CHECK_MARK + " to validate, or " + EmotesUtils.CROSS_MARK + " to cancel.");
		} else {
			builder.setDescription("Either press " + EmotesUtils.CHECK_MARK + " to validate, or " + EmotesUtils.CROSS_MARK + " to cancel.");
		}
		if (image != null) {
			builder.setImage(image);
		}
		builder.setFooter("Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl());
		builder.setTimestamp(Instant.now());
		if (color != null) {
			builder.setColor(color);
		} else {
			builder.setColor(Command.colorCommand);
		}
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		return (mBuilder.build());
	}
	
	public static class Builder extends EmbedDialog.Builder<Builder, VerficationDialog> {

		private User author;
		private User self;
		private Color color;
		private String title;
		private String thumbnail;
		private String description;
		private String image;
		private Consumer<Message> action = m -> {
			m.clearReactions().queue();
		};
		private Consumer<Message> cancel = m -> {
			m.clearReactions().queue();
			MessageUtils.editEmbedMessage(m,
				title, null, self.getAvatarUrl(),
				null,
				null, null,
				"Your request has been canceled.",
				null,
				"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
				(color != null) ? color : Command.colorCommand);
		};
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
		public VerficationDialog build() {
			return (new VerficationDialog(waiter, users, roles, timeout, unit, author, self, color, title, thumbnail, description, image, action, cancel, timeoutAction));
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
		
		public Builder setValidateAction(Consumer<Message> action) {
			this.action = action;
			return (this);
		}
		
		public Builder setCancelAction(Consumer<Message> cancel) {
			this.cancel = cancel;
			return (this);
		}
		
		public Builder setTimeoutAction(Consumer<Message> timeoutAction) {
			this.timeoutAction = timeoutAction;
			return (this);
		}
		
	}
	
}
