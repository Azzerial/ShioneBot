package net.azzerial.sc2d.core;

import net.azzerial.sc2d.builders.EntityBuilder;
import net.azzerial.sc2d.entities.Avatar;
import net.azzerial.sc2d.entities.Visual;
import net.azzerial.sc2d.entities.Artist;
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
	 * @param userId
	 * The id relating to the {@link Artist Artist} we wish to retrieve.
	 * 
	 * @return The matching {@link Artist Artist}.
	 * <br>If the id did not have a match in SoundCloud's database, returns {@code null}. 
	 */
	public Artist getUserById(String userId) {
		if (userId == null || userId.isEmpty()) {
			return (null);
		}
		// Checks if the userId is only made of digits.
		if (!userId.matches("^([0-9]+)$")) {
			return (null);
		}
		JSONObject userObj = api.requestUserToSoundCloudApi(userId);
		JSONObject userObj2 = api.requestUserToSoundCloudApiV2(userId);
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
	public String getUserIdFromPermalink(String permalink) {
		// Checks if the permalink isn't invalid.
		if (permalink == null || permalink.isEmpty()) {
			return (null);
		}
		return (api.requestUserIdToSoundCloudApi(permalink));
	}
	
	/**
	 * Gets the {@link Visual Visual} object for the provided id.
	 * <br>If the provided id does not match the required format, this will return {@code null}.
	 * 
	 * @param avatarId
	 * The id relating to the {@link Visual Visual} we wish to retrieve.
	 * 
	 * @return The matching {@link Visual Visual}.
	 * <br>If the id did not match the required format, returns {@code null}. 
	 */
	public Visual getVisualById(String visualId) {
		if (visualId.length() != 19
		|| !visualId.matches("^([0-9]{12})-([a-zA-Z0-9]){6}$")) {
			return (null);
		}
		return (new Visual(visualId));
	}
	
}