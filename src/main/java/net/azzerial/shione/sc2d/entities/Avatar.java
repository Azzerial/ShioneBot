package net.azzerial.shione.sc2d.entities;

/**
 * Represents a SoundCloud {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}.
 * <br>This should contain all information provided from SoundCloud about a {@link net.azzerial.shione.sc2d.entities.impl.Artist User}'s Avatar.
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
	 * Gets the Url of the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.AvatarFormat AvatarFormat} is equal to {@link net.azzerial.shione.sc2d.entities.AvatarFormat#UNKNOWN UNKONWN}.
	 * 
	 * @return Possibly-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar} Url.
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
	 * Gets the Soundcloud id of this {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}.
	 * 
	 * @return Never-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar} id.
	 */
	public String getId() {
		return (id);
	}
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.AvatarFormat AvatarFormat} of this {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}.
	 * 
	 * @return The {@link net.azzerial.shione.sc2d.entities.AvatarFormat AvatarFormat} of the {@link net.azzerial.shione.sc2d.entities.Avatar Avatar}.
	 */
	public AvatarFormat getFormat() {
		return (format);
	}
	
	/**
	 * Sets the value of the {@link net.azzerial.shione.sc2d.entities.AvatarFormat AvatarFormat} of this {@link net.azzerial.shione.sc2d.entities.Avatar Avatar} to the given <em>format</em> value.
	 * 
	 * @param format
	 * The new value of the {@link net.azzerial.shione.sc2d.entities.AvatarFormat AvatarFormat}.
	 */
	public Avatar setFormat(AvatarFormat format) {
		this.format = format;
		return (this);
	}
	
}
