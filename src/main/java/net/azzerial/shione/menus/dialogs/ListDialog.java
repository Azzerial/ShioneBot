package net.azzerial.shione.menus.dialogs;

import net.azzerial.shione.menus.EmbedMenu;
import net.azzerial.shione.menus.EventWaiter;
import net.azzerial.shione.utils.EmoteUtil;
import net.azzerial.shione.utils.MiscUtil;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.RestAction;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ListDialog extends EmbedMenu {

	/*
	- set looping
	- show Key Number (1, then 2, ...)
	- show Page Number
	- show Total Page Amount
	- set Row Count (int 5 -> 20) #RowMin=5 #RowMax=20
	 */

	private final User author;
	private final User self;
	private final Color color;
	private final String title;
	private final String link;
	private final String thumbnail;
	private final String description;
	private final String emptyItemsListDescription;
	private final String image;
	private final String codeblockLanguage;
	private final List<String> items;
	private final int pageAmount;
	private final int itemsPerPage;
	private final int pageSkipAmount;
	private final boolean showItemNumber;
	private final boolean showPageNumber;
	private final boolean enablePageSkip;
	private final Consumer<Message> timeoutAction;

	ListDialog(EventWaiter waiter, Set<User> users, Set<Role> roles, long timeout, TimeUnit unit,
		   User author, User self, Color color, String title, String link, String thumbnail, String description, String emptyItemsListDescription, String image, String codeblockLanguage,
		   List<String> items, int itemsPerPage, int pageSkipAmount, boolean showItemNumber, boolean showPageNumber, boolean enablePageSkip, boolean orderItems,
		   Consumer<Message> timeoutAction) {
		super(waiter, users, roles, timeout, unit);
		this.author = author;
		this.self = self;
		this.color = color;
		this.title = title;
		this.link = link;
		this.thumbnail = thumbnail;
		this.description = description;
		this.emptyItemsListDescription = emptyItemsListDescription;
		this.image = image;
		this.codeblockLanguage = codeblockLanguage;
		this.items = items;
		this.pageAmount = (int) Math.ceil((float) items.size() / itemsPerPage);
		this.itemsPerPage = itemsPerPage;
		this.pageSkipAmount = pageSkipAmount;
		this.showItemNumber = showItemNumber;
		this.showPageNumber = showPageNumber;
		this.enablePageSkip = enablePageSkip;
		this.timeoutAction = timeoutAction;

		if (orderItems) {
			Collections.sort(this.items, new MiscUtil.sortIgnoreCase());
		}
	}

	@Override
	public void display(MessageChannel channel) {
		initializeOnPage(channel, 1);
	}

	@Override
	public void update(Message message) {
		initializeOnPage(message, 1);
	}

	public void initializeOnPage(MessageChannel channel, int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > pageAmount) {
			pageNumber = pageAmount;
		}
		initialize(channel.sendMessage(getMessage(pageNumber)), pageNumber);
	}

	public void initializeOnPage(Message message, int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > pageAmount) {
			pageNumber = pageAmount;
		}
		initialize(message.editMessage(getMessage(pageNumber)), pageNumber);
	}

	private void initialize(RestAction<Message> restAction, int pageNumber) {
		List<String> emotes = new ArrayList<String>();
		if (pageAmount > 1) {
			if (enablePageSkip) {
				emotes.add(EmoteUtil.FAST_REVERSE);
			}
			emotes.add(EmoteUtil.REVERSE);
			emotes.add(EmoteUtil.PLAY);
			if (enablePageSkip) {
				emotes.add(EmoteUtil.FAST_FORWARD);
			}
		}
		emotes.add(EmoteUtil.HEAVY_MULTIPLICATION_X);

		restAction.queue(m -> {
			for (int i = 0; i < emotes.size(); i += 1) {
				if (i + 1 < emotes.size()) {
					m.addReaction(emotes.get(i)).queue();
				} else {
					m.addReaction(emotes.get(i)).queue(a -> createEvent(m, emotes, pageNumber));
				}
			}
		});
	}
	
	private void createEvent(Message message, List<String> emotes, int pageNumber) {
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
				event.getReaction().removeReaction(author).queue();

				int newPageNumber = pageNumber;
				switch (event.getReaction().getReactionEmote().getName()) {
					case (EmoteUtil.FAST_REVERSE):
						newPageNumber -= pageSkipAmount;
						break;
					case (EmoteUtil.REVERSE):
						newPageNumber -= 1;
						break;
					case (EmoteUtil.PLAY):
						newPageNumber += 1;
						break;
					case (EmoteUtil.FAST_FORWARD):
						newPageNumber += pageSkipAmount;
						break;
					case (EmoteUtil.HEAVY_MULTIPLICATION_X):
						message.delete().queue();
						return;
				}

				initializeOnPage(message, newPageNumber);
			},
			timeout, unit, () -> timeoutAction.accept(message));
	}
	
	private Message getMessage(int pageNumber) {
		EmbedBuilder builder = new EmbedBuilder();
		boolean noItems = items == null || items.isEmpty() || items.size() == 0;

		String rows = "";
		if (!noItems) {
			int itemsStart = (pageNumber - 1) * itemsPerPage;
			int itemsEnd = (items.size() < pageNumber * itemsPerPage ? items.size() : pageNumber * itemsPerPage);

			rows = "```" + codeblockLanguage + "\n";
			for (int i = itemsStart; i < itemsEnd; i += 1) {
				rows += (showItemNumber ? (i + 1) + ". " : "");
				rows += items.get(i) + "\n";
			}
			rows += "```";
			if (showPageNumber) {
				rows += "\nPage `" + pageNumber + "/" + pageAmount + "`";
			}
		}

		if (title != null && link != null) {
			builder.setAuthor(title, link, self.getAvatarUrl());
		} else if (title != null) {
			builder.setAuthor(title, null, self.getAvatarUrl());
		}
		if (thumbnail != null) {
			builder.setThumbnail(thumbnail);
		}
		if (description != null && !description.isEmpty() && noItems) {
			builder.setDescription(description + "\n\n" + emptyItemsListDescription);
		} else if (noItems) {
			builder.setDescription(emptyItemsListDescription);
		} else if (description != null && !description.isEmpty()) {
			builder.setDescription(description + "\n" + rows);
		} else {
			builder.setDescription(rows);
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

	public static class Builder extends EmbedMenu.Builder<Builder, ListDialog> {

		private User author;
		private User self;
		private Color color;
		private String title;
		private String link;
		private String thumbnail;
		private String description;
		private String emptyItemsListDescription = "The list is empty.";
		private String image;
		private String codeblockLanguage = "";
		private List<String> items = new ArrayList<String>();
		private int itemsPerPage = 10;
		private int pageSkipAmount = 5;
		private boolean showItemNumber = false;
		private boolean showPageNumber = true;
		private boolean orderItems = false;
		private boolean enablePageSkip = true;
		private Consumer<Message> timeoutAction = m -> {
			m.clearReactions().queue();
		};
		
		@Override
		public ListDialog build() {
			return (new ListDialog(waiter, users, roles, timeout, unit, author, self, color, title, link, thumbnail, description, emptyItemsListDescription, image, codeblockLanguage, items, itemsPerPage, pageSkipAmount, showItemNumber, showPageNumber, enablePageSkip, orderItems, timeoutAction));
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

		public Builder setEmptyItemsListDescription(String emptyItemsListDescription) {
			this.emptyItemsListDescription = emptyItemsListDescription;
			return (this);
		}

		public Builder setImage(String image) {
			this.image = image;
			return (this);
		}

		public Builder setCodeblockLanguage(String codeblockLanguage) {
			this.codeblockLanguage = codeblockLanguage;
			return (this);
		}
		
		public Builder addItem(String item) {
			this.items.add(item);
			return (this);
		}

		public Builder addItems(String... items) {
			for (String item : items) {
				this.items.add(item);
			}
			return (this);
		}

		public Builder setItems(List<String> items) {
			this.items.clear();
			this.items = items;
			return (this);
		}

		public Builder setItemsPerPage(int amount) {
			this.itemsPerPage = amount;
			return (this);
		}

		public Builder setPageSkipAmount(int amount) {
			this.pageSkipAmount = amount;
			return (this);
		}

		public Builder showItemNumber(boolean showItemNumber) {
			this.showItemNumber = showItemNumber;
			return (this);
		}

		public Builder showPageNumber(boolean showPageNumber) {
			this.showPageNumber = showPageNumber;
			return (this);
		}

		public Builder enablePageSkip(boolean enablePageSkip) {
			this.enablePageSkip = enablePageSkip;
			return (this);
		}

		public Builder orderItems(boolean orderItems) {
			this.orderItems = orderItems;
			return (this);
		}

		public Builder setTimeoutAction(Consumer<Message> timeoutAction) {
			this.timeoutAction = timeoutAction;
			return (this);
		}
		
	}
	
}
