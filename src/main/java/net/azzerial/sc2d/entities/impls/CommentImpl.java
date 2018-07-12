package net.azzerial.sc2d.entities.impls;

import java.util.Calendar;

import net.azzerial.sc2d.builders.EntityBuilder;
import net.azzerial.sc2d.core.API;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.sc2d.entities.Comment;
import net.azzerial.sc2d.utils.MiscUtil;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class CommentImpl implements Comment {

	private API api;
	private long id;

	private long artistId;
	private String artistPermalink;
	private String artistUsername;
	private long authorId;
	private String authorPermalink;
	private String authorUsername;
	private String content;
	private Calendar creationDate;
	private long timestamp;
	private long trackDuration;
	private long trackId;
	private String trackPermalink;
	private String trackTitle;

	public CommentImpl(API api, long id) {
		this.api = api;
		this.id = id;
	}

	// -- Getters --

	@Override
	public Artist getArtist() {
		JSONObject obj = api.requestJson(api.pathArtistEntity(Long.toString(artistId)));
		JSONObject obj2 = api.requestJson(api.pathArtistEntity2(Long.toString(artistId)));
		if (obj == null || obj2 == null) {
			return (null);
		}
		return (EntityBuilder.createUser(api, obj, obj2));
	}

	@Override
	public String getArtistId() {
		return (Long.toString(artistId));
	}

	@Override
	public long getArtistIdLong() {
		return (artistId);
	}

	@Override
	public String getArtistPermalink() {
		return (artistPermalink);
	}

	@Override
	public String getArtistUsername() {
		return (artistUsername);
	}

	@Override
	public Artist getAuthor() {
		JSONObject obj = api.requestJson(api.pathArtistEntity(Long.toString(authorId)));
		JSONObject obj2 = api.requestJson(api.pathArtistEntity2(Long.toString(authorId)));
		if (obj == null || obj2 == null) {
			return (null);
		}
		return (EntityBuilder.createUser(api, obj, obj2));
	}

	@Override
	public String getAuthorId() {
		return (Long.toString(authorId));
	}

	@Override
	public long getAuthorIdLong() {
		return (authorId);
	}

	@Override
	public String getAuthorUsername() {
		return (authorUsername);
	}

	@Override
	public String getAuthorPermalink() {
		return (authorPermalink);
	}

	@Override
	public String getId() {
		return (Long.toString(id));
	}

	@Override
	public long getIdLong() {
		return (id);
	}

	@Override
	public String getContentRaw() {
		return (content);
	}

	@Override
	public Calendar getCreationDate() {
		return (creationDate);
	}

	@Override
	public long getTimestamp() {
		return (timestamp);
	}

	@Override
	public int getTimestampSeconds() {
		return (Math.toIntExact(timestamp) / 1000);
	}

	@Override
	public String getTimestampString() {
		return (MiscUtil.secondsToStringFormat(getTimestampSeconds(),true));
	}

	@Override
	public long getTrackDuration() {
		return (trackDuration);
	}

	@Override
	public int getTrackDurationSeconds() {
		return (Math.toIntExact(trackDuration) / 1000);
	}

	@Override
	public String getTrackDurationString() {
		return (MiscUtil.secondsToStringFormat(getTrackDurationSeconds(),true));
	}

	@Override
	public String getTrackId() {
		return (Long.toString(trackId));
	}

	@Override
	public long getTrackIdLong() {
		return (trackId);
	}

	@Override
	public String getTrackPermalink() {
		return (trackPermalink);
	}

	@Override
	public String getTrackTitle() {
		return (trackTitle);
	}

	// -- Setters --

	public CommentImpl setArtistId(long artistId) {
		this.artistId = artistId;
		return (this);
	}

	public CommentImpl setArtistPermalink(String artistPermalink) {
		this.artistPermalink = artistPermalink;
		return (this);
	}

	public CommentImpl setArtistUsername(String artistUsername) {
		this.artistUsername = artistUsername;
		return (this);
	}

	public CommentImpl setAuthorId(long authorId) {
		this.authorId = authorId;
		return (this);
	}

	public CommentImpl setAuthorPermalink(String authorPermalink) {
		this.authorPermalink = authorPermalink;
		return (this);
	}

	public CommentImpl setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
		return (this);
	}

	public CommentImpl setContent(String content) {
		this.content = content;
		return (this);
	}

	public CommentImpl setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
		return (this);
	}

	public CommentImpl setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return (this);
	}

	public CommentImpl setTrackDuration(long trackDuration) {
		this.trackDuration = trackDuration;
		return (this);
	}

	public CommentImpl setTrackId(long trackId) {
		this.trackId = trackId;
		return (this);
	}

	public CommentImpl setTrackPermalink(String trackPermalink) {
		this.trackPermalink = trackPermalink;
		return (this);
	}

	public CommentImpl setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
		return (this);
	}
}
