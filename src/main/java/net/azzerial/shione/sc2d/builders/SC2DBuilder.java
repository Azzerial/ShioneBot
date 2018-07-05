package net.azzerial.shione.sc2d.builders;

import net.azzerial.shione.core.Settings;
import net.azzerial.shione.core.Shione;
import net.azzerial.shione.sc2d.API;
import net.azzerial.shione.sc2d.SC2D;

public class SC2DBuilder {

	private static SC2DBuilder instance;
	private SC2D sc2d;
	
	public SC2DBuilder(Settings settings) {
		if (settings.getSoundcloudClientId().isEmpty()
		|| settings.getSoundcloudAppVersion().isEmpty()) {
			System.out.println("[SC2D/SC2DBuilder]: Soundcloud informations are missing! Fuck this shit I'm out.");
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
	
	public SC2D getSC2D() {
		return (sc2d);
	}

	private SC2D loadSC2D(Settings settings) {
		SC2D new_sc2d = new SC2D(
			new API(settings.getSoundcloudClientId(),
				settings.getSoundcloudAppVersion())
		);
		return (new_sc2d);
	}
	
}
