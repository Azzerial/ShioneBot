package net.azzerial.sc2d.entities.impls;

import java.util.Calendar;

import net.azzerial.sc2d.core.API;
import net.azzerial.sc2d.entities.Artist;
import net.azzerial.sc2d.entities.Avatar;
import net.azzerial.sc2d.entities.Visual;

public class ArtistImpl implements Artist {

	private API api;
	private long id;
	
	private Calendar creationDate;
	private String avatarId;
	private boolean avatarEnabled;
	private String city;
	private long commentsCount;
	private String country;
	private String countryCode;
	private String description;
	private String firstName;
	private long followersCount;
	private long followingsCount;
	private String fullName;
	private String lastName;
	private long likesCount;
	private String pageUrl;
	private String permalink;
	private String planSubscription ;
	private long playlistsCount;
	private long playlistsLikes;
	private long repostsCount;
	private long tracksCount;
	private long tracksLikes;
	private String username;
	private String visualId;
	private boolean visualEnabled;
	
	public ArtistImpl(API api, long id) {
		this.api = api;
		this.id = id;
	}

	// -- Getters --
	
	@Override
	public Avatar getAvatar() {
		if (!avatarEnabled) {
			return (null);
		}
		return (new Avatar(avatarId));
	}

	@Override
	public String getAvatarDefaultUrl() {
		if (!avatarEnabled) {
			return (null);
		}
		return ("https://i1.sndcdn.com/avatars-" + avatarId + "-t500x500.jpg");
	}

	@Override
	public String getCity() {
		return (city);
	}

	@Override
	public long getCommentsCount() {
		return (commentsCount);
	}

	@Override
	public String getCountry() {
		return (country);
	}

	@Override
	public String getCountryCode() {
		return (countryCode);
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
	public String getFirstName() {
		return (firstName);
	}
	
	@Override
	public long getFollowersCount() {
		return (followersCount);
	}

	@Override
	public long getFollowingsCount() {
		return (followingsCount);
	}

	@Override
	public String getFullName() {
		return (fullName);
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
	public String getLastName() {
		return (lastName);
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
		return (pageUrl);
	}

	@Override
	public String getPlanSubscription() {
		return (planSubscription);
	}

	@Override
	public long getPlaylistsCount() {
		return (playlistsCount);
	}

	@Override
	public long getPlaylistsLikesCount() {
		return (playlistsLikes);
	}

	@Override
	public long getRepostsCount() {
		return (repostsCount);
	}

	@Override
	public long getTracksCount() {
		return (tracksCount);
	}

	@Override
	public long getTracksLikesCount() {
		return (tracksLikes);
	}

	@Override
	public String getUsername() {
		return (username);
	}

	@Override
	public Visual getVisual() {
		if (!visualEnabled) {
			return (null);
		}
		return (new Visual(visualId));
	}

	@Override
	public String getVisualDefaultUrl() {
		if (!visualEnabled) {
			return (null);
		}
		return ("https://i1.sndcdn.com/visuals-" + visualId + "-original.jpg");
	}
	
	// -- Setters --
	
	public ArtistImpl setAvatarId(String avatarId) {
		if (avatarId == null) {
			this.avatarId = "";
			this.avatarEnabled = false;
			return (this);
		}
		this.avatarId = avatarId;
		this.avatarEnabled = true;
		return (this);
	}
	public ArtistImpl setCity(String city) {
		this.city = city;
		return (this);
	}
	
	public ArtistImpl setCommentsCount(long commentsCount) {
		this.commentsCount = commentsCount;
		return (this);
	}
	
	public ArtistImpl setCountry(String country) {
		this.country = country;
		return (this);
	}
	
	public ArtistImpl setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return (this);
	}

	public ArtistImpl setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
		return (this);
	}
	
	public ArtistImpl setDescription(String description) {
		this.description = description;
		return (this);
	}
	
	public ArtistImpl setFirstName(String firstName) {
		this.firstName = firstName;
		return (this);
	}
	
	public ArtistImpl setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
		return (this);
	}
	
	public ArtistImpl setFollowingsCount(long followingsCount) {
		this.followingsCount = followingsCount;
		return (this);
	}
	
	public ArtistImpl setFullName(String fullName) {
		this.fullName = fullName;
		return (this);
	}
	
	public ArtistImpl setLastName(String lastName) {
		this.lastName = lastName;
		return (this);
	}
	
	public ArtistImpl setLikesCount(long likesCount) {
		this.likesCount = likesCount;
		return (this);
	}
	
	public ArtistImpl setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
		return (this);
	}
	
	public ArtistImpl setPermalink(String permalink) {
		this.permalink = permalink;
		return (this);
	}
	
	public ArtistImpl setPlanSubscription(String planSubscription) {
		this.planSubscription = planSubscription;
		return (this);
	}
	
	public ArtistImpl setPlaylistsCount(long playlistsCount) {
		this.playlistsCount = playlistsCount;
		return (this);
	}
	
	public ArtistImpl setPlaylistsLikesCount(long playlistsLikes) {
		this.playlistsLikes = playlistsLikes;
		return (this);
	}
	
	public ArtistImpl setRepostsCount(long repostsCount) {
		this.repostsCount = repostsCount;
		return (this);
	}
	
	public ArtistImpl setTracksCount(long tracksCount) {
		this.tracksCount = tracksCount;
		return (this);
	}
	
	public ArtistImpl setTracksLikesCount(long tracksLikes) {
		this.tracksLikes = tracksLikes;
		return (this);
	}
	
	public ArtistImpl setUsername(String username) {
		this.username = username;
		return (this);
	}
	
	public ArtistImpl setVisualId(String visualId) {
		if (visualId == null) {
			this.visualId = "";
			this.visualEnabled = false;
			return (this);
		}
		this.visualId = visualId;
		this.visualEnabled = true;
		return (this);
	}
	
}
