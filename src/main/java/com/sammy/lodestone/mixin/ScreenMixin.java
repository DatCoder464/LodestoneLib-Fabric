package com.sammy.lodestone.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_UI;

@Mixin(Screen.class)
final class ScreenMixin {
	@Inject(at = @At("HEAD"), method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;)V")
	private void lodestone$beforeBackgroundParticle(PoseStack poseStack, CallbackInfo ci) {
		ScreenParticleHandler.renderParticles(BEFORE_UI);
	}
}
