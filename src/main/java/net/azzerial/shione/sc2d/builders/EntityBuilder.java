 package net.azzerial.shione.sc2d.builders;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import net.azzerial.shione.sc2d.API;
import net.azzerial.shione.sc2d.entities.impl.Artist;
import net.azzerial.shione.sc2d.entities.impl.ArtistImpl;

public class EntityBuilder {
	
	// Utils
	private final static String DEFAULT_AVATAR = "https://a1.sndcdn.com/images/default_avatar_large.png";
	
	public static Artist createUser(API api, JSONObject user, JSONObject user2) {
		final long id = user2.getLong("id");
		String avatarId;
		String visualId;
		Calendar accountCreationDate = Calendar.getInstance();
		
		// Get and check the User's Avatar id.
		if (user.getString("avatar_url").equals(DEFAULT_AVATAR)
		&& user2.getString("avatar_url").startsWith(DEFAULT_AVATAR)) {
			avatarId = null;
		} else {
			avatarId = user2.getString("avatar_url").substring(30, 49);
		}
		// Get and check the User's Visual id.
		if (user2.get("visuals") == null) {
			visualId = null;
		} else {
			JSONObject visualsObj = user2.getJSONObject("visuals");
			JSONArray visualsArray = visualsObj.getJSONArray("visuals");
			JSONObject visualObj = visualsArray.getJSONObject(0);
			visualId = visualObj.getString("visual_url").substring(30, 49);
		}
		// Get and set the User's account creation date
		String[] createdDate = user2.getString("created_at").split("\\D");
		accountCreationDate.set(
			Integer.parseInt(createdDate[0]),
			Integer.parseInt(createdDate[1]),
			Integer.parseInt(createdDate[2]),
			Integer.parseInt(createdDate[3]),
			Integer.parseInt(createdDate[4]),
			Integer.parseInt(createdDate[5])
		);
		
		ArtistImpl userObj = new ArtistImpl(api, id);
		userObj.setAccountCreationDate(accountCreationDate)
			.setAvatarId(avatarId)
			.setCity((user2.get("city").equals(null)) ? (null) : (user2.getString("city").isEmpty() ? (null) : (user2.getString("city"))))
			.setCommentsCount(user2.getLong("comments_count"))
			.setCountry((user.get("country").equals(null)) ? (null) : (user.getString("country").isEmpty() ? (null) : (user.getString("country"))))
			.setCountryCode((user2.get("country_code").equals(null)) ? (null) : (user2.getString("country_code").isEmpty() ? (null) : (user2.getString("country_code"))))
			.setDescription((user2.get("description").equals(null)) ? (null) : (user2.getString("description").isEmpty() ? (null) : (user2.getString("description"))))
			.setFirstName((user2.get("first_name").equals(null)) ? (null) : (user2.getString("first_name").isEmpty() ? (null) : (user2.getString("first_name"))))
			.setFollowersCount(user2.getLong("followers_count"))
			.setFollowingsCount(user2.getLong("followings_count"))
			.setFullName((user2.get("full_name").equals(null)) ? (null) : (user2.getString("full_name").isEmpty() ? (null) : (user2.getString("full_name"))))
			.setLastName((user2.get("last_name").equals(null)) ? (null) : (user2.getString("last_name").isEmpty() ? (null) : (user2.getString("last_name"))))
			.setLikesCount(user2.getLong("likes_count") + user2.getLong("playlist_likes_count"))
			.setPageUrl(user2.getString("permalink_url"))
			.setPermalink(user2.getString("permalink"))
			.setPlanSubscription(user.getString("plan"))
			.setPlaylistsCount(user2.getLong("playlist_count"))
			.setPlaylistsLikesCount(user2.getLong("playlist_likes_count"))
			.setRepostsCount(user.getLong("reposts_count"))
			.setTracksCount(user2.getLong("track_count"))
			.setTracksLikesCount(user2.getLong("likes_count"))
			.setUsername(user2.getString("username"))
			.setVisualId(visualId)
		;
		return (userObj);
	}
	
}
