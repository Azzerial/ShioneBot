package net.azzerial.shione.database.entities;

import net.azzerial.shione.core.ShioneInfo;
import net.azzerial.shione.database.Database;
import net.azzerial.shione.database.GuildsManager;
import net.azzerial.shione.database.Permissions;
import net.azzerial.shione.utils.MiscUtil;
import net.dv8tion.jda.core.entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Guilds {

	public static final String ID = "id";
	public static final String ADMINS = "admins";
	public static final String PREFIX = "prefix";
	public static final String USERS = "users";
	public static final String ROLES = "roles";
	public static final String SOFTBANS = "softbans";
	public static final String CHANNELS = "muted_channels";

	private List<String> adminsId;
	private List<String> freeRolesId;
	private String id;
	private List<String> mutedChannelsId;
	private String prefix;
	private List<String> softbannedUsersId;
	private List<String> usersId;

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

	public boolean addAdmin(String userId) {
		if (adminsId.contains(userId)) {
			return (false);
		}

		String newAdminsList = MiscUtil.listToString(adminsId, ",");
		newAdminsList += "," + userId;
		return (GuildsManager.updateGuildColumn(id, ADMINS, newAdminsList));
	}

	public boolean removeAdmin(String userId) {
		if (!adminsId.contains(userId) || userId.equals(ShioneInfo.BOT_AUTHOR_ID)) {
			return (false);
		}

		adminsId.remove(userId);
		String newAdminsList = MiscUtil.listToString(adminsId, ",");
		return (GuildsManager.updateGuildColumn(id, ADMINS, newAdminsList));
	}

	public String getStringWithoutPrefix(String str) {
		if (str.startsWith(prefix)) {
			return (str.substring(prefix.length()));
		} else if (str.startsWith(ShioneInfo.PREFIX)) {
			return (str.substring(ShioneInfo.PREFIX.length()));
		}
		return (null);
	}

	public boolean isAdmin(User user) {
		if (Permissions.isOp(user)
			|| adminsId.contains(user.getId())) {
			return (true);
		}
		return (false);
	}

	public boolean isPrefix(String str) {
		if (str.startsWith(prefix)
			|| str.startsWith(ShioneInfo.PREFIX)) {
			return (true);
		}
		return (false);
	}


	// --- Getters ---

	public List<String> getAdminsId() {
		return (adminsId);
	}

	public List<String> getFreeRolesId() {
		return (freeRolesId);
	}

	public String getId() {
		return (id);
	}

	public List<String> getMutedChannelsId() {
		return (mutedChannelsId);
	}

	public String getPrefix() {
		return (prefix);
	}

	public List<String> getSoftbannedUsersId() {
		return (softbannedUsersId);
	}

	public List<String> getUsersId() {
		return (usersId);
	}

	// --- Setters ---

	public void setAdminsId(List<String> adminsId) {
		this.adminsId = adminsId;
	}

	public void setFreeRolesId(List<String> freeRolesId) {
		this.freeRolesId = freeRolesId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMutedChannelsId(List<String> mutedChannelsId) {
		this.mutedChannelsId = mutedChannelsId;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSoftbannedUsersId(List<String> softbannedUsersId) {
		this.softbannedUsersId = softbannedUsersId;
	}

	public void setUsersId(List<String> usersId) {
		this.usersId = usersId;
	}

}
