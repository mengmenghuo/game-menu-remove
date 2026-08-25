package com.mmh1146.menuremove.client.config;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GameMenuConfigScreen extends Screen {
	private static final Component TITLE = Component.literal("Game Menu Remove");

	private final Screen parent;

	public GameMenuConfigScreen(Screen parent) {
		super(TITLE);
		this.parent = parent;
	}

	@Override
	protected void init() {
		Button toggle = Button.builder(enabledLabel(), button -> {
			GameMenuConfig.setEnabled(!GameMenuConfig.isEnabled());
			button.setMessage(enabledLabel());
		}).pos(this.width / 2 - 100, this.height / 2 - 20).width(200).build();
		this.addRenderableWidget(toggle);

		this.addRenderableWidget(
			Button.builder(Component.translatable("gui.done"), button -> this.minecraft.setScreen(this.parent))
				.pos(this.width / 2 - 100, this.height / 2 + 12)
				.width(200)
				.build()
		);
	}

	private static Component enabledLabel() {
		return Component.literal("Enable mod: " + (GameMenuConfig.isEnabled() ? "ON" : "OFF"));
	}
}
