package com.mmh1146.menuremove.client.mixin;

import com.mmh1146.menuremove.client.config.GameMenuConfig;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.GridLayout;
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
		method = "addFeedbackButtons",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"
		)
	)
	private static LayoutElement filterBugFeedbackButtons(GridLayout.RowHelper rowHelper, LayoutElement element) {
		if (GameMenuConfig.isEnabled() && isBugOrFeedback(element)) {
			// Not added to the layout; the non-null return keeps the caller's
			// checkcast/putfield on the bug button from throwing
			return element;
		}
		return rowHelper.addChild(element);
	}

	private static boolean isBugOrFeedback(LayoutElement element) {
		if (element instanceof AbstractWidget widget) {
			ComponentContents contents = widget.getMessage().getContents();
			if (contents instanceof TranslatableContents translatable) {
				String key = translatable.getKey();
				return "menu.reportBugs".equals(key) || "menu.sendFeedback".equals(key);
			}
		}
		return false;
	}
}
