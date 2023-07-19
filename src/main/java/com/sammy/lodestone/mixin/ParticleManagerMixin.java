package com.sammy.lodestone.mixin;

import com.google.common.collect.ImmutableList;
import com.sammy.lodestone.systems.rendering.particle.ParticleTextureSheets;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ParticleEngine.class)
final class ParticleManagerMixin {
	@Mutable
	@Final
	@Shadow
	private static List<ParticleRenderType> RENDER_ORDER;

	@Inject(at = @At("RETURN"), method = "<clinit>")
	private static void lodestone$addTypes(CallbackInfo ci) {
		RENDER_ORDER = ImmutableList.<ParticleRenderType>builder().addAll(RENDER_ORDER)
				.add(ParticleTextureSheets.ADDITIVE, ParticleTextureSheets.TRANSPARENT)
				.build();
	}
}
