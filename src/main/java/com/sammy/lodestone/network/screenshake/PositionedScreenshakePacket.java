package com.sammy.lodestone.network.screenshake;

import com.sammy.lodestone.LodestoneLib;
import com.sammy.lodestone.handlers.ScreenshakeHandler;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.screenshake.PositionedScreenshakeInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PositionedScreenshakePacket extends ScreenshakePacket {

	public static final ResourceLocation ID = new ResourceLocation(LodestoneLib.MODID, "positionedscreenshake");

	public final Vec3 position;
	public final float falloffDistance;
	public final float minDot;
	public final float maxDistance;
	public final Easing falloffEasing;

	public PositionedScreenshakePacket(int duration, Vec3 position, float falloffDistance, float minDot, float maxDistance, Easing falloffEasing) {
		super(duration);
		this.position = position;
		this.falloffDistance = falloffDistance;
		this.minDot = minDot;
		this.maxDistance = maxDistance;
		this.falloffEasing = falloffEasing;
	}

	public static PositionedScreenshakePacket fromBuf(FriendlyByteBuf buf) {
		return ((PositionedScreenshakePacket) new PositionedScreenshakePacket(
				buf.readInt(),
				new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()),
				buf.readFloat(),
				buf.readFloat(),
				buf.readFloat(),
				Easing.valueOf(buf.readUtf())
		).setIntensity(
				buf.readFloat(),
				buf.readFloat(),
				buf.readFloat()
		).setEasing(
				Easing.valueOf(buf.readComponent().getString()),
				Easing.valueOf(buf.readComponent().getString())
		));
	}

	public PositionedScreenshakePacket(int duration, Vec3 position, float falloffDistance, float maxDistance) {
		this(duration, position, falloffDistance, 0f, maxDistance, Easing.LINEAR);
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(duration);
		buf.writeDouble(position.x);
		buf.writeDouble(position.y);
		buf.writeDouble(position.z);
		buf.writeFloat(falloffDistance);
		buf.writeFloat(minDot);
		buf.writeFloat(maxDistance);
		buf.writeUtf(falloffEasing.name);
		buf.writeFloat(intensity1);
		buf.writeFloat(intensity2);
		buf.writeFloat(intensity3);
		buf.writeUtf(intensityCurveStartEasing.name);
		buf.writeUtf(intensityCurveEndEasing.name);
	}

	@Override
	public void handle(ClientGamePacketListener listener) {
		ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(duration, position, falloffDistance, minDot, maxDistance, falloffEasing).setIntensity(intensity1, intensity2, intensity3).setEasing(intensityCurveStartEasing, intensityCurveEndEasing));
	}
}
