package com.sammy.lodestone.setup;

import com.sammy.lodestone.helpers.DataHelper;
import com.sammy.lodestone.mixin.FabricSpriteProviderImplAccessor;
import com.sammy.lodestone.systems.rendering.particle.screen.ScreenParticleEffect;
import com.sammy.lodestone.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class LodestoneScreenParticles {
	public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();
	public static final ScreenParticleType<ScreenParticleEffect> WISP = registerType(new LodestoneScreenParticleType());
	public static final ScreenParticleType<ScreenParticleEffect> SMOKE = registerType(new LodestoneScreenParticleType());
	public static final ScreenParticleType<ScreenParticleEffect> SPARKLE = registerType(new LodestoneScreenParticleType());
	public static final ScreenParticleType<ScreenParticleEffect> TWINKLE = registerType(new LodestoneScreenParticleType());
	public static final ScreenParticleType<ScreenParticleEffect> STAR = registerType(new LodestoneScreenParticleType());

	public static void registerParticleFactories() {
		registerProvider(WISP, new LodestoneScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wisp"))));
		registerProvider(SMOKE, new LodestoneScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("smoke"))));
		registerProvider(SPARKLE, new LodestoneScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("sparkle"))));
		registerProvider(TWINKLE, new LodestoneScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("twinkle"))));
		registerProvider(STAR, new LodestoneScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("star"))));
	}

	public static <T extends ScreenParticleEffect> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
		PARTICLE_TYPES.add(type);
		return type;
	}

	public static <T extends ScreenParticleEffect> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.Factory<T> provider) {
		type.factory = provider;
	}

	public static SpriteSet getSpriteSet(ResourceLocation resourceLocation) {
		final Minecraft client = Minecraft.getInstance();
		return FabricSpriteProviderImplAccessor.FabricSpriteProviderImpl(client.particleEngine, client.particleEngine.spriteSets.get(resourceLocation));
	}

}
