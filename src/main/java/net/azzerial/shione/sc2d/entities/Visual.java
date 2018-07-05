package net.azzerial.shione.sc2d.entities;

/**
 * Represents a SoundCloud {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
 * <br>This should contain all information provided from SoundCloud about a {@link net.azzerial.shione.sc2d.entities.impl.Artist User}'s Visual.
 */
public class Visual {

	private String id;
	private VisualFormat format;

	private String urlPrefix = "https://i1.sndcdn.com/visuals-";
	private String urlSuffix = ".jpg";
	
	public Visual(String visualId) {
		this.id = visualId;
		this.format = VisualFormat.ORIGINAL;
	}
	
	/**
	 * Gets the Url of the {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
	 * <br>Returns {@code null} if the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} is equal to {@link net.azzerial.shione.sc2d.entities.VisualFormat#UNKNOWN UNKONWN}.
	 * 
	 * @return Possibly-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Visual Visual} Url.
	 */
	public String getUrl() {
		String visualUrl;
		
		if (format.getFormat().equalsIgnoreCase(VisualFormat.UNKNOWN.getFormat())) {
			return (null);
		}
		visualUrl = urlPrefix;
		visualUrl += id;
		visualUrl += "-";
		visualUrl += format.getFormat();
		visualUrl += urlSuffix;
		return (visualUrl);
	}
	
	/**
	 * Gets the Soundcloud id of this {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
	 * 
	 * @return Never-null {@code String} containing the {@link net.azzerial.shione.sc2d.entities.Visual Visual} id.
	 */
	public String getId() {
		return (id);
	}
	
	/**
	 * Gets the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} of this {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
	 * 
	 * @return The {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} of the {@link net.azzerial.shione.sc2d.entities.Visual Visual}.
	 */
	public VisualFormat getFormat() {
		return (format);
	}
	
	/**
	 * Sets the value of the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat} of this {@link net.azzerial.shione.sc2d.entities.Visual Visual} to the given <em>format</em> value.
	 * 
	 * @param format
	 * The new value of the {@link net.azzerial.shione.sc2d.entities.VisualFormat VisualFormat}.
	 */
	public Visual setFormat(VisualFormat format) {
		this.format = format;
		return (this);
	}
	
}

