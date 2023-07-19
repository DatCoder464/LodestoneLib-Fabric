package com.sammy.lodestone.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
final class InGameHudMixin {
	@Shadow @Final private Minecraft minecraft;

	@Inject(at = @At("HEAD"), method = "renderHotbar")
	private void lodestone$renderHotbarStart(float l1, PoseStack j1, CallbackInfo ci) {
		ScreenParticleHandler.renderingHotbar = true;
	}

	@Inject(at = @At("RETURN"), method = "renderHotbar")
	private void lodestone$renderHotbarEnd(float l1, PoseStack j1, CallbackInfo ci) {
		ScreenParticleHandler.renderingHotbar = false;
	}
}
