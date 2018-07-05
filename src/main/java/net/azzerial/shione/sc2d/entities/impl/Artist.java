package net.azzerial.shione.sc2d.entities.impl;

import java.util.Calendar;

import net.azzerial.shione.sc2d.entities.Avatar;
import net.azzerial.shione.sc2d.entities.Visual;

/**
 * Represents a SoundCloud {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
 * <br>This should contain all the information provided from SoundCloud about a {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
 * <br>
 * <br>Depending on the SoundCloud {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}, some data may be {@code null}.
 */
public interface Artist {

	/**
	 * Gets the {@link java.util.Calendar Date} this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}'s account has been created.
	 * 
	 * @return The {@link java.util.Calendar Date} the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}'s account has been created.
	 */
	Calendar getRegistrationDate();
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar} of this{@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 * 
	 * @return The {@link net.azzerial.shione.sc2d.entities.Avatar Avatar} of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 */
	Avatar getAvatar();
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}'s URL of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 * 
	 * @return Never-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}'s URL.
	 */
	String getAvatarDefaultUrl();
	
	/**
	 * Gets the city of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The city of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns an empty {@code String} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a city.
	 */
	String getCity();
	
	/**
	 * Gets the amount of comments written by this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of comments written by this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getCommentsCount();
	
	/**
	 * Gets the country of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The country of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a country.
	 */
	String getCountry();
	
	/**
	 * Gets the country code of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The country code of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a country.
	 */
	String getCountryCode();
	
	/**
	 * Gets the description of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The description of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} doesn't have a description.
	 */
	String getDescription();
	
	/**
	 * Gets the first name of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The first name of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a first name.
	 */
	String getFirstName();
	
	/**
	 * Gets the last name of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The last name of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a last name.
	 */
	String getLastName();
	
	/**
	 * Gets the amount of followers this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has.
	 * 
	 * @return {@code long} representation of the amount of followers this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has.
	 */
	long getFollowersCount();

	/**
	 * Gets the amount of followings this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of followings of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getFollowingsCount();
	
	/**
	 * Gets the full name of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The full name of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} hasn't registered a first name and a last name.
	 */
	String getFullName();
	
	/**
	 * Gets the SoundCloud id of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return Never-null {@code String} representation of a SoundCloud id of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	String getId();
	
	/**
	 * Gets the SoundCloud id of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return Never-null {@code long} representation of a SoundCloud id of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getIdLong();
	
	/**
	 * Gets the total amount of likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return {@code long} representation of the total amount of likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getLikesCount();
	
	/**
	 * Gets the page url of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The page url of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	String getPageUrl();
	
	/**
	 * Gets the SoundCloud permalink of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The SoundCloud permalink of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	String getPermalink();
	
	/**
	 * Gets the SoundCloud plan subscription type of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The SoundCloud plan subscription type of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	String getPlanSubscription();
	
	/**
	 * Gets the amount of playlists this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has posted.
	 * 
	 * @return {@code long} representation of the amount of playlists this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has posted.
	 */
	long getPlaylistsCount();
	
	/**
	 * Gets the amount of playlist likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of playlist likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getPlaylistsLikesCount();
	
	/**
	 * Gets the amount of tracks, playlists or album reposts of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}. 
	 * 
	 * @return {@code long} representation of the amount of tracks, playlists or album reposts of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getRepostsCount();
	
	/**
	 * Gets the amount of tracks this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has posted.
	 * 
	 * @return {@code long} representation of the amount of tracks this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}} has posted.
	 */
	long getTracksCount();
	
	/**
	 * Gets the amount of track likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return {@code long} representation of the amount of track likes of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	long getTracksLikesCount();
	
	/**
	 * Gets the username of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 * 
	 * @return The username of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}}.
	 */
	String getUsername();
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.Visual Visual} of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 * 
	 * @return The {@link net.azzerial.shione.sc2d.entities.Visual Visual} of the {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 */
	Visual getVisual();
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.Visual Visual}'s URL of this {@link net.azzerial.shione.sc2d.entities.impl.Artist Artist}.
	 * 
	 * @return Never-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Visual Visual}'s URL.
	 */
	String getVisualDefaultUrl();
	
//	String getGender();
//	String getKind();
//	String getLocale();
//	String getPermalinkUrl();
//	String getUriForSoundCloudApi();
//	String getUriForSoundCloudApiV2();
//	boolean isVerified();
	
	/*
	 * User can be get by:
	 * Id
	 * URL
	 * permalink
	 * 
	 * Username
	 * 
	 * User has:
	 * 
	 * Avatar			DONE
	 * City
	 * Country
	 * Description
	 * First name
	 * Last name
	 * Full name
	 * Gender
	 * Id
	 * Kind
	 * Locale
	 * Permalink
	 * Permalink URL
	 * URI
	 * URN
	 * URL
	 * Username
	 * Verified
	 * Visuals
	 * 	urnID
	 * 	token
	 * 		-> si ajoutés alors à Avatar aussi
	 * 
	 * 
	 * 
	 * Account creation date
	 * Consumer subscription
	 * 	product
	 * 		id
	 * 		name
	 * 	recurring
	 * Creator subscription
	 * 	product
	 * 		id
	 * 		name
	 * 	recurring
	 * Groups count
	 * Last modified
	 * 
	 * 
	 * 
	 * Comments count
	 * Followers count
	 * Following count
	 * Likes count
	 * Playlist likes count
	 * Playlist count
	 * Private playlist count
	 * Reposts count
	 * Track count
	 * Blocked track count
	 */
	
}
