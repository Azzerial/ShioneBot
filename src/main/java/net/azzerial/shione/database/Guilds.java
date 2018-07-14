package net.azzerial.shione.database;

import net.azzerial.shione.utils.MiscUtil;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Guilds {

	public static final String ID = "id";
	public static final String PREFIX = "prefix";
	public static final String ADMINS = "admins";
	public static final String USERS = "users";
	public static final String ROLES = "roles";
	public static final String SOFTBANS = "softbans";
	public static final String CHANNELS = "muted_channels";

	private String id;
	private String prefix;
	private List<String> adminsId;
	private List<String> usersId;
	private List<String> freeRolesId;
	private List<String> softbannedUsersId;
	private List<String> mutedChannelsId;

	public Guilds(String id, String prefix, String admins, String users,
		      String roles, String softbans, String mutedChannels) {
		this.id = id;
		this.prefix = prefix;
		this.adminsId = MiscUtil.stringToList(admins, ",");
		this.usersId = MiscUtil.stringToList(users, ",");
		this.freeRolesId = MiscUtil.stringToList(roles, ",");
		this.softbannedUsersId = MiscUtil.stringToList(softbans, ",");
		this.mutedChannelsId = MiscUtil.stringToList(mutedChannels, ",");
	}

	public String getId() {
		return (id);
	}

	public String getPrefix() {
		return (prefix);
	}

	public List<String> getAdminsId() {
		return (adminsId);
	}

	public List<String> getUsersId() {
		return (usersId);
	}

	public List<String> getFreeRolesId() {
		return (freeRolesId);
	}

	public List<String> getSoftbannedUsersId() {
		return (softbannedUsersId);
	}

	public List<String> getMutedChannelsId() {
		return (mutedChannelsId);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setAdminsId(List<String> adminsId) {
		this.adminsId = adminsId;
	}

	public void setUsersId(List<String> usersId) {
		this.usersId = usersId;
	}

	public void setFreeRolesId(List<String> freeRolesId) {
		this.freeRolesId = freeRolesId;
	}

	public void setSoftbannedUsersId(List<String> softbannedUsersId) {
		this.softbannedUsersId = softbannedUsersId;
	}

	public void setMutedChannelsId(List<String> mutedChannelsId) {
		this.mutedChannelsId = mutedChannelsId;
	}
}
