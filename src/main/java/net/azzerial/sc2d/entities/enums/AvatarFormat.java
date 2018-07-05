package net.azzerial.sc2d.entities.enums;

import net.azzerial.sc2d.entities.Avatar;

/**
 * Represents the image format of an {@link Avatar Avatar}.
 */
public enum AvatarFormat {

	T16x16("mini"),		// 16x16
	T18x18("tiny"),		// 18x18
	T47x47("badge"),	// 47x47
	T50x50("t50x50"),	// 50x50
	T67x67("t67x67"),	// 67x67
	T100x100("large"),	// 100x100
	T120x120("t120x120"),	// 120x120
	T200x200("t200x200"),	// 200x200
	T250x250("t250x250"),	// 250x250
	T300x300("t300x300"),	// 300x300
	T500x500("t500x500"),	// 500x500
	ORIGINAL("original"),	// Original sizes, not at fixe value.
	UNKNOWN("");		// Empty.
	
	private String format;
	
	AvatarFormat(String format) {
		this.format = format;
	}
	
	/**
	 * Gets the actual format of an {@link Avatar Avatar}.
	 * 
	 * @return A {@code String} representation of the {@link Avatar Avatar} actual {@link AvatarFormat AvatarFormat}.
	 */
	public String getFormat() {
		return (format);
	}
	
	/**
	 * Gets the {@link AvatarFormat AvatarFormat} from the provided key.
	 * <br>If the provided key does not have a matching {@link AvatarFormat AvatarFormat}, this will return {@link AvatarFormat#UNKNOWN UNKONWN}
	 * 
	 * @param key
	 * The key relating to the {@link AvatarFormat AvatarFormat} we wish to retrieve.
	 * 
	 * @return The matching {@link AvatarFormat AvatarFormat}. If there is no match, returns {@link AvatarFormat#UNKNOWN UNKONWN}.
	 */
	public static AvatarFormat fromFormat(String key) {
		for (AvatarFormat avatarFormat : values()) {
			if (avatarFormat.format.equalsIgnoreCase(key)) {
				return (avatarFormat);
			}
		}
		return (UNKNOWN);
	}
}
