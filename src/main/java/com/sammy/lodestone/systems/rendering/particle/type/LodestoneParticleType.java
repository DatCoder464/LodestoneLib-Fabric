package com.sammy.lodestone.systems.rendering.particle.type;

import com.mojang.serialization.Codec;
import com.sammy.lodestone.systems.rendering.particle.world.GenericParticle;
import com.sammy.lodestone.systems.rendering.particle.world.WorldParticleEffect;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;

public class LodestoneParticleType extends ParticleType<WorldParticleEffect> {
	public LodestoneParticleType() {
		super(false, WorldParticleEffect.DESERIALIZER);
	}

	@Override
	public boolean getOverrideLimiter() {
		return true;
	}

	@Override
	public Codec<WorldParticleEffect> codec() {
		return WorldParticleEffect.codecFor(this);
	}

	public record Factory(SpriteSet sprite) implements ParticleProvider<WorldParticleEffect> {
		@Override
		public Particle createParticle(WorldParticleEffect data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
			return new GenericParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
		}
	}
}
