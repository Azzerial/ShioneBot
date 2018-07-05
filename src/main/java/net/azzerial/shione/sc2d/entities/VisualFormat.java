package net.azzerial.shione.sc2d.entities;

/**
 * Represents the image format of a {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
 */
public enum VisualFormat {
	
	T50x50("t50x50"),	// 50x50
	T67x67("t67x67"),	// 67x67
	T120x120("t120x120"),	// 120x120
	T200x200("t200x200"),	// 200x200
	T250x250("t250x250"),	// 250x250
	T300x300("t300x300"),	// 300x300
	T500x500("t500x500"),	// 500x500
	ORIGINAL("original"),	// Original sizes, not at fixe value.
	UNKNOWN("");		// Empty.
	
	private String format;
	
	VisualFormat(String format) {
		this.format = format;
	}
	
	/**
	 * Gets the actual format of an {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
	 * 
	 * @return A {@code String} representation of the {@link net.azzerial.shione.sc2d.entities.Visual Visual} actual {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat}.
	 */
	public String getFormat() {
		return (format);
	}
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} from the provided key.
	 * <br>If the provided key does not have a matching {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat}, this will return {@link net.azzerial.shione.sc2d.entities.VisualFormat#UNKNOWN UNKONWN}
	 * 
	 * @param key
	 * The key relating to the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} we wish to retrieve.
	 * 
	 * @return The matching {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat}. If there is no match, returns {@link net.azzerial.shione.sc2d.entities.VisualFormat#UNKNOWN UNKONWN}.
	 */
	public static VisualFormat fromFormat(String key) {
		for (VisualFormat visualFormat : values()) {
			if (visualFormat.format.equalsIgnoreCase(key)) {
				return (visualFormat);
			}
		}
		return (UNKNOWN);
	}
}
