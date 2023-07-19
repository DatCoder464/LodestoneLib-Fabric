package com.sammy.lodestone.mixin;


import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.EffectProgram;
import com.sammy.lodestone.systems.postprocess.LodestoneGlslPreprocessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EffectProgram.class)
public class EffectShaderMixin {
	@ModifyArg(method = "compileShader", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/shaders/EffectProgram;compileShaderInternal(Lcom/mojang/blaze3d/shaders/Program$Type;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;Lcom/mojang/blaze3d/preprocessor/GlslPreprocessor;)I"), index = 4)
	private static GlslPreprocessor useCustomPreprocessor(GlslPreprocessor org) {
		return new LodestoneGlslPreprocessor();
	}
}
