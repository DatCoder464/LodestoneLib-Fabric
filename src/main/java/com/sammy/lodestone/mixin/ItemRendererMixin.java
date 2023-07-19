package com.sammy.lodestone.mixin;

import com.sammy.lodestone.handlers.ScreenParticleHandler;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
final class ItemRendererMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;"), method = "renderGuiItem(Lnet/minecraft/world/item/ItemStack;IILnet/minecraft/client/resources/model/BakedModel;)V")
	private void lodestone$itemParticleEmitter(ItemStack stack, int x, int y, BakedModel model, CallbackInfo ci) {
		ScreenParticleHandler.renderItem(stack);
	}
}
