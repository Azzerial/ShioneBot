package net.azzerial.shione.database.entities;

import net.azzerial.sc2d.entities.Artist;
import net.azzerial.shione.database.UsersManager;
import net.azzerial.shione.utils.MiscUtil;
import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class Users {

	public static final String ID = "id";
	public static final String CURRENCY = "currency";
	public static final String GUILDS = "guilds";
	public static final String REQUESTS = "requests";
	public static final String SOFTBANS = "softbans";
	public static final String SOUNDCLOUD_ID = "soundcloud_id";
	public static final String SOUNDCLOUD_PERMALINK = "soundcloud_permalink";

	private String id;
	private String soundcloudId;
	private String soundcloudPermalink;
	private long currency;
	private long requests;
	private List<String> guildsId;
	private List<String> softbansGuildsId;

	public Users(String id, String soundcloudId, String soundcloudPermalink,
		     long currency, long requests, String guilds, String softbans) {
		this.id = id;
		this.soundcloudId = soundcloudId;
		this.soundcloudPermalink = soundcloudPermalink;
		this.currency = currency;
		this.requests = requests;
		this.guildsId = MiscUtil.stringToList(guilds, ",");
		this.softbansGuildsId = MiscUtil.stringToList(softbans, ",");
	}

	public boolean addSoundCloudAccount(Artist artist) {
		if (isRegistered()) {
			return (false);
		}

		if (UsersManager.updateUserColumn(id, SOUNDCLOUD_ID, artist.getId())
		&& UsersManager.updateUserColumn(id, SOUNDCLOUD_PERMALINK, artist.getPermalink())) {
			return (true);
		}
		return (false);
	}

	public boolean removeSoundCloudAccount() {
		if (!isRegistered()) {
			return (false);
		}

		if (UsersManager.updateUserColumn(id, SOUNDCLOUD_ID, "")
			&& UsersManager.updateUserColumn(id, SOUNDCLOUD_PERMALINK, "")) {
			return (true);
		}
		return (false);
	}

	public boolean isRegistered() {
		if (soundcloudId == null || soundcloudId.isEmpty()
		|| soundcloudPermalink == null || soundcloudPermalink.isEmpty()) {
			return (false);
		}
		return (true);
	}

	// --- Getters ---

	public long getCurrency() {
		return (currency);
	}

	public List<String> getGuildsId() {
		return (guildsId);
	}

	public String getId() {
		return (id);
	}

	public long getRequests() {
		return (requests);
	}

	public List<String> getSoftbansGuildsId() {
		return (softbansGuildsId);
	}

	public String getSoundcloudId() {
		return (soundcloudId);
	}

	public String getSoundcloudPermalink() {
		return (soundcloudPermalink);
	}

	// --- Setters ---

	public void setCurrency(long currency) {
		this.currency = currency;
	}

	public void setGuildsId(List<String> guildsId) {
		this.guildsId = guildsId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRequests(long requests) {
		this.requests = requests;
	}

	public void setSoftbansGuildsId(List<String> softbansGuildsId) {
		this.softbansGuildsId = softbansGuildsId;
	}

	public void setSoundcloudId(String soundcloudId) {
		this.soundcloudId = soundcloudId;
	}

	public void setSoundcloudPermalink(String soundcloudPermalink) {
		this.soundcloudPermalink = soundcloudPermalink;
	}

}
