package net.azzerial.sc2d.entities;

import net.azzerial.sc2d.entities.enums.AvatarFormat;

/**
 * Represents a SoundCloud {@link Avatar Avatar}.
 * <br>This should contain all information provided from SoundCloud about a {@link Artist User}'s Avatar.
 */
public class Avatar {

	private String id;
	private AvatarFormat format;

	private String urlPrefix = "https://i1.sndcdn.com/avatars-";
	private String urlSuffix = ".jpg";
	
	public Avatar(String avatarId) {
		this.id = avatarId;
		this.format = AvatarFormat.T500x500;
	}
	
	/**
	 * Gets the Url of the {@link Avatar Avatar}.
	 * <br>Returns {@code null} if the {@link AvatarFormat AvatarFormat} is equal to {@link AvatarFormat#UNKNOWN UNKONWN}.
	 * 
	 * @return Possibly-null {@code String} containing the {@link Avatar Avatar} Url.
	 */
	public String getUrl() {
		String avatarUrl;
		
		if (format.getFormat().equalsIgnoreCase(AvatarFormat.UNKNOWN.getFormat())) {
			return (null);
		}
		avatarUrl = urlPrefix;
		avatarUrl += id;
		avatarUrl += "-";
		avatarUrl += format.getFormat();
		avatarUrl += urlSuffix;
		return (avatarUrl);
	}
	
	/**
	 * Gets the Soundcloud id of this {@link Avatar Avatar}.
	 * 
	 * @return Never-null {@code String} containing the {@link Avatar Avatar} id.
	 */
	public String getId() {
		return (id);
	}
	
	/**
	 * Gets the {@link AvatarFormat AvatarFormat} of this {@link Avatar Avatar}.
	 * 
	 * @return The {@link AvatarFormat AvatarFormat} of the {@link Avatar Avatar}.
	 */
	public AvatarFormat getFormat() {
		return (format);
	}
	
	/**
	 * Sets the value of the {@link AvatarFormat AvatarFormat} of this {@link Avatar Avatar} to the given <em>format</em> value.
	 * 
	 * @param format
	 * The new value of the {@link AvatarFormat AvatarFormat}.
	 */
	public Avatar setFormat(AvatarFormat format) {
		this.format = format;
		return (this);
	}
	
}
