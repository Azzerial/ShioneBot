package net.azzerial.shione.database;

import net.azzerial.shione.utils.MiscUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class GuildsManager {

	public static final String ADD_GUILD = "ADD_GUILD";
	public static final String GET_GUILDS = "GET_GUILDS";
	public static final String REMOVE_GUILD = "REMOVE_GUILD";
	public static final String UPDATE_GUILD = "UPDATE_GUILD";

	private static GuildsManager guildsManager;
	private static ArrayList<Guilds> guilds;

	private GuildsManager() {
		guilds = new ArrayList<Guilds>();

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

				Guilds guild = new Guilds(id, prefix, admins, users, roles, softbans, mutedChannels);
				guilds.add(guild);
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

	public static boolean addGuild(Guilds guild) {
		if (guilds.contains(guild)) {
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
				return (guilds.add(guild));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean removeGuild(Guilds guild) {
		if (!guilds.contains(guild)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(REMOVE_GUILD);
			statement.setString(1, guild.getId());
			if (statement.executeUpdate() == 1) {
				return (guilds.remove(guild));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean updateGuildColumn(String id, String column, String value) {
		if (id == null || id.isEmpty()) {
			return (false);
		}
		Guilds guild = getGuild(id);
		if (guild == null || column == null || column.isEmpty()) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(UPDATE_GUILD);
			statement.setString(1, column);
			statement.setString(2, value);
			statement.setString(3, id);
			if (statement.executeUpdate() == 1) {
				return (updateGuildCache(id, column, value));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	private static boolean updateGuildCache(String id, String column, String value) {
		for (int i = 0; i < guilds.size(); i += 1) {
			if (guilds.get(i).getId().equals(id)) {
				switch (column) {
					case (Guilds.ADMINS):
						guilds.get(i).setAdminsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.CHANNELS):
						guilds.get(i).setMutedChannelsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.ID):
						guilds.get(i).setId(value);
						return (true);
					case (Guilds.PREFIX):
						guilds.get(i).setPrefix(value);
						return (true);
					case (Guilds.ROLES):
						guilds.get(i).setFreeRolesId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.SOFTBANS):
						guilds.get(i).setSoftbannedUsersId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Guilds.USERS):
						guilds.get(i).setUsersId(MiscUtil.stringToList(value, ","));
						return (true);
				}
				return (false);
			}
		}
		return (false);
	}

	public static Guilds getGuild(String id) {
		for (int i = 0; i < guilds.size(); i += 1) {
			if (guilds.get(i).getId().equals(id)) {
				return (guilds.get(i));
			}
		}
		return (null);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Guilds> getGuilds() {
		return ((ArrayList<Guilds>) guilds.clone());
	}

}
