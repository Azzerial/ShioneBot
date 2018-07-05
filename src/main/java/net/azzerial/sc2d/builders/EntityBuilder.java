 package net.azzerial.sc2d.builders;

import java.util.Calendar;

import net.azzerial.sc2d.entities.Artist;
import net.azzerial.sc2d.entities.impls.ArtistImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import net.azzerial.sc2d.core.API;

 public class EntityBuilder {

	private final static String DEFAULT_AVATAR = "https://a1.sndcdn.com/images/default_avatar_large.png";
	
	public static Artist createUser(API api, JSONObject artistJson, JSONObject artist2JSON) {
		final long id = artist2JSON.getLong("id");
		String avatarId;
		String visualId;
		Calendar accountCreationDate = Calendar.getInstance();
		
		// Get and check the User's Avatar id.
		if (artistJson.getString("avatar_url").equals(DEFAULT_AVATAR)
		&& artist2JSON.getString("avatar_url").startsWith(DEFAULT_AVATAR)) {
			avatarId = null;
		} else {
			avatarId = artist2JSON.getString("avatar_url").substring(30, 49);
		}
		// Get and check the User's Visual id.
		if (artist2JSON.get("visuals") == null) {
			visualId = null;
		} else {
			JSONObject visualsObj = artist2JSON.getJSONObject("visuals");
			JSONArray visualsArray = visualsObj.getJSONArray("visuals");
			JSONObject visualObj = visualsArray.getJSONObject(0);
			visualId = visualObj.getString("visual_url").substring(30, 49);
		}
		// Get and set the User's account creation date
		String[] createdDate = artist2JSON.getString("created_at").split("\\D");
		accountCreationDate.set(
			Integer.parseInt(createdDate[0]),
			Integer.parseInt(createdDate[1]),
			Integer.parseInt(createdDate[2]),
			Integer.parseInt(createdDate[3]),
			Integer.parseInt(createdDate[4]),
			Integer.parseInt(createdDate[5])
		);
		
		ArtistImpl artist = new ArtistImpl(api, id);
		artist.setAccountCreationDate(accountCreationDate)
			.setAvatarId(avatarId)
			.setCity((artist2JSON.get("city").equals(null)) ? (null) : (artist2JSON.getString("city").isEmpty() ? (null) : (artist2JSON.getString("city"))))
			.setCommentsCount(artist2JSON.getLong("comments_count"))
			.setCountry((artistJson.get("country").equals(null)) ? (null) : (artistJson.getString("country").isEmpty() ? (null) : (artistJson.getString("country"))))
			.setCountryCode((artist2JSON.get("country_code").equals(null)) ? (null) : (artist2JSON.getString("country_code").isEmpty() ? (null) : (artist2JSON.getString("country_code"))))
			.setDescription((artist2JSON.get("description").equals(null)) ? (null) : (artist2JSON.getString("description").isEmpty() ? (null) : (artist2JSON.getString("description"))))
			.setFirstName((artist2JSON.get("first_name").equals(null)) ? (null) : (artist2JSON.getString("first_name").isEmpty() ? (null) : (artist2JSON.getString("first_name"))))
			.setFollowersCount(artist2JSON.getLong("followers_count"))
			.setFollowingsCount(artist2JSON.getLong("followings_count"))
			.setFullName((artist2JSON.get("full_name").equals(null)) ? (null) : (artist2JSON.getString("full_name").isEmpty() ? (null) : (artist2JSON.getString("full_name"))))
			.setLastName((artist2JSON.get("last_name").equals(null)) ? (null) : (artist2JSON.getString("last_name").isEmpty() ? (null) : (artist2JSON.getString("last_name"))))
			.setLikesCount(artist2JSON.getLong("likes_count") + artist2JSON.getLong("playlist_likes_count"))
			.setPageUrl(artist2JSON.getString("permalink_url"))
			.setPermalink(artist2JSON.getString("permalink"))
			.setPlanSubscription(artistJson.getString("plan"))
			.setPlaylistsCount(artist2JSON.getLong("playlist_count"))
			.setPlaylistsLikesCount(artist2JSON.getLong("playlist_likes_count"))
			.setRepostsCount(artistJson.getLong("reposts_count"))
			.setTracksCount(artist2JSON.getLong("track_count"))
			.setTracksLikesCount(artist2JSON.getLong("likes_count"))
			.setUsername(artist2JSON.getString("username"))
			.setVisualId(visualId);
		return (artist);
	}
	
}
