package net.azzerial.shione.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.azzerial.shione.core.Shione;
import net.azzerial.shione.core.ShioneInfo;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Role;

public class Server {
	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// Paths
	private Path adminFile;
	private Path channelsFile;
	private Path settingsFile;
	
	// Variables
	private List<String> admins;
	private List<TextChannel> allowedChannels;
	private String guildId;
	private List<Role> freeRoles;
	private String prefix;
//	private List<String> scUsers;
//	private List<String> bannedUsers;

	public Server(String guildId) {
		this.guildId = guildId;
		this.adminFile = new File("./Guilds/" + guildId).toPath().resolve("Admins.json");
		this.channelsFile = new File("./Guilds/" + guildId).toPath().resolve("Channels.json");
		this.settingsFile = new File("./Guilds/" + guildId).toPath().resolve("Settings.json");
		
		if (!settingsFile.toFile().exists()) {
			// Set the administrators list
			this.admins = new ArrayList<String>();
			this.admins.add(Shione.getAPI().getGuildById(guildId).getOwner().getUser().getId());
			
			// Set the bot prefix to the default one
			this.prefix = ShioneInfo.PREFIX;
			
			// Set the list of channels in which users can send commands to Shione
			this.allowedChannels = new ArrayList<TextChannel>();
			this.allowedChannels.addAll(Shione.getAPI().getGuildById(guildId).getTextChannels());
			
			// Set the list of free roles users can get by typing the iam (role) commands
			this.freeRoles = new ArrayList<Role>();
			
			// Save the variables values into files
			saveServerData();
			return;
		}
		// Load the variables values from files
		loadServerData();
	}
	
	// --- Loaders/Savers ---
	
	public void loadServerData() {
		loadAdmin();
//		loadChannels();
//		loadSettings();
	}
	
	public void loadAdmin() {		
		try {
			BufferedReader reader = Files.newBufferedReader(adminFile, StandardCharsets.UTF_8);
			this.admins = gson.fromJson(reader, new TypeToken<ArrayList<String>>(){}.getType());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadChannels() {
		try {
			BufferedReader reader = Files.newBufferedReader(channelsFile, StandardCharsets.UTF_8);
			this.allowedChannels = gson.fromJson(reader, new TypeToken<ArrayList<TextChannel>>(){}.getType());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSettings() {
		try {
			BufferedReader reader = Files.newBufferedReader(settingsFile, StandardCharsets.UTF_8);
			this.prefix = gson.fromJson(reader, new TypeToken<String>(){}.getType());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveServerData() {
		saveAdmin();
//		saveChannels();
//		saveSettings();
	}
	
	public void saveAdmin() {
		String json = gson.toJson(this.admins);

		try {
			BufferedWriter writer = Files.newBufferedWriter(adminFile, StandardCharsets.UTF_8);
			writer.append(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveChannels() {
		String json = gson.toJson(this.allowedChannels);
		try {
			BufferedWriter writer = Files.newBufferedWriter(channelsFile, StandardCharsets.UTF_8);
			writer.append(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSettings() {
		String json = gson.toJson(this.prefix);
		try {
			BufferedWriter writer = Files.newBufferedWriter(settingsFile, StandardCharsets.UTF_8);
			writer.append(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// --- Getters ---

	public List<String> getAdmins() {
		return (admins);
	}

	public String getGuildId() {
		return (guildId);
	}
	
	public String getPrefix() {
		return (prefix);
	}
	
	public List<TextChannel> getAllowedChannels() {
		return (allowedChannels);
	}
	
	public List<Role> getFreeRoles() {
		return (freeRoles);
	}
	
	// --- Setters ---
	
	public boolean addAdmin(String userId) {
		if (admins.contains(userId)) {
			return (false);
		}
		this.admins.add(userId);
		saveServerData();
		return (true);
	}
	
	public boolean removeAdmin(String userId) {
		if (Shione.getAPI().getGuildById(guildId).getOwner().getUser().getId().equals(userId)) {
			return (false);
		}
		if (!admins.contains(userId)) {
			return (false);
		}
		this.admins.remove(userId);
		saveServerData();
		return (true);
	}
	
	// --- Checkers ---

	public boolean isAdmin(User user) {
		if (Shione.getPermissions().isOp(user)
		|| admins.contains(user.getId())) {
			return (true);
		}
		return (false);
	}
	
	public boolean isPrefix(String str) {
		if (str.equals(prefix)
		|| str.equals(ShioneInfo.PREFIX)) {
			return (true);
		}
		return (false);
	}
	
}
