package com.sammy.lodestone.setup;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import com.sammy.lodestone.LodestoneLib;
import com.sammy.lodestone.systems.rendering.ExtendedShader;
import com.sammy.lodestone.systems.rendering.ShaderHolder;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LodestoneShaders {
	public static List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaderList;
	public static ShaderHolder ADDITIVE_TEXTURE = new ShaderHolder();
	public static ShaderHolder LODESTONE_PARTICLE = new ShaderHolder();
	public static ShaderHolder ADDITIVE_PARTICLE = new ShaderHolder();

	public static ShaderHolder MASKED_TEXTURE = new ShaderHolder();
	public static ShaderHolder DISTORTED_TEXTURE = new ShaderHolder("Speed", "TimeOffset", "Intensity", "XFrequency", "YFrequency", "UVCoordinates");
	public static ShaderHolder METALLIC_NOISE = new ShaderHolder("Intensity", "Size", "Speed", "Brightness");
	public static ShaderHolder RADIAL_NOISE = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");
	public static ShaderHolder RADIAL_SCATTER_NOISE = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");

	public static ShaderHolder VERTEX_DISTORTION = new ShaderHolder();
	//public static ShaderHolder BLOOM = new ShaderHolder();

	public static ShaderHolder SCROLLING_TEXTURE = new ShaderHolder("Speed");
	public static ShaderHolder TRIANGLE_TEXTURE = new ShaderHolder();
	public static ShaderHolder COLOR_GRADIENT_TEXTURE = new ShaderHolder("DarkColor");
	public static ShaderHolder SCROLLING_TRIANGLE_TEXTURE = new ShaderHolder("Speed");

	public static void init(ResourceManager manager) throws IOException {
		shaderList = new ArrayList<>();
		registerShader(ExtendedShader.createShaderInstance(LODESTONE_PARTICLE, manager, LodestoneLib.id("particle"), DefaultVertexFormat.PARTICLE));
		registerShader(ExtendedShader.createShaderInstance(ADDITIVE_TEXTURE, manager, LodestoneLib.id("additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(ADDITIVE_PARTICLE, manager, LodestoneLib.id("additive_particle"), DefaultVertexFormat.PARTICLE));

		registerShader(ExtendedShader.createShaderInstance(DISTORTED_TEXTURE, manager, LodestoneLib.id("noise/distorted_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(METALLIC_NOISE, manager, LodestoneLib.id("noise/metallic"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(RADIAL_NOISE, manager, LodestoneLib.id("noise/radial_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(RADIAL_SCATTER_NOISE, manager, LodestoneLib.id("noise/radial_scatter_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));

		registerShader(ExtendedShader.createShaderInstance(SCROLLING_TEXTURE, manager, LodestoneLib.id("vfx/scrolling_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(TRIANGLE_TEXTURE, manager, LodestoneLib.id("vfx/triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
		registerShader(ExtendedShader.createShaderInstance(SCROLLING_TRIANGLE_TEXTURE, manager, LodestoneLib.id("vfx/scrolling_triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
	}
	public static void registerShader(ExtendedShader extendedShaderInstance) {
		registerShader(extendedShaderInstance, (shader) -> ((ExtendedShader) shader).getHolder().setInstance((ExtendedShader) shader));
	}
	public static void registerShader(ShaderInstance shader, Consumer<ShaderInstance> onLoaded)
	{
		shaderList.add(Pair.of(shader, onLoaded));
	}
}
