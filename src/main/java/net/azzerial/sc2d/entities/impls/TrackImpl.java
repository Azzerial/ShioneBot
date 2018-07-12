package net.azzerial.sc2d.entities.impls;

import net.azzerial.sc2d.builders.EntityBuilder;
import net.azzerial.sc2d.core.API;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.sc2d.entities.Track;
import net.azzerial.sc2d.utils.MiscUtil;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

@SuppressWarnings("unused")
public class TrackImpl implements Track {

	private API api;
	private long id;

	private long artistId;
	private String artistPermalink;
	private String artistUsername;
	private long commentsCount;
	private Calendar creationDate;
	private String description;
	private long downloadCount;
	private String downloadUrl;
	private long duration;
	private long likesCount;
	private String genre;
	private String permalink;
	private long playbackCount;
	private long repostsCount;
	private String streamUrl;
	private String streamPreviewUrl;
	private List<String> tags;
	private String title;
	private String waveformImage;
	private boolean isCommentable;
	private boolean isDownloadable;
	private boolean isPublic;
	private boolean isStreamable;

	public TrackImpl(API api, long id) {
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
	public long getCommentsCount() {
		return (commentsCount);
	}

	@Override
	public Calendar getCreationDate() {
		return (creationDate);
	}

	@Override
	public String getDescription() {
		return (description);
	}

	@Override
	public long getDownloadCount() {
		return (downloadCount);
	}

	@Override
	public String getDownloadUrl() {
		return (downloadUrl);
	}

	@Override
	public long getDuration() {
		return (duration);
	}

	@Override
	public int getDurationSeconds() {
		return (Math.toIntExact(duration / 1000));
	}

	@Override
	public String getDurationString() {
		return (MiscUtil.secondsToStringFormat(getDurationSeconds(), true));
	}

	@Override
	public String getGenre() {
		return (genre);
	}

	@Override
	public long getLikesCount() {
		return (likesCount);
	}

	@Override
	public String getPermalink() {
		return (permalink);
	}

	@Override
	public String getPermalinkUrl() {
		return (API.SOUNDCLOUD_URL + permalink);
	}

	@Override
	public long getPlaybackCount() {
		return (playbackCount);
	}

	@Override
	public long getRepostsCount() {
		return (repostsCount);
	}

	@Override
	public String getStreamUrl() {
		return (streamUrl);
	}

	@Override
	public String getStreamPreviewUrl() {
		return (streamPreviewUrl);
	}

	@Override
	public List<String> getTags() {
		return (tags);
	}

	@Override
	public String getTitle() {
		return (title);
	}

	@Override
	public String getTrackId() {
		return (Long.toString(id));
	}

	@Override
	public long getTrackIdLong() {
		return (id);
	}

	@Override
	public String getWaveformImageUrl() {
		return (waveformImage);
	}

	@Override
	public boolean isCommentable() {
		return (isCommentable);
	}

	@Override
	public boolean isDownloadable() {
		return (isDownloadable);
	}

	@Override
	public boolean isPublic() {
		return (isPublic);
	}

	@Override
	public boolean isStreamable() {
		return (isStreamable);
	}

	// -- Setters --

	public TrackImpl setArtistId(long artistId) {
		this.artistId = artistId;
		return (this);
	}

	public TrackImpl setArtistPermalink(String artistPermalink) {
		this.artistPermalink = artistPermalink;
		return (this);
	}

	public TrackImpl setArtistUsername(String artistUsername) {
		this.artistUsername = artistUsername;
		return (this);
	}

	public TrackImpl setCommentsCount(long commentsCount) {
		this.commentsCount = commentsCount;
		return (this);
	}

	public TrackImpl setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
		return (this);
	}

	public TrackImpl setDescription(String description) {
		this.description = description;
		return (this);
	}

	public TrackImpl setDownloadCount(long downloadCount) {
		this.downloadCount = downloadCount;
		return (this);
	}

	public TrackImpl setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
		return (this);
	}

	public TrackImpl setDuration(long duration) {
		this.duration = duration;
		return (this);
	}

	public TrackImpl setGenre(String genre) {
		this.genre = genre;
		return (this);
	}

	public TrackImpl setLikesCount(long likesCount) {
		this.likesCount = likesCount;
		return (this);
	}

	public TrackImpl setPermalink(String permalink) {
		this.permalink = permalink;
		return (this);
	}

	public TrackImpl setPlaybackCount(long playbackCount) {
		this.playbackCount = playbackCount;
		return (this);
	}

	public TrackImpl setRepostsCount(long repostsCount) {
		this.repostsCount = repostsCount;
		return (this);
	}

	public TrackImpl setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
		return (this);
	}

	public TrackImpl setStreamPreviewUrl(String streamPreviewUrl) {
		this.streamPreviewUrl = streamPreviewUrl;
		return (this);
	}

	public TrackImpl setTags(List<String> tags) {
		this.tags = tags;
		return (this);
	}

	public TrackImpl setTitle(String title) {
		this.title = title;
		return (this);
	}

	public TrackImpl setWaveformImageUrl(String waveformImage) {
		this.waveformImage = waveformImage;
		return (this);
	}

	public TrackImpl setCommentable(boolean isCommentable) {
		this.isCommentable = isCommentable;
		return (this);
	}

	public TrackImpl setDownloadable(boolean isDownloadable) {
		this.isDownloadable = isDownloadable;
		return (this);
	}

	public TrackImpl setPublic(boolean isPublic) {
		this.isPublic = isPublic;
		return (this);
	}

	public TrackImpl setStreamable(boolean isStreamable) {
		this.isStreamable = isStreamable;
		return (this);
	}
}
