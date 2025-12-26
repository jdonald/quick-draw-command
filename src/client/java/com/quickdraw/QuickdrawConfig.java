package com.quickdraw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration manager for Quickdraw Commands.
 * Handles loading/saving config to JSON file and provides access to settings.
 */
public class QuickdrawConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuickdrawCommands.MOD_ID);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("quickdraw-commands.json");

    private static ConfigData config = new ConfigData();

    /**
     * Configuration data class that gets serialized to JSON.
     */
    public static class ConfigData {
        public boolean enabled = true;
        public String command1 = "/t spawn";
        public String command2 = "";
        public String command3 = "";
        public String command4 = "";
        public String command5 = "";
        public int cooldownSeconds = 0;
    }

    /**
     * Load configuration from file. Creates default config if it doesn't exist.
     */
    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                config = GSON.fromJson(reader, ConfigData.class);
                if (config == null) {
                    config = new ConfigData();
                }
                LOGGER.info("Loaded Quickdraw Commands config");
            } catch (IOException e) {
                LOGGER.error("Failed to load config, using defaults", e);
                config = new ConfigData();
            }
        } else {
            config = new ConfigData();
            save();
            LOGGER.info("Created default Quickdraw Commands config");
        }
    }

    /**
     * Save current configuration to file.
     */
    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
            LOGGER.info("Saved Quickdraw Commands config");
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    // Getters and setters

    public static boolean isEnabled() {
        return config.enabled;
    }

    public static void setEnabled(boolean enabled) {
        config.enabled = enabled;
    }

    public static String getCommand1() {
        return config.command1;
    }

    public static void setCommand1(String command) {
        config.command1 = command != null ? command : "";
    }

    public static String getCommand2() {
        return config.command2;
    }

    public static void setCommand2(String command) {
        config.command2 = command != null ? command : "";
    }

    public static String getCommand3() {
        return config.command3;
    }

    public static void setCommand3(String command) {
        config.command3 = command != null ? command : "";
    }

    public static String getCommand4() {
        return config.command4;
    }

    public static void setCommand4(String command) {
        config.command4 = command != null ? command : "";
    }

    public static String getCommand5() {
        return config.command5;
    }

    public static void setCommand5(String command) {
        config.command5 = command != null ? command : "";
    }

    public static int getCooldownSeconds() {
        return config.cooldownSeconds;
    }

    public static void setCooldownSeconds(int seconds) {
        config.cooldownSeconds = Math.max(0, seconds);
    }

    public static int getCooldownMs() {
        return config.cooldownSeconds * 1000;
    }

    /**
     * Get all commands as an array.
     */
    public static String[] getCommands() {
        return new String[]{
                config.command1,
                config.command2,
                config.command3,
                config.command4,
                config.command5
        };
    }

    /**
     * Get the raw config data for the config screen.
     */
    public static ConfigData getConfigData() {
        return config;
    }
}
