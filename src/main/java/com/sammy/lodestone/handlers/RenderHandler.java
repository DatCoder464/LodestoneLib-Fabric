package com.sammy.lodestone.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.sammy.lodestone.config.ClientConfig;
import com.sammy.lodestone.helpers.RenderHelper;
import com.sammy.lodestone.setup.LodestoneRenderTypes;
import com.sammy.lodestone.systems.rendering.ExtendedShader;
import com.sammy.lodestone.systems.rendering.ShaderUniformHandler;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

import java.util.HashMap;

public class RenderHandler {
	public static HashMap<RenderType, BufferBuilder> EARLY_BUFFERS = new HashMap<>();
	public static HashMap<RenderType, BufferBuilder> BUFFERS = new HashMap<>();
	public static HashMap<RenderType, BufferBuilder> LATE_BUFFERS = new HashMap<>();
	public static HashMap<RenderType, ShaderUniformHandler> HANDLERS = new HashMap<>();
	public static MultiBufferSource.BufferSource EARLY_DELAYED_RENDER;
	public static MultiBufferSource.BufferSource DELAYED_RENDER;
	public static MultiBufferSource.BufferSource LATE_DELAYED_RENDER;
	public static Matrix4f PARTICLE_MATRIX = null;

	public static void init() {
		EARLY_DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(EARLY_BUFFERS, new BufferBuilder(FabricLoaderImpl.INSTANCE.isModLoaded("sodium") ? 262144 : 256));
		DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(BUFFERS, new BufferBuilder(FabricLoaderImpl.INSTANCE.isModLoaded("sodium") ? 2097152 : 256));
		LATE_DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(LATE_BUFFERS, new BufferBuilder(FabricLoaderImpl.INSTANCE.isModLoaded("sodium") ? 262144 : 256));
	}
	public static void renderLast(PoseStack stack) {
		stack.pushPose();
		if (ClientConfig.DELAYED_RENDERING) {
			RenderSystem.getModelViewStack().pushPose();
			RenderSystem.getModelViewStack().setIdentity();
			if (PARTICLE_MATRIX != null) {
				RenderSystem.getModelViewStack().mulPoseMatrix(PARTICLE_MATRIX);
			}
			RenderSystem.applyModelViewMatrix();
			DELAYED_RENDER.endBatch(LodestoneRenderTypes.TRANSPARENT_PARTICLE);
			DELAYED_RENDER.endBatch(LodestoneRenderTypes.ADDITIVE_PARTICLE);
			RenderSystem.getModelViewStack().popPose();
			RenderSystem.applyModelViewMatrix();
		}
		draw(EARLY_DELAYED_RENDER, EARLY_BUFFERS);
		draw(DELAYED_RENDER, BUFFERS);
		draw(LATE_DELAYED_RENDER, LATE_BUFFERS);
		stack.popPose();
	}

	public static void draw(MultiBufferSource.BufferSource source, HashMap<RenderType, BufferBuilder> buffers) {
		for (RenderType type : buffers.keySet()) {
			ShaderInstance instance = RenderHelper.getShader(type);
			if (HANDLERS.containsKey(type)) {
				ShaderUniformHandler handler = HANDLERS.get(type);
				handler.updateShaderData(instance);
			}
			source.endBatch(type);
			if (instance instanceof ExtendedShader extendedShaderInstance) {
				extendedShaderInstance.setUniformDefaults();
			}
		}
		source.endBatch();
	}
	public static void addRenderType(RenderType type) {
		RenderHandler.EARLY_BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
		RenderHandler.BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
		RenderHandler.LATE_BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
	}
}
