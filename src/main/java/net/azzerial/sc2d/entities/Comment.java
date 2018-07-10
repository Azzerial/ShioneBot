package net.azzerial.sc2d.entities;

import java.util.Calendar;

public interface Comment {

	// -- NEED JAVADOCS --

	Artist getArtist();
	String getArtistId();
	long getArtistIdLong();
	String getArtistUsername();
	String getArtistPermalink();
	Artist getAuthor();
	String getAuthorId();
	long getAuthorIdLong();
	String getAuthorUsername();
	String getAuthorPermalink();
	String getContentRaw();
	Calendar getCreationDate();
	String getId();
	long getIdLong();
	long getTimestamp();
	int getTimestampSeconds();
	String getTimestampString();
	long getTrackDuration();
	int getTrackDurationSeconds();
	String getTrackDurationString();
	String getTrackId();
	long getTrackIdLong();
	String getTrackPermalink();
	String getTrackTitle();

	// -- IDEAS --

	/*
	 * Artist avatar
	 * Author avatar
	 * get Track
	 * Track artwork
	 * Track release date
	 */

}
