package net.azzerial.shione.listeners;

import java.awt.Color;
import java.io.File;
import java.util.TreeMap;

import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.entities.Server;
import net.azzerial.shione.utils.MessageUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildEvent extends ListenerAdapter {
	
	public static final String ADMIN_REQUIRED_MESSAGE = "You do not have the permission to run this command. (Admin required)";
	
	private static TreeMap<String, Server> guilds;

	public GuildEvent() {
		createGuildsFolder();
		
		guilds = new TreeMap<String, Server>();
		for (String guildId : new File("./Guilds").list()) {
			Server server = new Server(guildId);
			guilds.put(guildId, server);
		}
	}
	
	public void onGuildJoin(GuildJoinEvent event) {
		Guild guild = event.getGuild();
		
		System.out.println(ShioneInfo.getTime() + "[Listeners/GuildEvent]: New guild joined! (Name: " + guild.getName() + ", Id: " + guild.getId() + ")");
		MessageUtils.sendEmbedMessage(event.getGuild().getDefaultChannel(),
			"Hello, my name is Shione. I'm the bot sent by SoundCloud.", null, event.getJDA().getSelfUser().getAvatarUrl(),
			"https://78.media.tumblr.com/071f6f3075392792250f2a8400230618/tumblr_nfk0p6MKBd1saxdy4o1_500.gif",
			null, null,
			"**Type `./help` to view my commands.**",
			null,
			"Thanks for adding my bot!", event.getJDA().getUserById(ShioneInfo.BOT_AUTHOR_ID).getAvatarUrl(),
			new Color(153, 179, 255));
		createGuildsFolder();
		createGuildDatabase(event, guild);
	}
	
	public void onGuildLeave(GuildLeaveEvent event) {
		Guild guild = event.getGuild();
		
		System.out.println(ShioneInfo.getTime() + "[Listeners/GuildEvent]: I left a guild. (Name: " + guild.getName() + ", Id: " + guild.getId() + ")");
		guilds.remove(guild.getId());
	}
	
	private void createGuildsFolder() {
		File guildsDir = new File("./Guilds");
		
		if (!guildsDir.exists()) {
			guildsDir.mkdirs();
			System.out.println(ShioneInfo.getTime() + "[Listeners/GuildEvent]: Guilds folder created.");
		}
	}
	
	private void createGuildDatabase(GuildJoinEvent event, Guild guild) {
		File guildDir = new File("./Guilds/" + guild.getId());
		guildDir.mkdirs();

		Server server = new Server(guild.getId());
		guilds.put(guild.getId(), server);
	}
	
	public static Server getServer(String guildId) {
		return (guilds.get(guildId));
	}
}
