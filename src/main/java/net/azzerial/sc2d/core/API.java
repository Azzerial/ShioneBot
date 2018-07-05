package net.azzerial.sc2d.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

	public final String APP_VERSION = "app_version=";
	public final String CLIENT_ID = "client_id=";
	public final String LIMIT = "limit=";
	public final String OFFSET = "offset=";
	public final String LINKED_PARTITIONING = "linked_partitioning=";
	
	private String cliendId;
	private String appVersion;
	
	public API(String cliendId, String appVersion) {
		this.cliendId = cliendId;
		this.appVersion = appVersion;
	}
	
	public String getAppVersion() {
		return (appVersion);
	}

	public String getCliendId() {
		return (cliendId);
	}
	
	public String requestUserIdToSoundCloudApi(String permalink) {
		long userId;
		JSONObject userObj = null;
		
		try {
			InputStream stream = new URL("https://api.soundcloud.com/users/"
				+ permalink + "?"
				+ CLIENT_ID + this.cliendId
			).openStream();
			String json = IOUtils.toString(stream);
			userObj = new JSONObject(json);
			userId = userObj.getLong("id");
			stream.close();
		} catch (IOException | JSONException e) {
			return (null);
		}
		return (Long.toString(userId));
	}
	
	public JSONObject requestUserToSoundCloudApi(String userId) {
		JSONObject userObj = null;
		
		try {
			InputStream stream = new URL("https://api.soundcloud.com/users/"
				+ userId + "?"
				+ CLIENT_ID + this.cliendId
			).openStream();
			String json = IOUtils.toString(stream);
			userObj = new JSONObject(json);
			stream.close();
		} catch (IOException | JSONException e) {
			return (null);
		}
		return (userObj);
	}
	
	public JSONObject requestUserToSoundCloudApiV2(String userId) {
		JSONObject userObj = null;
		
		try {
			InputStream stream = new URL("https://api-v2.soundcloud.com/users/"
				+ userId + "?"
				+ CLIENT_ID + this.cliendId + "&"
				+ APP_VERSION + this.appVersion
			).openStream();
			String json = IOUtils.toString(stream);
			userObj = new JSONObject(json);
			stream.close();
		} catch (IOException | JSONException e) {
			return (null);
		}
		return (userObj);
	}
	
}
