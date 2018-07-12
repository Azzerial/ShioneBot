 package net.azzerial.sc2d.builders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.azzerial.sc2d.entities.Artist;
import net.azzerial.sc2d.entities.Comment;
import net.azzerial.sc2d.entities.Track;
import net.azzerial.sc2d.entities.impls.ArtistImpl;
import net.azzerial.sc2d.entities.impls.CommentImpl;
import net.azzerial.sc2d.entities.impls.TrackImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import net.azzerial.sc2d.core.API;

 public class EntityBuilder {

	private final static String DEFAULT_AVATAR = "https://a1.sndcdn.com/images/default_avatar_large.png";
	
	public static Artist createUser(API api, JSONObject artistObj, JSONObject artist2Obj) {
		final long id = artist2Obj.getLong("id");
		String avatarId;
		String visualId;
		Calendar accountCreationDate = Calendar.getInstance();

		// Check if the provided Jsons are from a user
		if (!artistObj.getString("kind").equals("user")
		|| !artist2Obj.getString("kind").equals("user")) {
			return (null);
		}
		// Get and check the artist's Avatar id.
		if (artistObj.getString("avatar_url").equals(DEFAULT_AVATAR)
		&& artist2Obj.getString("avatar_url").startsWith(DEFAULT_AVATAR)) {
			avatarId = null;
		} else {
			avatarId = artist2Obj.getString("avatar_url").substring(30, 49);
		}
		// Get and check the artist's Visual id.
		if (artist2Obj.get("visuals") == null) {
			visualId = null;
		} else {
			JSONObject visualsObj = artist2Obj.getJSONObject("visuals");
			JSONArray visualsArray = visualsObj.getJSONArray("visuals");
			JSONObject visualObj = visualsArray.getJSONObject(0);
			visualId = visualObj.getString("visual_url").substring(30, 49);
		}
		// Get and set the artist's account creation date
		String[] createdDate = artist2Obj.getString("created_at").split("\\D");
		accountCreationDate.set(
			Integer.parseInt(createdDate[0]),
			Integer.parseInt(createdDate[1]),
			Integer.parseInt(createdDate[2]),
			Integer.parseInt(createdDate[3]),
			Integer.parseInt(createdDate[4]),
			Integer.parseInt(createdDate[5])
		);
		
		ArtistImpl artist = new ArtistImpl(api, id);
		artist.setAvatarId(avatarId)
			.setCity((artist2Obj.get("city").equals(null)) ? (null) : (artist2Obj.getString("city").isEmpty() ? (null) : (artist2Obj.getString("city"))))
			.setCommentsCount(artist2Obj.getLong("comments_count"))
			.setCountry((artistObj.get("country").equals(null)) ? (null) : (artistObj.getString("country").isEmpty() ? (null) : (artistObj.getString("country"))))
			.setCountryCode((artist2Obj.get("country_code").equals(null)) ? (null) : (artist2Obj.getString("country_code").isEmpty() ? (null) : (artist2Obj.getString("country_code"))))
			.setCreationDate(accountCreationDate)
			.setDescription((artist2Obj.get("description").equals(null)) ? (null) : (artist2Obj.getString("description").isEmpty() ? (null) : (artist2Obj.getString("description"))))
			.setFirstName((artist2Obj.get("first_name").equals(null)) ? (null) : (artist2Obj.getString("first_name").isEmpty() ? (null) : (artist2Obj.getString("first_name"))))
			.setFollowersCount(artist2Obj.getLong("followers_count"))
			.setFollowingsCount(artist2Obj.getLong("followings_count"))
			.setFullName((artist2Obj.get("full_name").equals(null)) ? (null) : (artist2Obj.getString("full_name").isEmpty() ? (null) : (artist2Obj.getString("full_name"))))
			.setLastName((artist2Obj.get("last_name").equals(null)) ? (null) : (artist2Obj.getString("last_name").isEmpty() ? (null) : (artist2Obj.getString("last_name"))))
			.setLikesCount(artist2Obj.getLong("likes_count") + artist2Obj.getLong("playlist_likes_count"))
			.setPermalink(artist2Obj.getString("permalink"))
			.setPlanSubscription(artistObj.getString("plan"))
			.setPlaylistsCount(artist2Obj.getLong("playlist_count"))
			.setPlaylistsLikesCount(artist2Obj.getLong("playlist_likes_count"))
			.setRepostsCount(artistObj.getLong("reposts_count"))
			.setTracksCount(artist2Obj.getLong("track_count"))
			.setTracksLikesCount(artist2Obj.getLong("likes_count"))
			.setUsername(artist2Obj.getString("username"))
			.setVisualId(visualId);
		return (artist);
	}

	public static Comment createComment(API api, JSONObject commentObj) {
		final long id = commentObj.getLong("id");
		Calendar commentCreationDate = Calendar.getInstance();

		// Check if the provided Json is from a comment
		if (!commentObj.getString("kind").equals("comment")) {
			return (null);
		}
		// Get the author json data
		JSONObject authorObj = commentObj.getJSONObject("user");
		if (!authorObj.getString("kind").equals("user")) {
			return (null);
		}
		// Get the track json data
		JSONObject trackObj = commentObj.getJSONObject("track");
		if (!trackObj.getString("kind").equals("track")) {
			return (null);
		}
		// Get the artist json data
		JSONObject artistObj = trackObj.getJSONObject("user");
		if (!artistObj.getString("kind").equals("user")) {
			return (null);
		}
		// Get and set the comment's creation date
		String[] createdDate = commentObj.getString("created_at").split("\\D");
		commentCreationDate.set(
			Integer.parseInt(createdDate[0]),
			Integer.parseInt(createdDate[1]),
			Integer.parseInt(createdDate[2]),
			Integer.parseInt(createdDate[3]),
			Integer.parseInt(createdDate[4]),
			Integer.parseInt(createdDate[5])
		);

		CommentImpl comment = new CommentImpl(api, id);
		comment.setArtistId(artistObj.getLong("id"))
			.setArtistPermalink(artistObj.getString("permalink"))
			.setArtistUsername(artistObj.getString("username"))
			.setAuthorId(authorObj.getLong("id"))
			.setAuthorPermalink(authorObj.getString("permalink"))
			.setAuthorUsername(authorObj.getString("username"))
			.setContent(commentObj.getString("body"))
			.setCreationDate(commentCreationDate)
			.setTimestamp(commentObj.getLong("timestamp"))
			.setTrackDuration(trackObj.getLong("duration"))
			.setTrackId(trackObj.getLong("id"))
			.setTrackPermalink(trackObj.getString("permalink"))
			.setTrackTitle(trackObj.getString("title"));
		return (comment);
	}

	public static Track createTrack(API api, JSONObject trackObj, JSONObject streamObj) {
		final long id = trackObj.getLong("id");
		Calendar trackCreationDate = Calendar.getInstance();

		// Check if the provided Json is from a track
		if (!trackObj.getString("kind").equals("track")) {
			return (null);
		}
		// Get the artist json data
		JSONObject artistObj = trackObj.getJSONObject("user");
		if (!artistObj.getString("kind").equals("user")) {
			return (null);
		}
		// Get the track's tags
		List<String> tags = new ArrayList<String>();
		for (String tag : trackObj.getString("tag_list").split(" (?=(?:(?:[^\"]*\"){2})*[^\"]*$)", -1)) {
			tags.add(tag.replaceAll("\"", ""));
		}
		// Get the track's waveform image
		String waveformImage = trackObj.getString("waveform_url");
		waveformImage = waveformImage.replaceFirst("wis", "w1");
		waveformImage = waveformImage.replace(".json", ".png");
		// Get and set the track's creation date
		String[] createdDate = trackObj.getString("created_at").split("\\D");
		trackCreationDate.set(
			Integer.parseInt(createdDate[0]),
			Integer.parseInt(createdDate[1]),
			Integer.parseInt(createdDate[2]),
			Integer.parseInt(createdDate[3]),
			Integer.parseInt(createdDate[4]),
			Integer.parseInt(createdDate[5])
		);

		TrackImpl track = new TrackImpl(api, id);
		track.setArtistId(artistObj.getLong("id"))
			.setArtistPermalink(artistObj.getString("permalink"))
			.setArtistUsername(artistObj.getString("username"))
			.setCommentable(trackObj.getBoolean("commentable"))
			.setCommentsCount(trackObj.getLong("comment_count"))
			.setCreationDate(trackCreationDate)
			.setDescription(trackObj.get("description").equals(null) ? (null) : trackObj.getString("description"))
			.setDownloadable(trackObj.getBoolean("downloadable"))
			.setDownloadCount(trackObj.getLong("download_count"))
			.setDownloadUrl(trackObj.get("download_url").equals(null) ? (null) : trackObj.getString("download_url"))
			.setDuration(trackObj.getLong("duration"))
			.setGenre(trackObj.getString("genre")) // NULL CHECK
			.setLikesCount(trackObj.getLong("likes_count"))
			.setPermalink(trackObj.getString("permalink"))
			.setPlaybackCount(trackObj.getLong("playback_count"))
			.setPublic((boolean) trackObj.get("public"))
			.setRepostsCount(trackObj.getLong("reposts_count"))
			.setStreamable(trackObj.getBoolean("streamable"))
			.setStreamPreviewUrl(streamObj.getString("preview_mp3_128_url"))
			.setStreamUrl(streamObj.getString("http_mp3_128_url"))
			.setTags(tags)
			.setTitle(trackObj.getString("title"))
			.setWaveformImageUrl(waveformImage);
		return (track);
	}
	
}
