package net.azzerial.sc2d.builders;

import net.azzerial.shione.core.Settings;
import net.azzerial.shione.core.Shione;
import net.azzerial.sc2d.core.API;
import net.azzerial.sc2d.core.SC2D;

public class SC2DBuilder {

	private static SC2DBuilder instance;
	private SC2D sc2d;
	
	public SC2DBuilder(Settings settings) {
		// Checks if the settings config contains the clientId and the appVersion to communicate with the SoundCloud's API.
		if (settings.getSoundcloudClientId().isEmpty()
		|| settings.getSoundcloudAppVersion().isEmpty()) {
			System.out.println("[SC2D/SC2DBuilder]: Soundcloud informations are missing! As I can't reach SoundCloud's API I have no other choice than to leave you here.");
			System.exit(Shione.INVALID_INFORMATION_PROVIDED);
		}
		this.sc2d = loadSC2D(settings);
	}
	
	public static SC2DBuilder getInstance(Settings settings) {
		if (instance == null) {
			instance = new SC2DBuilder(settings);
		}
		return (instance);
	}
	
	public SC2D build() {
		return (sc2d);
	}

	private SC2D loadSC2D(Settings settings) {
		return (
			new SC2D(
				new API(
					settings.getSoundcloudClientId(),
					settings.getSoundcloudAppVersion()
				)
			)
		);
	}
	
}
