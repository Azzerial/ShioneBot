package net.azzerial.sc2d.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

	// -- Request Paths Elements --

	public final String SOUNDCLOUD_URL = "https://soundcloud.com/";
	// APIs
	private final String API = "https://api.soundcloud.com/";
	private final String API_V2 = "https://api-v2.soundcloud.com/";
	// Resources
	private final String RSC_COMMENTS = "comments/";
	private final String RSC_PLAYLISTS = "playlists/";
	private final String RSC_TRACKS = "tracks/";
	private final String RSC_USERS = "users/";
	// Sub-resources
	private final String SUBRSC_COMMENTS = "/comments?";
	private final String SUBRSC_FAVORITES = "/favorites?";
	private final String SUBRSC_FOLLOWERS = "/followers?";
	private final String SUBRSC_FOLLOWINGS = "/followings?";
	private final String SUBRSC_PLAYLISTS = "/playlists?";
	private final String SUBRSC_TRACKS = "/tracks?";
	private final String SUBRSC_WEB_PROFILE = "/web-profiles?";
	// Parameters
	private final String APP_VERSION = "app_version=";
	private final String CLIENT_ID = "client_id=";
	private final String LIMIT = "limit=";
	private final String OFFSET = "offset=";
	private final String LINKED_PARTITIONING = "linked_partitioning=";

	// -- API --

	private final String clientId;
	private final String appVersion;
	
	public API(String clientId, String appVersion) {
		this.clientId = clientId;
		this.appVersion = appVersion;
	}

	// -- Getters --

	public String getAppVersion() {
		return (appVersion);
	}

	public String getClientId() {
		return (clientId);
	}

	// -- SoundCloud Requests --

	public JSONObject requestJson(String path) {
		JSONObject obj = null;

		try {
			InputStream stream = new URL(path).openStream();
			String json = IOUtils.toString(stream);
			obj = new JSONObject(json);
			stream.close();
		} catch (IOException | JSONException e) {
			return (null);
		}
		return (obj);
	}

	public String getArtistId(String permalink) {
		long userId;
		String path = "https://api.soundcloud.com/users/" + permalink + "?" + CLIENT_ID + this.clientId;
		JSONObject obj = requestJson(path);

		if (obj == null) {
			return (null);
		}
		userId = obj.getLong("id");
		return (Long.toString(userId));
	}

	// -- Request Paths Builders --

	/*
	 * The request path is built the following way:
	 * 	API + RSC + ID + (SUBRSC) + PARAM
	 *
	 *
	 * API:
	 * The version of the SoundCloud API we want to get the json data from.
	 *
	 * RSC:
	 * The type of resource we want to retrieve.
	 *
	 * ID:
	 * The id of the resource we are retrieving.
	 *
	 * SUBRSC:
	 * Sometimes you want to get a sub-resource of the retrieved resource.
	 *
	 * PARAM:
	 * For the API only the CLIENT_ID is mandatory, but for the API_V2,
	 * both the CLIENT_ID and the APP_VERSION are mandatory.
	 *
	 * Optional parameters:
	 * LIMIT
	 * 	-> size of the collection you are retrieving (only used for sub-resources).
	 * OFFSET
	 * 	-> value of the continuation of the collection we are retrieving.
	 * LINKED_PARTITIONING
	 * 	-> adds a href of the continuation of the collection we are retrieving.
	 */

	public String pathArtistEntity(String artistId) {
		return (API + RSC_USERS + artistId + "?" + CLIENT_ID + clientId);
	}

	public String pathArtistEntity2(String artistId) {
		return (API_V2 + RSC_USERS + artistId + "?" + CLIENT_ID + clientId + "&" + APP_VERSION + appVersion);
	}

	public String pathCommentEntity(String commentId) {
		return (API_V2 + RSC_COMMENTS + commentId + "?" + CLIENT_ID + clientId + "&" + APP_VERSION + appVersion);
	}

}
