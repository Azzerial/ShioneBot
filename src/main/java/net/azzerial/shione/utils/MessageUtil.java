package net.azzerial.shione.utils;

import java.awt.Color;
import java.time.Instant;
import java.util.function.Consumer;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class MessageUtil {

	// Message Senders
	
	public static void sendRawMessage(MessageChannel channel, String message) {
		MessageBuilder builder = new MessageBuilder();
		builder.append(message);
		Message msg = builder.build();
		channel.sendMessage(msg).queue();
	}
	
	public static void sendRawMessage(MessageChannel channel, String message, Consumer<? super Message> rest) {
		MessageBuilder builder = new MessageBuilder();
		builder.append(message);
		Message msg = builder.build();
		channel.sendMessage(msg).queue(rest);
	}
	
	public static void sendCodeBlockMessage(MessageChannel channel, String message, String code) {
		MessageBuilder builder = new MessageBuilder();
		builder.appendCodeBlock(message, code);
		Message msg = builder.build();
		channel.sendMessage(msg).queue();
	}
	
	public static void sendCodeBlockMessage(MessageChannel channel, String message, String code, Consumer<? super Message> rest) {
		MessageBuilder builder = new MessageBuilder();
		builder.appendCodeBlock(message, code);
		Message msg = builder.build();
		channel.sendMessage(msg).queue(rest);
	}
	
	public static void sendEmbedMessage(MessageChannel channel, User author, User self,
			String title, String description, Color color) {
		sendEmbedMessage(channel,
			title, null, self.getAvatarUrl(),
			null,
			null, null,
			description,
			null,
			"Requested by " + author.getName() + "#" + author.getDiscriminator(), author.getAvatarUrl(),
			color);
	}
	
	public static void sendEmbedMessage(MessageChannel channel,
			String author, String authorUrl, String authorIconUrl,
			String thumbnailUrl,
			String title, String titleUrl,
			String description,
			String imageUrl,
			String footerText, String footerIconUrl, 
			Color color) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(author, authorUrl, authorIconUrl);
		builder.setThumbnail(thumbnailUrl);
		builder.setTitle(title, titleUrl);
		builder.setDescription(description);
		builder.setImage(imageUrl);
		builder.setFooter(footerText, footerIconUrl);
		builder.setTimestamp(Instant.now());
		builder.setColor(color);
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		Message msg = mBuilder.build();
		channel.sendMessage(msg).queue();
	}
	
	public static void sendEmbedMessage(MessageChannel channel,
			String author, String authorUrl, String authorIconUrl,
			String thumbnailUrl,
			String title, String titleUrl,
			String description,
			String imageUrl,
			String footerText, String footerIconUrl, 
			Color color,
			Consumer<? super Message> rest) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(author, authorUrl, authorIconUrl);
		builder.setThumbnail(thumbnailUrl);
		builder.setTitle(title, titleUrl);
		builder.setDescription(description);
		builder.setImage(imageUrl);
		builder.setFooter(footerText, footerIconUrl);
		builder.setTimestamp(Instant.now());
		builder.setColor(color);
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		Message msg = mBuilder.build();
		channel.sendMessage(msg).queue(rest);
	}
	
	// Message Editors
	
	public static void editEmbedMessage (Message message, User author, User self,
			String title, String description, Color color) {
		editEmbedMessage(message,
			title, null, self.getAvatarUrl(),
			null,
			null, null,
			description,
			null,
			"Requested by " + author.getName() + "#" + author.getDiscriminator() + ".", author.getAvatarUrl(),
			color);
	}
	
	public static void editEmbedMessage(Message message,
			String author, String authorUrl, String authorIconUrl,
			String thumbnailUrl,
			String title, String titleUrl,
			String description,
			String imageUrl,
			String footerText, String footerIconUrl, 
			Color color) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(author, authorUrl, authorIconUrl);
		builder.setThumbnail(thumbnailUrl);
		builder.setTitle(title, titleUrl);
		builder.setDescription(description);
		builder.setImage(imageUrl);
		builder.setFooter(footerText, footerIconUrl);
		builder.setTimestamp(Instant.now());
		builder.setColor(color);
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		Message msg = mBuilder.build();
		message.editMessage(msg).queue();
	}
	
	public static void editEmbedMessage(Message message,
			String author, String authorUrl, String authorIconUrl,
			String thumbnailUrl,
			String title, String titleUrl,
			String description,
			String imageUrl,
			String footerText, String footerIconUrl, 
			Color color,
			Consumer<? super Message> rest) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setAuthor(author, authorUrl, authorIconUrl);
		builder.setThumbnail(thumbnailUrl);
		builder.setTitle(title, titleUrl);
		builder.setDescription(description);
		builder.setImage(imageUrl);
		builder.setFooter(footerText, footerIconUrl);
		builder.setTimestamp(Instant.now());
		builder.setColor(color);
		MessageEmbed embed = builder.build();
		MessageBuilder mBuilder = new MessageBuilder(embed);
		Message msg = mBuilder.build();
		message.editMessage(msg).queue(rest);
	}
	
	// Message Removers
	// Message Customs
	
}
