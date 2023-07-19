package com.sammy.lodestone.handlers;

import com.sammy.lodestone.config.ClientConfig;
import com.sammy.lodestone.systems.screenshake.ScreenshakeInstance;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class ScreenshakeHandler {
	private static final ImprovedNoise sampler = new ImprovedNoise(new LegacyRandomSource(new Random().nextLong()));
	public static final ArrayList<ScreenshakeInstance> INSTANCES = new ArrayList<>();
	public static float intensity;
	public static float yawOffset;
	public static float pitchOffset;

	public static void cameraTick(Camera camera, RandomGenerator random) {
		if (intensity >= 0.1) {
			yawOffset = randomizeOffset(10);
			pitchOffset = randomizeOffset(-10);
			camera.setRotation(camera.getYRot() + yawOffset, camera.getXRot() + pitchOffset);
		}
	}

	public static void clientTick(Camera camera, RandomGenerator random) {
		double sum = Math.min(INSTANCES.stream().mapToDouble(i1 -> i1.updateIntensity(camera, random)).sum(), ClientConfig.SCREENSHAKE_INTENSITY);

		intensity = (float) Math.pow(sum, 3);
		INSTANCES.removeIf(i -> i.progress >= i.duration);
	}

	public static void addScreenshake(ScreenshakeInstance instance) {
		INSTANCES.add(instance);
	}

	public static float randomizeOffset(int offset) {
		float min = -intensity * 2;
		float max = intensity * 2;
		float sampled = (float) sampler.noise((Minecraft.getInstance().level.getDayTime() % 24000L + Minecraft.getInstance().getFrameTime())/intensity, offset, 0) * 1.5f;
		return min >= max ? min : sampled * max;
	}
}
