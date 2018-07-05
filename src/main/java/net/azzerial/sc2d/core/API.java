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
	
	private final String cliendId;
	private final String appVersion;
	
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
		String path = "https://api.soundcloud.com/users/" + permalink + "?" + CLIENT_ID + this.cliendId;
		JSONObject obj = requestJson(path);

		if (obj == null) {
			return (null);
		}
		userId = obj.getLong("id");
		return (Long.toString(userId));
	}

	public String pathArtistEntity(String artistId) {
		return ("https://api.soundcloud.com/users/" + artistId + "?" + CLIENT_ID + this.cliendId);
	}

	public String pathArtistEntity2(String artistId) {
		return ("https://api-v2.soundcloud.com/users/" + artistId + "?" + CLIENT_ID + this.cliendId + "&" + APP_VERSION + this.appVersion);
	}

}
