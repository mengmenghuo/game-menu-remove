package com.mmh1146.menuremove.client;

import com.mmh1146.menuremove.client.config.GameMenuConfig;
import net.fabricmc.api.ClientModInitializer;

public class GameMenuRemoveClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GameMenuConfig.load();
	}
}