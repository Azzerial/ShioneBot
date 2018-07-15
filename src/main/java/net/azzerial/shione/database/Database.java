package net.azzerial.shione.database;

import java.sql.*;

import java.util.HashMap;

public class Database {

	private static Database instance;

	private Connection connection;
	private HashMap<String, PreparedStatement> preparedStatements;

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return (instance);
	}

	private Database() {
		preparedStatements = new HashMap<String, PreparedStatement>();

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:shione.db");
			Statement statement = connection.createStatement();

			statement.setQueryTimeout(30);
			statement.execute("PRAGMA foreign_keys = ON");

			// Create the Ops table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"Ops(" +
					"id TEXT NOT NULL UNIQUE, " +
					"PRIMARY KEY (id)" +
				")");
			// Create the Guilds table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"Guilds(" +
					"id TEXT NOT NULL UNIQUE, " +
					"prefix TEXT NOT NULL, " +
					"admins TEXT NOT NULL, " +
					"users TEXT, " +
					"roles TEXT, " +
					"softbans TEXT, " +
					"muted_channels TEXT, " +
					"PRIMARY KEY (id)" +
				")");
			// Create the Users table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"Users(" +
					"id TEXT NOT NULL UNIQUE, " +
					"soundcloud_id TEXT NOT NULL UNIQUE, " +
					"soundcloud_permalink TEXT NOT NULL UNIQUE, " +
					"guilds TEXT, " +
					"softbans TEXT, " +
					"currency REAL DEFAULT 0, " +
					"requests REAL DEFAULT 0, " +
					"PRIMARY KEY (id, soundcloud_id)" +
				")");

			// Ops Statements
			preparedStatements.put(Permissions.ADD_OP, connection.prepareStatement("INSERT INTO Ops (id) VALUES (?)"));
			preparedStatements.put(Permissions.GET_OPS, connection.prepareStatement("SELECT id FROM Ops"));
			preparedStatements.put(Permissions.REMOVE_OP, connection.prepareStatement("DELETE FROM Ops WHERE id = ?"));

			// Guilds Statements
			preparedStatements.put(GuildsManager.ADD_GUILD, connection.prepareStatement("INSERT INTO Guilds VALUES (?, ?, ?, ?, ?, ?, ?)"));
			preparedStatements.put(GuildsManager.GET_GUILDS, connection.prepareStatement("SELECT * FROM Guilds"));
			preparedStatements.put(GuildsManager.REMOVE_GUILD, connection.prepareStatement("DELETE FROM Guilds WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_ADMINS, connection.prepareStatement("UPDATE Guilds SET admins = ? WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_CHANNELS, connection.prepareStatement("UPDATE Guilds SET muted_channels = ? WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_PREFIX, connection.prepareStatement("UPDATE Guilds SET prefix = ? WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_ROLES, connection.prepareStatement("UPDATE Guilds SET roles = ? WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_SOFTBANS, connection.prepareStatement("UPDATE Guilds SET softbans = ? WHERE id = ?"));
			preparedStatements.put(GuildsManager.UPDATE_GUILD_USERS, connection.prepareStatement("UPDATE Guilds SET users = ? WHERE id = ?"));

			// Users Statements
			preparedStatements.put(UsersManager.ADD_USER, connection.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?, ?)"));
			preparedStatements.put(UsersManager.GET_USERS, connection.prepareStatement("SELECT * FROM Users"));
			preparedStatements.put(UsersManager.REMOVE_USERS, connection.prepareStatement("DELETE FROM Users WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_CURRENCY, connection.prepareStatement("UPDATE Users SET currency = ? WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_GUILDS, connection.prepareStatement("UPDATE Users SET guilds = ? WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_REQUESTS, connection.prepareStatement("UPDATE Users SET requests = ? WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_SOFTBANS, connection.prepareStatement("UPDATE Users SET softbans = ? WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_SOUNDCLOUD_ID, connection.prepareStatement("UPDATE Users SET soundcloud_id = ? WHERE id = ?"));
			preparedStatements.put(UsersManager.UPDATE_USERS_SOUNDCLOUD_PERMALINK, connection.prepareStatement("UPDATE Users SET soundcloud_permalink = ? WHERE id = ?"));

		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement getStatement(String statementName) {
		if (!preparedStatements.containsKey(statementName)) {
			return (null);
		}
		return (preparedStatements.get(statementName));
	}

}