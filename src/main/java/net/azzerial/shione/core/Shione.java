package net.azzerial.shione.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Executors;

import javax.security.auth.login.LoginException;

import net.azzerial.sc2d.builders.SC2DBuilder;
import net.azzerial.sc2d.core.SC2D;
import net.azzerial.shione.commands.HelpCommand;
import net.azzerial.shione.commands.bot.EvalCommand;
import net.azzerial.shione.commands.bot.OpCommand;
import net.azzerial.shione.commands.bot.ShutdownCommand;
import net.azzerial.shione.commands.bot.TestCommand;
import net.azzerial.shione.commands.browse.WhoisCommand;
import net.azzerial.shione.commands.user.RegisterCommand;
import net.azzerial.shione.commands.guild.AdminCommand;
import net.azzerial.shione.commands.guild.GuildCommand;
import net.azzerial.shione.database.Database;
import net.azzerial.shione.database.Permissions;
import net.azzerial.shione.listeners.GuildEvent;
import net.azzerial.shione.menus.EventWaiter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Shione {
	
	// Non error exit codes.
	public static final int NORMAL_SHUTDOWN = 0;
	public static final int RESTART_SHUTDOWN = 1;
	public static final int NEWLY_CREATED_FILE = 2;
	
	// Error exit codes.
	public static final int INVALID_INFORMATION_PROVIDED = 10;
	public static final int BAD_TOKEN_PROVIDED = 11;
	public static final int UNABLE_TO_CONNECT_TO_DISCORD = 12;
	public static final int UNSUPPORTED_SYSTEM = 13;
	
	private static JDA api;
	private static SC2D scapi;
	private static EventWaiter waiter;

	public static void main(String[] args) {
		System.out.println(ShioneInfo.console_header);
		
		if (System.getProperty("file.encoding").equals("UTF-8")) {
			setupBot();
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: Hurray! Looks like we're online captain!");
		} else {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: This system is actually running in: \"" + System.getProperty("file.encoding") + "\" mode.");
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: We are not running in UTF-8 mode! This is a problem!");
			relaunchInUTF8();
		}
	}
	
	public static JDA getAPI() {
		return (api);
	}
	
	public static SC2D getSC2DAPI() {
		return (scapi);
	}
	
	public static EventWaiter getEventWaiter() {
		return (waiter);
	}
	
	private static void setupBot() {
		try {
			Settings settings = SettingsManager.getInstance().getSettings();
			Database.getInstance();
			
			// Start the JDA api.
			JDABuilder jda_builder = new JDABuilder(AccountType.BOT);
			jda_builder.setToken(settings.getBotToken());
			jda_builder.setAudioEnabled(settings.getAudioEnabled());
			jda_builder.setAutoReconnect(settings.getAutoReconnected());
			jda_builder.setGame(settings.getGame());
			jda_builder.setStatus(settings.getStatus());
			
			// Register the commands.
			HelpCommand command = new HelpCommand();
			jda_builder.addEventListener(command.registerCommand(command));
			jda_builder.addEventListener(command.registerCommand(new AdminCommand()));
			jda_builder.addEventListener(command.registerCommand(new EvalCommand()));
			jda_builder.addEventListener(command.registerCommand(new GuildCommand()));
			jda_builder.addEventListener(command.registerCommand(new OpCommand()));
			jda_builder.addEventListener(command.registerCommand(new RegisterCommand()));
			jda_builder.addEventListener(command.registerCommand(new ShutdownCommand()));
			jda_builder.addEventListener(command.registerCommand(new TestCommand()));
			jda_builder.addEventListener(command.registerCommand(new WhoisCommand()));
			
			// Login to Discord.
			api = jda_builder.buildBlocking();

			// Add event listeners.
			waiter = new EventWaiter(Executors.newSingleThreadScheduledExecutor());
			api.addEventListener(waiter);
			api.addEventListener(new GuildEvent());
			
			// Start the SC2D api.
			SC2DBuilder sc2d_builder = new SC2DBuilder(settings);
			scapi = sc2d_builder.build();
			
			// Set the Ops list.
			Permissions.setupPermissions();
			for (String op : Permissions.getOps()) {
				System.out.println("Op: " + op);
			}

		} catch (IllegalArgumentException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: Some of the needed informations went missing! Please provide valid informations in the Config.json file.");
			e.printStackTrace();
			System.exit(INVALID_INFORMATION_PROVIDED);
		} catch (LoginException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: The provided botToken was incorrect! Please provide a valid botToken in the Config.json file.");
			e.printStackTrace();
			System.exit(BAD_TOKEN_PROVIDED);
		} catch (InterruptedException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: Oh no! Our login thread got interruped!");
			e.printStackTrace();
			System.exit(UNABLE_TO_CONNECT_TO_DISCORD);
		}
	}
	
	public static File getJarFile() {
		String path = Shione.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String decodedPath = null;
		
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (!decodedPath.endsWith(".jar")) {
			return (new File(ShioneInfo.JAR_FILE_NAME));
		}
		return (new File(decodedPath));
	}
	
	private static void relaunchInUTF8() {
		String[] command = new String[] {"java", "-Dfile.encoding=UTF-8", "-jar", Shione.getJarFile().getAbsolutePath()};
		
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.inheritIO();
		try {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: Relaunching in \"UTF-8\" mode using: -Dfile.encoding=UTF-8\n");
			Process process = processBuilder.start();
			process.waitFor();
			System.exit(process.exitValue());
		} catch (IOException | InterruptedException e) {
			System.out.println(ShioneInfo.getTime() + "[Core/Shione]: Failed to relaunch in \"UTF-8\" mode using: -Dfile.encoding=UTF-8");
			e.printStackTrace();
			System.exit(UNSUPPORTED_SYSTEM);
		}
	}
	
}
