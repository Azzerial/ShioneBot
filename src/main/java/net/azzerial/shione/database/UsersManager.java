package net.azzerial.shione.database;

import net.azzerial.sc2d.entities.Artist;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.database.entities.Users;
import net.azzerial.shione.utils.MiscUtil;
import net.dv8tion.jda.core.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersManager {

	public static final String ADD_USER = "ADD_USER";
	public static final String GET_USERS = "GET_USERS";
	public static final String REMOVE_USERS = "REMOVE_USERS";
	public static final String UPDATE_USERS_CURRENCY = "UPDATE_USERS_CURRENCY";
	public static final String UPDATE_USERS_GUILDS = "UPDATE_USERS_GUILDS";
	public static final String UPDATE_USERS_REQUESTS = "UPDATE_USERS_REQUESTS";
	public static final String UPDATE_USERS_SOFTBANS = "UPDATE_USERS_SOFTBANS";
	public static final String UPDATE_USERS_SOUNDCLOUD_ID = "UPDATE_USERS_SOUNDCLOUD_ID";
	public static final String UPDATE_USERS_SOUNDCLOUD_PERMALINK = "UPDATE_USERS_SOUNDCLOUD_PERMALINK";

	private static UsersManager usersManager;
	private static ArrayList<Users> usersCache;

	private UsersManager() {
		usersCache = new ArrayList<Users>();

		try {
			ResultSet usersSet = Database.getInstance().getStatement(GET_USERS).executeQuery();
			while (usersSet.next()) {
				String id = usersSet.getString(Users.ID);
				String soundcloudId = usersSet.getString(Users.SOUNDCLOUD_ID);
				String soundcloudPermalink = usersSet.getString(Users.SOUNDCLOUD_PERMALINK);
				long currency = usersSet.getLong(Users.CURRENCY);
				long requests = usersSet.getLong(Users.REQUESTS);
				String guildsId = usersSet.getString(Users.GUILDS);
				String softbansGuildsId = usersSet.getString(Users.SOFTBANS);

				usersCache.add(new Users(id, soundcloudId, soundcloudPermalink, currency, requests, guildsId, softbansGuildsId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void loadUsers() {
		if (usersManager == null) {
			usersManager = new UsersManager();
		}
	}

	public static UsersManager getUsersManager() {
		return (usersManager);
	}

	public static boolean isUserInDatabase(String id) {
		if (getUser(id) != null) {
			return (true);
		}
		return (false);
	}

	public static boolean createNewDefaultUser(String id) {
		return (addUser(new Users(id, "", "", 0, 0, "", "")));
	}

	public static boolean addUser(Users user) {
		if (usersCache.contains(user)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(ADD_USER);
			statement.setString(1, user.getId());
			statement.setString(2, user.getSoundcloudId());
			statement.setString(3, user.getSoundcloudPermalink());
			statement.setString(4, MiscUtil.listToString(user.getGuildsId(), ","));
			statement.setString(5, MiscUtil.listToString(user.getSoftbansGuildsId(), ","));
			statement.setLong(6, user.getCurrency());
			statement.setLong(7, user.getRequests());

			if (statement.executeUpdate() == 1) {
				return (usersCache.add(user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean removeUser(Users user) {
		if (!usersCache.contains(user)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(REMOVE_USERS);
			statement.setString(1, user.getId());
			if (statement.executeUpdate() == 1) {
				return (usersCache.remove(user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean updateUserColumn(String id, String column, String value) {
		if (id == null || id.isEmpty() || value == null) {
			return (false);
		}
		Users user = getUser(id);
		if (user == null || column == null || column.isEmpty()) {
			return (false);
		}
		String statementName = "";
		boolean isLongType = false;
		switch (column) {
			case (Users.CURRENCY):
				statementName = UPDATE_USERS_CURRENCY;
				isLongType = true;
				break;
			case (Users.GUILDS):
				statementName = UPDATE_USERS_GUILDS;
				break;
			case (Users.REQUESTS):
				statementName = UPDATE_USERS_REQUESTS;
				isLongType = true;
				break;
			case (Users.SOFTBANS):
				statementName = UPDATE_USERS_SOFTBANS;
				break;
			case (Users.SOUNDCLOUD_ID):
				statementName = UPDATE_USERS_SOUNDCLOUD_ID;
				break;
			case (Users.SOUNDCLOUD_PERMALINK):
				statementName = UPDATE_USERS_SOUNDCLOUD_PERMALINK;
				break;
		}
		if (statementName.isEmpty()) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(statementName);
			if (isLongType) {
				statement.setLong(1, Long.parseLong(value));
			} else {
				statement.setString(1, value);
			}
			statement.setString(2, id);
			if (statement.executeUpdate() == 1) {
				return (updateUserCache(id, column, value));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	private static boolean updateUserCache(String id, String column, String value) {
		for (int i = 0; i < usersCache.size(); i += 1) {
			if (usersCache.get(i).getId().equals(id)) {
				switch (column) {
					case (Users.CURRENCY):
						usersCache.get(i).setCurrency(Long.parseLong(value));
						return (true);
					case (Users.GUILDS):
						usersCache.get(i).setGuildsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Users.REQUESTS):
						usersCache.get(i).setRequests(Long.parseLong(value));
						return (true);
					case (Users.SOFTBANS):
						usersCache.get(i).setSoftbansGuildsId(MiscUtil.stringToList(value, ","));
						return (true);
					case (Users.SOUNDCLOUD_ID):
						usersCache.get(i).setSoundcloudId(value);
						return (true);
					case (Users.SOUNDCLOUD_PERMALINK):
						usersCache.get(i).setSoundcloudPermalink(value);
						return (true);
				}
				return (false);
			}
		}
		return (false);
	}

	public static Users getUser(String id) {
		for (int i = 0; i < usersCache.size(); i += 1) {
			if (usersCache.get(i).getId().equals(id)) {
				return (usersCache.get(i));
			}
		}
		return (null);
	}

	public static Users getUserFromSoundCloudId(String soundcloudId) {
		for (int i = 0; i < usersCache.size(); i += 1) {
			if (usersCache.get(i).getSoundcloudId().equals(soundcloudId)) {
				return (usersCache.get(i));
			}
		}
		return (null);
	}

	public static Users getUserFromSoundCloudPermalink(String soundcloudPermalink) {
		for (int i = 0; i < usersCache.size(); i += 1) {
			if (usersCache.get(i).getSoundcloudPermalink().equals(soundcloudPermalink)) {
				return (usersCache.get(i));
			}
		}
		return (null);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Users> getUsers() {
		return ((ArrayList<Users>) usersCache.clone());
	}

	public static Artist getArtistById(String id) {
		return (Shione.getSC2DAPI().getArtistById(getUser(id).getSoundcloudId()));
	}

	public static Artist getArtistByUser(User user) {
		return (getArtistById(user.getId()));
	}

	public static boolean isSoundCloudAccountAvailable(String soundcloudId) {
		Users user = getUserFromSoundCloudId(soundcloudId);

		if (user == null) {
			return (true);
		}
		return (false);
	}

}
