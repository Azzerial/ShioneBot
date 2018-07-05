package net.azzerial.sc2d.entities;

import net.azzerial.sc2d.entities.enums.VisualFormat;

/**
 * Represents a SoundCloud {@link Visual Visual}.
 * <br>This should contain all information provided from SoundCloud about a {@link Artist User}'s Visual.
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
	 * Gets the Url of the {@link Visual Visual}.
	 * <br>Returns {@code null} if the {@link VisualFormat VisualFormat} is equal to {@link VisualFormat#UNKNOWN UNKONWN}.
	 * 
	 * @return Possibly-null {@code String} containing the {@link Visual Visual} Url.
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
	 * Gets the Soundcloud id of this {@link Visual Visual}.
	 * 
	 * @return Never-null {@code String} containing the {@link Visual Visual} id.
	 */
	public String getId() {
		return (id);
	}
	
	/**
	 * Gets the {@link VisualFormat VisualFormat} of this {@link Visual Visual}.
	 * 
	 * @return The {@link VisualFormat VisualFormat} of the {@link Visual Visual}.
	 */
	public VisualFormat getFormat() {
		return (format);
	}
	
	/**
	 * Sets the value of the {@link VisualFormat VisualFormat} of this {@link Visual Visual} to the given <em>format</em> value.
	 * 
	 * @param format
	 * The new value of the {@link VisualFormat VisualFormat}.
	 */
	public Visual setFormat(VisualFormat format) {
		this.format = format;
		return (this);
	}
	
}

