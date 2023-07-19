package com.sammy.lodestone.mixin;

import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.sammy.lodestone.handlers.PostProcessHandler;
import com.sammy.lodestone.handlers.RenderHandler;
import com.sammy.lodestone.setup.LodestoneShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Tuple;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
final class GameRendererMixin {
	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V", shift = At.Shift.AFTER))
	private void lodestone$renderWorldLast(float tickDelta, long limitTime, PoseStack matrix, CallbackInfo ci) {
		Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		matrix.pushPose();
		matrix.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());
		PostProcessHandler.renderLast(matrix);
		RenderHandler.renderLast(matrix);
		matrix.popPose();
	}
	@Inject(method = "reloadShaders", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private void lodestone$registerShaders(ResourceManager manager, CallbackInfo ci, List<Program> list, List<Pair<ShaderInstance, Consumer<ShaderInstance>>> list2) throws IOException {
		LodestoneShaders.init(manager);
		list2.addAll(LodestoneShaders.shaderList);
	}
	@Inject(method = "resize", at = @At(value = "HEAD"))
	public void injectionResizeListener(int width, int height, CallbackInfo ci) {
		PostProcessHandler.resize(width, height);
	}
}
