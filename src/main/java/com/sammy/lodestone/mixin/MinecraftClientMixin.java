package com.sammy.lodestone.mixin;

import com.sammy.lodestone.handlers.ScreenParticleHandler;
import com.sammy.lodestone.handlers.ScreenshakeHandler;
import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.setup.LodestoneScreenParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.lodestone.LodestoneLib.RANDOM;

@Mixin(Minecraft.class)
final class MinecraftClientMixin {
	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;registerReloadListener(Lnet/minecraft/server/packs/resources/PreparableReloadListener;)V", ordinal = 17))
	private void lodestone$registerParticleFactories(GameConfig runArgs, CallbackInfo ci) {
		LodestoneParticles.registerFactories();
		LodestoneScreenParticles.registerParticleFactories();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void lodestone$clientTick(CallbackInfo ci) {
		ScreenParticleHandler.clientTick();
		ScreenshakeHandler.clientTick(Minecraft.getInstance().gameRenderer.getMainCamera(), RANDOM);
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", ordinal = 4, shift = At.Shift.AFTER))
	private void lodestone$renderTickThingamajig(boolean tick, CallbackInfo ci) {
		ScreenParticleHandler.renderParticles();
	}
}
