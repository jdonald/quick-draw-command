package com.quickdraw;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quickdraw Commands - A Fabric mod that executes commands automatically upon joining a server.
 * This is useful for servers that require specific commands to be run on login,
 * such as teleporting to a home town on Towny servers.
 */
public class QuickdrawCommands implements ModInitializer {
    public static final String MOD_ID = "quickdraw-commands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Quickdraw Commands initialized - Common");
    }
}
