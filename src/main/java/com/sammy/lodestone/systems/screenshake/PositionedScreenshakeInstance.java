package com.sammy.lodestone.systems.screenshake;

import com.mojang.math.Vector3f;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;

import java.util.random.RandomGenerator;

public class PositionedScreenshakeInstance extends ScreenshakeInstance{
	public Vec3 position;
	public float falloffDistance;
	public float maxDistance;
	public float minDot;
	public final Easing falloffEasing;

	public PositionedScreenshakeInstance(int duration, Vec3 position, float falloffDistance, float minDot, float maxDistance, Easing falloffEasing) {
		super(duration);
		this.position = position;
		this.falloffDistance = falloffDistance;
		this.minDot = minDot;
		this.maxDistance = maxDistance;
		this.falloffEasing = falloffEasing;
	}

	@Override
	public float updateIntensity(Camera camera, RandomGenerator random) {
		float intensity = super.updateIntensity(camera, random);
		float distance = (float) position.distanceTo(camera.getPosition());
		if (distance > maxDistance) {
			return 0;
		}
		float distanceMultiplier = 1;
		if (distance > falloffDistance) {
			float remaining = maxDistance-falloffDistance;
			float current = distance-falloffDistance;
			distanceMultiplier = 1-current/remaining;
		}

		Vector3f lookDirection = camera.getLookVector();
		Vec3 directionToScreenshake = position.subtract(camera.getPosition()).normalize();
		float angle = Math.max(minDot, lookDirection.dot(new Vector3f(directionToScreenshake)));
		return intensity * distanceMultiplier * angle;
	}
}
