package net.azzerial.sc2d.core;

import net.azzerial.sc2d.builders.EntityBuilder;
import net.azzerial.sc2d.entities.*;
import org.json.JSONObject;

public class SC2D {
	
	private API api;

	public SC2D(API api) {
		this.api = api;
	}

	public API getApi() {
		return (api);
	}

	/**
	 * Gets the {@link Avatar Avatar} object for the provided id.
	 * <br>If the provided id does not match the required format, this will return {@code null}.
	 * 
	 * @param avatarId
	 * The id relating to the {@link Avatar Avatar} we wish to retrieve.
	 * 
	 * @return The matching {@link Avatar Avatar}.
	 * <br>If the id did not match the required format, returns {@code null}. 
	 */
	public Avatar getAvatarById(String avatarId) {
		// Checks if the avatarId matches SoundCloud's avatar token format.
		if (avatarId.length() != 19
		|| !avatarId.matches("^([0-9]{12})-([a-zA-Z0-9]){6}$")) {
			return (null);
		}
		return (new Avatar(avatarId));
	}
	
	/**
	 * Gets the {@link Artist Artist} object for the provided id.
	 * <br>If the provided id does not have a match in SoundCloud's database, this will return {@code null}.
	 * 
	 * @param artistId
	 * The id relating to the {@link Artist Artist} we wish to retrieve.
	 * 
	 * @return The matching {@link Artist Artist}.
	 * <br>If the id did not have a match in SoundCloud's database, returns {@code null}. 
	 */
	public Artist getArtistById(String artistId) {
		if (artistId == null || artistId.isEmpty()) {
			return (null);
		}
		// Checks if the artistId is only made of digits.
		if (!artistId.matches("^([0-9]+)$")) {
			return (null);
		}
		JSONObject userObj = api.requestJson(api.pathArtistEntity(artistId));
		JSONObject userObj2 = api.requestJson(api.pathArtistEntity2(artistId));
		if (userObj == null || userObj2 == null) {
			return (null);
		}
		return (EntityBuilder.createUser(api, userObj, userObj2));
	}
	
	/**
	 * Gets the {@link Artist Artist} id object for the provided permalink.
	 * <br>If the provided permalink does not have a match in SoundCloud's database, this will return {@code null}.
	 * 
	 * @param permalink
	 * The permalink relating to the {@link Artist Artist} id we wish to retrieve.
	 * 
	 * @return The {@code String} representation of the matching {@link Artist Artist} id.
	 * <br>If the permalink did not have a match in SoundCloud's database, returns {@code null}. 
	 */
	public String getArtistIdFromPermalink(String permalink) {
		// Checks if the permalink isn't invalid.
		if (permalink == null || permalink.isEmpty()) {
			return (null);
		}
		return (api.getArtistId(permalink));
	}
	
	/**
	 * Gets the {@link Visual Visual} object for the provided id.
	 * <br>If the provided id does not match the required format, this will return {@code null}.
	 * 
	 * @param visualId
	 * The id relating to the {@link Visual Visual} we wish to retrieve.
	 * 
	 * @return The matching {@link Visual Visual}.
	 * <br>If the id did not match the required format, returns {@code null}. 
	 */
	public Visual getVisualById(String visualId) {
		// Checks if the visualId matches SoundCloud's visual token format.
		if (visualId.length() != 19
		|| !visualId.matches("^([0-9]{12})-([a-zA-Z0-9]){6}$")) {
			return (null);
		}
		return (new Visual(visualId));
	}

	// -- NEED JAVADOCS --

	public Comment getCommentById(String commentId) {
		if (commentId == null || commentId.isEmpty()) {
			return (null);
		}
		// Checks if the commentId is only made of digits.
		if (!commentId.matches("^([0-9]+)$")) {
			return (null);
		}
		JSONObject commentObj = api.requestJson(api.pathCommentEntity(commentId));
		if (commentObj == null) {
			return (null);
		}
		return (EntityBuilder.createComment(api, commentObj));
	}

	public Track getTrackById(String trackId) {
		if (trackId == null || trackId.isEmpty()) {
			return (null);
		}
		// Checks if the trackId is only made of digits.
		if (!trackId.matches("^([0-9]+)$")) {
			return (null);
		}
		JSONObject trackObj = api.requestJson(api.pathTrackEntity(trackId));
		JSONObject streamObj = api.requestJson(api.pathTrackStreamData(trackId));
		if (trackObj == null || streamObj == null) {
			return (null);
		}
		return (EntityBuilder.createTrack(api, trackObj, streamObj));
	}
}
