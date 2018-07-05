package net.azzerial.shione.entities;

public class Account {

	private final String discordId;
	private final String soundcloudId;
	
	public Account(String discordId, String soundcloudId) {
		this.discordId = discordId;
		this.soundcloudId = soundcloudId;
	}
	
	public String getDiscordId() {
		return (discordId);
	}
	
	public String getSoundCloudId() {
		return (soundcloudId);
	}
	
}
