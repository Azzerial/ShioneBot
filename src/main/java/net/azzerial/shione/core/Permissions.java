package net.azzerial.shione.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.User;

public class Permissions {

	public static final String ADD_OP = "ADD_OP";
	public static final String GET_OPS = "GET_OPS";
	public static final String REMOVE_OP = "REMOVE_OP";

	public static final String ADMIN_REQUIRED_MESSAGE = "You do not have the permission to run this commands. (Admin required)";
	public static final String PERMISSION_REQUIRED_MESSAGE = "You do not have the permission to run this commands.";
	public static final String OP_REQUIRED_MESSAGE = "You do not have the permission to run this commands. (Op required)";

	private static Permissions permissions;
	private static ArrayList<String> ops;

	private Permissions() {
		ops = new ArrayList<String>();
		try {
			ResultSet opsSet = Database.getInstance().getStatement(GET_OPS).executeQuery();
			while (opsSet.next()) {
				ops.add(opsSet.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ops.size() == 0) {
			ops.add(ShioneInfo.BOT_AUTHOR_ID);
		}
	}

	public static void setupPermissions() {
		if (permissions == null) {
			permissions = new Permissions();
		}
	}

	public static Permissions getPermissions() {
		return permissions;
	}

	public static boolean addOp(String userId) {
		if (ops.contains(userId)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(ADD_OP);
			statement.setString(1, userId);
			if (statement.executeUpdate() == 1) {
				return (ops.add(userId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	public static boolean removeOp(String userId) {
		if (!ops.contains(userId) || userId.equals(ShioneInfo.BOT_AUTHOR_ID)) {
			return (false);
		}

		try {
			PreparedStatement statement = Database.getInstance().getStatement(REMOVE_OP);
			statement.setString(1, userId);
			if (statement.executeUpdate() == 1) {
				return (ops.remove(userId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (false);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> getOps() {
		return ((ArrayList<String>) ops.clone());
	}

	public static boolean isOp(User user) {
		return (isOp(user.getId()));
	}

	public static boolean isOp(String userId) {
		return (ops.contains(userId));
	}
}
