package net.azzerial.shione.database;

import net.azzerial.shione.core.ShioneInfo;

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
					"id TEXT UNIQUE, " +
					"PRIMARY KEY (id)" +
				")");
			// Create the GuildDb
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"Guilds(" +
					"id TEXT UNIQUE, " +
					"prefix TEXT NOT NULL DEFAULT '" + ShioneInfo.PREFIX + "', " +
					"admins TEXT NOT NULL, " +
					"users TEXT, " +
					"roles TEXT, " +
					"softbans TEXT, " +
					"muted_channels TEXT, " +
					"PRIMARY KEY (id)" +
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

/*
			// Create the Guilds table
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"Guilds(" +
					"id INTEGER," +
					"PRIMARY KEY (id)" +
				")");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
				"GuildAdmins(" +
					"guild_id INTEGER," +
					"list TEXT NOT NULL," +
					"PRIMARY KEY (guild_id)," +
					"FOREIGN KEY (guild_id) REFERENCES Guilds(id) " +
						"ON UPDATE NO ACTION " +
						"ON DELETE CASCADE" +
				")");
			statement.executeUpdate("REPLACE INTO Guilds (" +
					"id)" +
				"VALUES " +
					"(1)," +
					"(2)," +
					"(3)"
				);
			statement.executeUpdate("REPLACE INTO GuildAdmins (" +
					"guild_id," +
					"list)" +
				"VALUES " +
					"(1, '1231425215,3123123312,3124214432')," +
					"(2, '3243245325,2432454353,4324325325')," +
					"(3, '5435345345,5435342643,6722472472')"
				);
		*/