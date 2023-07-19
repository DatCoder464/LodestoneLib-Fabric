package com.sammy.lodestone.network.screenshake;

import com.sammy.lodestone.LodestoneLib;
import com.sammy.lodestone.handlers.ScreenshakeHandler;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.screenshake.ScreenshakeInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ScreenshakePacket implements Packet<ClientGamePacketListener> {
	public static final ResourceLocation ID = new ResourceLocation(LodestoneLib.MODID, "screenshake");

	public final int duration;
	public float intensity1, intensity2, intensity3;
	public Easing intensityCurveStartEasing = Easing.LINEAR, intensityCurveEndEasing = Easing.LINEAR;

	public ScreenshakePacket(int duration) {
		this.duration = duration;
	}
	public ScreenshakePacket(FriendlyByteBuf buf) {
		duration = buf.readInt();
		intensity1 = buf.readFloat();
		intensity2 = buf.readFloat();
		intensity3 = buf.readFloat();
		setEasing(Easing.valueOf(buf.readUtf()), Easing.valueOf(buf.readUtf()));
	}

	public ScreenshakePacket setIntensity(float intensity) {
		return setIntensity(intensity, intensity);
	}

	public ScreenshakePacket setIntensity(float intensity1, float intensity2) {
		return setIntensity(intensity1, intensity2, intensity2);
	}

	public ScreenshakePacket setIntensity(float intensity1, float intensity2, float intensity3) {
		this.intensity1 = intensity1;
		this.intensity2 = intensity2;
		this.intensity3 = intensity3;
		return this;
	}

	public ScreenshakePacket setEasing(Easing easing) {
		this.intensityCurveStartEasing = easing;
		this.intensityCurveEndEasing = easing;
		return this;
	}

	public ScreenshakePacket setEasing(Easing intensityCurveStartEasing, Easing intensityCurveEndEasing) {
		this.intensityCurveStartEasing = intensityCurveStartEasing;
		this.intensityCurveEndEasing = intensityCurveEndEasing;
		return this;
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(duration);
		buf.writeFloat(intensity1);
		buf.writeFloat(intensity2);
		buf.writeFloat(intensity3);
		buf.writeUtf(intensityCurveStartEasing.name);
		buf.writeUtf(intensityCurveEndEasing.name);
	}

	@Override
	public void handle(ClientGamePacketListener listener) {
		ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(duration).setIntensity(intensity1, intensity2, intensity3).setEasing(intensityCurveStartEasing, intensityCurveEndEasing));
	}
}
