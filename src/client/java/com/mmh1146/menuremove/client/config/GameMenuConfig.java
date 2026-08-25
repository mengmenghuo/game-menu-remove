package com.mmh1146.menuremove.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class GameMenuConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("game-menu-remove");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("game-menu-remove.json");

	private static boolean enableMod = true;

	private GameMenuConfig() {
	}

	public static void load() {
		try {
			if (!Files.exists(CONFIG_FILE)) {
				save();
			}
			ConfigData data = GSON.fromJson(Files.readString(CONFIG_FILE), ConfigData.class);
			enableMod = data == null || data.enableMod;
		} catch (Exception e) {
			LOGGER.warn("Failed to load config {}, falling back to defaults", CONFIG_FILE, e);
			enableMod = true;
		}
	}

	public static boolean isEnabled() {
		return enableMod;
	}

	public static void setEnabled(boolean enabled) {
		enableMod = enabled;
		try {
			save();
		} catch (IOException e) {
			LOGGER.warn("Failed to save config {}", CONFIG_FILE, e);
		}
	}

	private static void save() throws IOException {
		Files.createDirectories(CONFIG_FILE.getParent());
		ConfigData data = new ConfigData();
		data.enableMod = enableMod;
		Files.writeString(CONFIG_FILE, GSON.toJson(data));
	}

	private static class ConfigData {
		@SerializedName("enable_mod")
		public boolean enableMod = true;
	}
}
