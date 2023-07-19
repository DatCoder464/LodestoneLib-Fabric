package com.sammy.lodestone.mixin;

import com.sammy.lodestone.handlers.PostProcessHandler;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {
	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PostChain;process(F)V", ordinal = 1))
	public void lodestone$injectionBeforeTransparencyChainProcess(CallbackInfo ci) {
		PostProcessHandler.copyDepthBuffer();
	}

}
