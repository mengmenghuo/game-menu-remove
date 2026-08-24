package com.mmh1146.menuremove.client.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {
	@Redirect(
		method = "createPauseMenu",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/layouts/LinearLayout;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"
		)
	)
	private LayoutElement filterBugFeedbackButtons(LinearLayout layout, LayoutElement element) {
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
