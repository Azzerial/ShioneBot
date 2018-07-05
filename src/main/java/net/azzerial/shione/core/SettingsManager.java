package net.azzerial.shione.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game.GameType;

public class SettingsManager {

	private static SettingsManager instance;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Settings settings;
	private final Path configFile = new File(".").toPath().resolve("Config.json");

	public SettingsManager() {
		if (!configFile.toFile().exists()) {
			// Create new config file.
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: Entering default settings mode.");
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: You will have to edit the generated Config.json with your login information.");
			this.settings = getDefaultSettings();
			saveSettings();
			System.exit(Shione.NEWLY_CREATED_FILE);
		}
		// Load existing config file.
		System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: Loading existing Config.json settings.");
		loadSettings();
	}
	
	public static SettingsManager getInstance() {
		if (instance == null) {
			instance = new SettingsManager();
		}
		return (instance);
	}
	
	public Settings getSettings() {
		return (settings);
	}
	
	private void loadSettings() {
		try {
			BufferedReader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8);
			this.settings = gson.fromJson(reader, Settings.class);
			reader.close();
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: Config.json settings successfully loaded.");
		} catch (IOException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: Error loading Config.json settings!");
			e.printStackTrace();
		}
	}
	
	private void saveSettings() {
		String json = gson.toJson(this.settings);

		try {
			BufferedWriter writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8);
			writer.append(json);
			writer.close();
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: The settings were correctly saved into Config.json!");
		} catch (IOException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: I failed to save the settings into Config.json!");
			e.printStackTrace();
		}
	}
	
	private Settings getDefaultSettings() {
		Settings new_settings = new Settings();
		
		new_settings.setBotToken("");
		new_settings.setSoundcloudClientId("");
		new_settings.setSoundcloudAppVersion("");
		new_settings.setAudioEnabled(false);
		new_settings.setAutoReconnected(false);
		new_settings.setGame(GameType.DEFAULT, ShioneInfo.BOT_NAME + " v" + ShioneInfo.BOT_VERSION + " - Created by " + ShioneInfo.BOT_AUTHOR);
		new_settings.setStatus(OnlineStatus.ONLINE);
		return (new_settings);
	}
}
