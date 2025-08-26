package io.github.eaglercraftex;

import net.lax1dude.eaglercraft.v1_8.EaglercraftVersion;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;

public class client {
	private static Client INSTANCE = new Client();
	
	public String NAME = EaglercraftVersion.projectForkName;
	public String VERSION = EaglercraftVersion.projectForkVersion;
	public String VENDOR = EaglercraftVersion.projectForkVendor;
	
	public static Logger logger;
	
	public void start() {
		logger = LogManager.getLogger("eaglercraftex");
		
		logger.info("Loading " + NAME + " " + VERSION);
	}
}
