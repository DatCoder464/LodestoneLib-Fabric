package com.sammy.lodestone.mixin;

import com.sammy.lodestone.config.ClientConfig;
import com.sammy.lodestone.handlers.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.lodestone.LodestoneLib.RANDOM;


@Mixin(Camera.class)
public class CameraMixin {
	@Inject(method = "setup", at = @At("RETURN"))
	private void lodestoneScreenshake(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
		if (ClientConfig.SCREENSHAKE_INTENSITY > 0) {
			ScreenshakeHandler.cameraTick((Camera) (Object) this, RANDOM);
		}
	}
}
