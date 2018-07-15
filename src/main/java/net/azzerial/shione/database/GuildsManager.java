package net.azzerial.shione.database;

import net.azzerial.shione.database.entities.Guilds;
import net.azzerial.shione.utils.MiscUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuildsManager {

	public static final String ADD_GUILD = "ADD_GUILD";
	public static final String GET_GUILDS = "GET_GUILDS";
	public static final String REMOVE_GUILD = "REMOVE_GUILD";
	public static final String UPDATE_GUILD_PREFIX = "UPDATE_GUILD_PREFIX";
	public static final String UPDATE_GUILD_ADMINS = "UPDATE_GUILD_ADMINS";
	public static final String UPDATE_GUILD_USERS = "UPDATE_GUILD_USERS";
	public static final String UPDATE_GUILD_ROLES = "UPDATE_GUILD_ROLES";
	public static final String UPDATE_GUILD_SOFTBANS = "UPDATE_GUILD_SOFTBANS";
	public static final String UPDATE_GUILD_CHANNELS = "UPDATE_GUILD_CHANNELS";

	private static GuildsManager guildsManager;
	private static ArrayList<Guilds> guildsCache;

	private GuildsManager() {
		guildsCache = new ArrayList<Guilds>();

		try {
			ResultSet guildsSet = Database.getInstance().getStatement(GET_GUILDS).executeQuery();
			while (guildsSet.next()) {
				String id = guildsSet.getString(Guilds.ID);
				String prefix = guildsSet.getString(Guilds.PREFIX);
				String admins = guildsSet.getString(Guilds.ADMINS);
				String users = guildsSet.getString(Guilds.USERS);
				String roles = guildsSet.getString(Guilds.ROLES);
				String softbans = guildsSet.getString(Guilds.SOFTBANS);
				String mutedChannels = guildsSet.getString(Guilds.CHANNELS);

				guildsCache.add(new Guilds(id, prefix, admins, users, roles, softbans, mutedChannels));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void loadGuilds() {
		if (guildsManager == null) {
			guildsManager = new GuildsManager();
		}
	}

	public static GuildsManager getGuildsManager() {
		return (guildsManager);
	}

	public static boolean isGuildInDatabase(String id) {
		if (getGuild(id) != null) {
			return (true);
		}
		return (false);
	}

	public static boolean createNewDefaultGuild(String id, String ownerId) {
		return (addGuild(new Guilds(id, "", ownerId, "", "", "", "")));
	}

	public static boolean addGuild(Guilds guild) {
		if (guildsCache.contains(guild)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(ADD_GUILD);
			statement.setString(1, guild.getId());
			statement.setString(2, guild.getPrefix());
			statement.setString(3, MiscUtil.listToString(guild.getAdminsId(), ","));
			statement.setString(4, MiscUtil.listToString(guild.getUsersId(), ","));
			statement.setString(5, MiscUtil.listToString(guild.getFreeRolesId(), ","));
			statement.setString(6, MiscUtil.listToString(guild.getSoftbannedUsersId(), ","));
			statement.setString(7, MiscUtil.listToString(guild.getMutedChannelsId(), ","));

			if (statement.executeUpdate() == 1) {
				return (guildsCache.add(guild));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean removeGuild(Guilds guild) {
		if (!guildsCache.contains(guild)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(REMOVE_GUILD);
			statement.setString(1, guild.getId());
			if (statement.executeUpdate() == 1) {
				return (guildsCache.remove(guild));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean updateGuildColumn(String id, String column, String value) {
		if (id == null || id.isEmpty() || value == null) {
			return (false);
		}
		Guilds guild = getGuild(id);
		if (guild == null || column == null || column.isEmpty()) {
			return (false);
		}
		String statementName = "";
		switch (column) {
			case (Guilds.ADMINS):
				statementName = UPDATE_GUILD_ADMINS;
				break;
			case (Guilds.CHANNELS):
				statementName = UPDATE_GUILD_CHANNELS;
				break;
			case (Guilds.PREFIX):
				statementName = UPDATE_GUILD_PREFIX;
				break;
			case (Guilds.ROLES):
				statementName = UPDATE_GUILD_ROLES;
				break;
			case (Guilds.SOFTBANS):
				statementName = UPDATE_GUILD_SOFTBANS;
				break;
			case (Guilds.USERS):
				statementName = UPDATE_GUILD_USERS;
				break;
		}
		if (statementName.isEmpty()) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(statementName);
			statement.setString(1, value);
			statement.setString(2, id);
			if (statement.executeUpdate() == 1) {
				return (updateGuildCache(id, column, value));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	private static boolean updateGuildCache(String id, String column, String value) {
		for (int i = 0; i < guildsCache.size(); i += 1) {
			if (guildsCache.get(i).getId().equals(id)) {
				switch (column) {
					case (Guilds.ADMINS):
						guildsCache.get(i).setAdminsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.CHANNELS):
						guildsCache.get(i).setMutedChannelsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.PREFIX):
						guildsCache.get(i).setPrefix(value);
						return (true);
					case (Guilds.ROLES):
						guildsCache.get(i).setFreeRolesId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.SOFTBANS):
						guildsCache.get(i).setSoftbannedUsersId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.USERS):
						guildsCache.get(i).setUsersId(MiscUtil.stringToList(value, ","));
						return (true);
				}
				return (false);
			}
		}
		return (false);
	}

	public static Guilds getGuild(String id) {
		for (int i = 0; i < guildsCache.size(); i += 1) {
			if (guildsCache.get(i).getId().equals(id)) {
				return (guildsCache.get(i));
			}
		}
		return (null);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Guilds> getGuilds() {
		return ((ArrayList<Guilds>) guildsCache.clone());
	}

	public static String getEffectivePrefix(String guildId) {
		return (getGuild(guildId).getEffectivePrefix());
	}
}
