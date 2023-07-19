package com.sammy.lodestone.systems.rendering.particle.type;

import com.sammy.lodestone.systems.rendering.particle.screen.GenericScreenParticle;
import com.sammy.lodestone.systems.rendering.particle.screen.ScreenParticleEffect;
import com.sammy.lodestone.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.world.level.Level;

public class LodestoneScreenParticleType extends ScreenParticleType<ScreenParticleEffect> {

	public LodestoneScreenParticleType() {
		super();
	}

	public static class Factory implements ScreenParticleType.Factory<ScreenParticleEffect> {
		public final SpriteSet sprite;

		public Factory(SpriteSet sprite) {
			this.sprite = sprite;
		}

		@Override
		public ScreenParticle createParticle(Level clientWorld, ScreenParticleEffect options, double pX, double pY, double pXSpeed, double pYSpeed) {
			return new GenericScreenParticle(clientWorld, options, (FabricSpriteProviderImpl) sprite, pX, pY, pXSpeed, pYSpeed);
		}
	}
}
