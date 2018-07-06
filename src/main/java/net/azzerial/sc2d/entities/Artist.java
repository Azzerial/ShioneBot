package net.azzerial.sc2d.entities;

import java.util.Calendar;

/**
 * Represents a SoundCloud {@link Artist Artist}.
 * <br>This should contain all the information provided from SoundCloud about a {@link Artist Artist}.
 * <br>
 * <br>Depending on the SoundCloud {@link Artist Artist}, some data may be {@code null}.
 */
public interface Artist {
	
	/**
	 * Gets the {@link Avatar Avatar} of this{@link Artist Artist}.
	 * 
	 * @return The {@link Avatar Avatar} of the {@link Artist Artist}.
	 */
	Avatar getAvatar();
	
	/**
	 * Gets the {@link Avatar Avatar}'s URL of this {@link Artist Artist}.
	 * 
	 * @return Never-null {@code String} containing the {@link Avatar Avatar}'s URL.
	 */
	String getAvatarDefaultUrl();
	
	/**
	 * Gets the city of this {@link Artist Artist}}.
	 * 
	 * @return The city of the {@link Artist Artist}}.
	 * <br>Returns an empty {@code String} if the {@link Artist Artist}} hasn't registered a city.
	 */
	String getCity();
	
	/**
	 * Gets the amount of comments written by this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of comments written by this {@link Artist Artist}}.
	 */
	long getCommentsCount();
	
	/**
	 * Gets the country of this {@link Artist Artist}}.
	 * 
	 * @return The country of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} hasn't registered a country.
	 */
	String getCountry();
	
	/**
	 * Gets the country code of this {@link Artist Artist}}.
	 * 
	 * @return The country code of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} hasn't registered a country.
	 */
	String getCountryCode();

	/**
	 * Gets the {@link java.util.Calendar Date} this {@link Artist Artist}'s account has been created.
	 *
	 * @return The {@link java.util.Calendar Date} the {@link Artist Artist}'s account has been created.
	 */
	Calendar getCreationDate();

	/**
	 * Gets the description of this {@link Artist Artist}}.
	 * 
	 * @return The description of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} doesn't have a description.
	 */
	String getDescription();
	
	/**
	 * Gets the first name of this {@link Artist Artist}}.
	 * 
	 * @return The first name of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} hasn't registered a first name.
	 */
	String getFirstName();
	
	/**
	 * Gets the last name of this {@link Artist Artist}}.
	 * 
	 * @return The last name of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} hasn't registered a last name.
	 */
	String getLastName();
	
	/**
	 * Gets the amount of followers this {@link Artist Artist}} has.
	 * 
	 * @return {@code long} representation of the amount of followers this {@link Artist Artist}} has.
	 */
	long getFollowersCount();

	/**
	 * Gets the amount of followings this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of followings of this {@link Artist Artist}}.
	 */
	long getFollowingsCount();
	
	/**
	 * Gets the full name of this {@link Artist Artist}}.
	 * 
	 * @return The full name of the {@link Artist Artist}}.
	 * <br>Returns {@code null} if the {@link Artist Artist}} hasn't registered a first name and a last name.
	 */
	String getFullName();
	
	/**
	 * Gets the SoundCloud id of this {@link Artist Artist}}.
	 * 
	 * @return Never-null {@code String} representation of a SoundCloud id of the {@link Artist Artist}}.
	 */
	String getId();
	
	/**
	 * Gets the SoundCloud id of this {@link Artist Artist}}.
	 * 
	 * @return Never-null {@code long} representation of a SoundCloud id of the {@link Artist Artist}}.
	 */
	long getIdLong();
	
	/**
	 * Gets the total amount of likes of this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the total amount of likes of this {@link Artist Artist}}.
	 */
	long getLikesCount();
	
	/**
	 * Gets the SoundCloud permalink of this {@link Artist Artist}}.
	 * 
	 * @return The SoundCloud permalink of the {@link Artist Artist}}.
	 */
	String getPermalink();

	/**
	 * Gets the page url of this {@link Artist Artist}}.
	 *
	 * @return The page url of the {@link Artist Artist}}.
	 */
	String getPermalinkUrl();
	
	/**
	 * Gets the SoundCloud plan subscription type of this {@link Artist Artist}}.
	 * 
	 * @return The SoundCloud plan subscription type of the {@link Artist Artist}}.
	 */
	String getPlanSubscription();
	
	/**
	 * Gets the amount of playlists this {@link Artist Artist}} has posted.
	 * 
	 * @return {@code long} representation of the amount of playlists this {@link Artist Artist}} has posted.
	 */
	long getPlaylistsCount();
	
	/**
	 * Gets the amount of playlist likes of this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of playlist likes of this {@link Artist Artist}}.
	 */
	long getPlaylistsLikesCount();
	
	/**
	 * Gets the amount of tracks, playlists or album reposts of this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of tracks, playlists or album reposts of this {@link Artist Artist}}.
	 */
	long getRepostsCount();
	
	/**
	 * Gets the amount of tracks this {@link Artist Artist}} has posted.
	 * 
	 * @return {@code long} representation of the amount of tracks this {@link Artist Artist}} has posted.
	 */
	long getTracksCount();
	
	/**
	 * Gets the amount of track likes of this {@link Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of track likes of this {@link Artist Artist}}.
	 */
	long getTracksLikesCount();
	
	/**
	 * Gets the username of this {@link Artist Artist}}.
	 * 
	 * @return The username of the {@link Artist Artist}}.
	 */
	String getUsername();
	
	/**
	 * Gets the {@link Visual Visual} of this {@link Artist Artist}.
	 * 
	 * @return The {@link Visual Visual} of the {@link Artist Artist}.
	 */
	Visual getVisual();
	
	/**
	 * Gets the {@link Visual Visual}'s URL of this {@link Artist Artist}.
	 * 
	 * @return Never-null {@code String} containing the {@link Visual Visual}'s URL.
	 */
	String getVisualDefaultUrl();
	
}
