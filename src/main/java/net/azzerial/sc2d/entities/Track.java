package net.azzerial.sc2d.entities;

import java.util.Calendar;
import java.util.List;

@SuppressWarnings("unused")
public abstract interface Track {

	// -- NEED JAVADOCS --

	Artist getArtist();
	String getArtistId();
	long getArtistIdLong();
	String getArtistPermalink();
	String getArtistUsername();
	long getCommentsCount();
	Calendar getCreationDate(); // Choose the display date (on soundcloud release and not checking)
	String getDescription();
	long getDownloadCount();
	String getDownloadUrl();
	long getDuration();
	int getDurationSeconds();
	String getDurationString();
	String getGenre();
	long getLikesCount();
	String getPermalink();
	String getPermalinkUrl();
	long getPlaybackCount();
	long getRepostsCount();
	String getStreamUrl();
	String getStreamPreviewUrl();
	List<String> getTags();
	String getTitle();
	String getTrackId();
	long getTrackIdLong();
	String getWaveformImageUrl();
	boolean isCommentable();
	boolean isDownloadable();
	boolean isPublic();
	boolean isStreamable();

	// -- IDEAS --
/*
 *	List<Comment> getComment();
 *	List<Artist> getFavoriters();
 *	List<Comment> getReposters();
 *	Avatar getArtistAvatar();
 *	Calendar getLastModificationDate();
 *
 *	artwork + track visuals
 *	Waveform getWaveformData();
 *
 *	is in album / playlist ?
 *	purchase (title + url)
 *	state of the track (“processing” * “failed” * “finished”)
 */

}
