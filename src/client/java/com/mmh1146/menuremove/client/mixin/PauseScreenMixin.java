package com.mmh1146.menuremove.client.mixin;

import com.mmh1146.menuremove.client.config.GameMenuConfig;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
	@Inject(method = "createPauseMenu", at = @At("HEAD"))
	private void reloadConfig(CallbackInfo ci) {
		GameMenuConfig.load();
	}

	@Redirect(
		method = "createPauseMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/layouts/LinearLayout;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"
		)
	)
	private LayoutElement filterBugFeedbackButtons(LinearLayout layout, LayoutElement element) {
		if (!GameMenuConfig.isEnabled()) {
			return layout.addChild(element);
		}
		if (element instanceof AbstractWidget widget) {
			ComponentContents contents = widget.getMessage().getContents();
			if (contents instanceof TranslatableContents translatable) {
				String key = translatable.getKey();
				if ("menu.reportBugs".equals(key) || "menu.sendFeedback".equals(key)) {
					return null;
				}
			}
		}
		return layout.addChild(element);
	}
}
