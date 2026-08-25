package com.mmh1146.menuremove.client.mixin;

import com.mmh1146.menuremove.client.config.GameMenuConfig;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
	// In 26.1 this method only adds the "Give Feedback" and "Report Bugs" buttons
	@Inject(method = "addFeedbackButtons", at = @At("HEAD"), cancellable = true)
	private static void removeFeedbackButtons(CallbackInfo ci) {
		if (GameMenuConfig.isEnabled()) {
			ci.cancel();
		}
	}

	@Inject(method = "createPauseMenu", at = @At("HEAD"))
	private void reloadConfig(CallbackInfo ci) {
		GameMenuConfig.load();
	}
}
