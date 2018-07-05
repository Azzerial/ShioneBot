package net.azzerial.shione.core;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;

public class Settings {

	private String botToken;
	private String soundcloudClientId;
	private String soundcloudAppVersion;
	private Boolean autoReconnect;
	private Boolean audioEnabled;
	@SuppressWarnings("unused")
	private final String game_types_list = "DEFAULT, LISTENING, STREAMING, WATCHING."; 
	private Game game;
	@SuppressWarnings("unused")
	private final String statuses_list = "ONLINE, IDLE, DO_NOT_DISTURB, INVISIBLE, OFFLINE.";
	private OnlineStatus status;
	
	public String getBotToken() {
		return (botToken);
	}
	
	public void setBotToken(String botToken) {
		this.botToken = botToken;
	}
	
	public String getSoundcloudClientId() {
		return (soundcloudClientId);
	}
	
	public void setSoundcloudClientId(String soundcloudClientId) {
		this.soundcloudClientId = soundcloudClientId;
	}
	
	public String getSoundcloudAppVersion() {
		return (soundcloudAppVersion);
	}
	
	public void setSoundcloudAppVersion(String soundcloudAppVersion) {
		this.soundcloudAppVersion = soundcloudAppVersion;
	}
	
	public Boolean getAutoReconnected() {
		return (autoReconnect);
	}
	
	public void setAutoReconnected(boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	
	public Boolean getAudioEnabled() {
		return (audioEnabled);
	}
	
	public void setAudioEnabled(boolean audioEnabled) {
		this.audioEnabled = audioEnabled;
	}
	
	public Game getGame() {
		return (game);
	}
	
	public void setGame(GameType type, String name) {
		this.game = Game.of(type, name);
	}
	
	public OnlineStatus getStatus() {
		return (status);
	}
	
	public void setStatus(OnlineStatus status) {
		this.status = status;
	}
	
}
