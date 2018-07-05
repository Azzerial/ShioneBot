package net.azzerial.shione.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.dv8tion.jda.core.entities.User;

public class Permissions {
	
	public static final String OP_REQUIRED_MESSAGE = "You do not have the permission to run this commands. (Op required)";
	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Path opsFile = new File(".").toPath().resolve("Ops.json");
	private static Permissions instance;
	private ArrayList<String> ops;

	private Permissions() {
		if (!opsFile.toFile().exists()) {
			// Create new ops file.
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: Entering default settings mode.");
			System.out.println(ShioneInfo.getTime() + "[Core/SettingsManager]: You will have to edit the generated Config.json with your login information.");
			this.ops = new ArrayList<String>();
			this.ops.add(ShioneInfo.BOT_AUTHOR_ID);
			saveOps();
			return;
		}
		// Load existing ops file.
		System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: Loading existing Ops.json.");
		refreshOps();
	}
	
	public void refreshOps() {
		try {
			BufferedReader reader = Files.newBufferedReader(opsFile, StandardCharsets.UTF_8);
			this.ops = gson.fromJson(reader, new TypeToken<ArrayList<String>>(){}.getType());
			reader.close();
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: Ops.json successfully loaded.");
		} catch (IOException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: Error loading Ops.json!");
			e.printStackTrace();
		}
	}
	
	public void saveOps() {
		String json = gson.toJson(this.ops);

		try {
			BufferedWriter writer = Files.newBufferedWriter(opsFile, StandardCharsets.UTF_8);
			writer.append(json);
			writer.close();
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: The Ops list has been correctly saved into Ops.json!");
		} catch (IOException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: I failed to save the Ops into Ops.json!");
			e.printStackTrace();
		}
	}

	public static Permissions getInstance() {
		if (instance == null) {
			instance = new Permissions();
		}
		return (instance);
	}

	public ArrayList<String> getOps() {
		return (this.ops);
	}
	
	public boolean addOps(String userId) {
		if (this.ops.contains(userId)) {
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: " + Shione.getAPI().getUserById(userId).getName() + " is already Op!");
			return (false);
		}
		this.ops.add(userId);
		saveOps();
		System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: " + Shione.getAPI().getUserById(userId).getName() + " has been added to the Ops list!");
		return (true);
	}
	
	public boolean removeOps(String userId) {
		if (!this.ops.contains(userId)) {
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: " + Shione.getAPI().getUserById(userId).getName() + " isn't Op!");
			return (false);
		}
		if (userId.equals(ShioneInfo.BOT_AUTHOR_ID)) {
			System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: You can't remove the bot's author from the Ops list!");
			return (false);
		}
		this.ops.remove(userId);
		saveOps();
		System.out.println(ShioneInfo.getTime() + "[Core/Permissions]: " + Shione.getAPI().getUserById(userId).getName() + " has been removed from the Ops list!");
		return (true);
	}
	
	public boolean isOp(User user) {
		return (this.ops.contains(user.getId()));
	}
}
