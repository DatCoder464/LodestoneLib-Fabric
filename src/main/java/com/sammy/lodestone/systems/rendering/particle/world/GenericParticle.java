package com.sammy.lodestone.systems.rendering.particle.world;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.lodestone.config.ClientConfig;
import com.sammy.lodestone.handlers.RenderHandler;
import com.sammy.lodestone.setup.LodestoneRenderTypes;
import com.sammy.lodestone.systems.rendering.particle.ParticleTextureSheets;
import com.sammy.lodestone.systems.rendering.particle.SimpleParticleEffect;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class GenericParticle extends TextureSheetParticle {
    protected WorldParticleEffect data;
    private final ParticleRenderType textureSheet;
    protected final FabricSpriteProviderImpl spriteProvider;
	private final Vec3 startingVelocity;
	private boolean reachedPositiveAlpha;
	private boolean reachedPositiveScale;
    float[] hsv1 = new float[3], hsv2 = new float[3];
    public GenericParticle(ClientLevel world, WorldParticleEffect data, FabricSpriteProviderImpl spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z);
        this.data = data;
        this.textureSheet = data.textureSheet;
        this.spriteProvider = spriteProvider;
        this.roll = data.spinOffset + data.spin1;
        if (!data.forcedMotion) {
            this.xd = velocityX;
            this.yd = velocityY;
            this.zd = velocityZ;
        }
        this.setLifetime(data.lifetime);
        this.gravity = data.gravity;
        this.hasPhysics = !data.noClip;
        this.friction = 1;
		this.startingVelocity = data.motionStyle == SimpleParticleEffect.MotionStyle.START_TO_END ? data.startingVelocity : new Vec3((float)velocityX, (float)velocityY, (float)velocityZ);
		Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
		if (spriteProvider != null) {
			if (getAnimator().equals(SimpleParticleEffect.Animator.RANDOM_SPRITE)) {
				setSprite(spriteProvider.get(new Random()));
			}
			if (getAnimator().equals(SimpleParticleEffect.Animator.FIRST_INDEX) || getAnimator().equals(SimpleParticleEffect.Animator.WITH_AGE)) {
				setSprite(0);
			}
			if (getAnimator().equals(SimpleParticleEffect.Animator.LAST_INDEX)) {
				setSprite(spriteProvider.getSprites().size() - 1);
			}
		}
		updateTraits();
    }

    public void setSprite(int spriteIndex) {
        if (spriteIndex < spriteProvider.getSprites().size() && spriteIndex >= 0) {
            setSprite(spriteProvider.getSprites().get(spriteIndex));
        }
    }
    public void pickColor(float colorCoeff) {
        float h = Mth.rotLerp(colorCoeff, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = Mth.lerp(colorCoeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(colorCoeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
    }

    public float getCurve(float multiplier) {
        return Mth.clamp((age * multiplier) / (float)lifetime, 0, 1);
    }
    public SimpleParticleEffect.Animator getAnimator() {
        return data.animator;
    }
    protected void updateTraits() {
		if (data.removalProtocol == SimpleParticleEffect.SpecialRemovalProtocol.INVISIBLE ||
				(data.removalProtocol == SimpleParticleEffect.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE && (getCurve(data.scaleCoefficient) > 0.5f || getCurve(data.alphaCoefficient) > 0.5f))) {
			if ((reachedPositiveAlpha && alpha <= 0) || (reachedPositiveScale && quadSize <= 0)) {
				remove();
				return;
			}
		}
		if (alpha > 0) {
			reachedPositiveAlpha = true;
		}
		if (quadSize > 0) {
			reachedPositiveScale = true;
		}
		pickColor(data.colorCurveEasing.ease(getCurve(data.colorCoefficient), 0, 1, 1));
		if (data.isTrinaryScale()) {
			float trinaryAge = getCurve(data.scaleCoefficient);
			if (trinaryAge >= 0.5f) {
				quadSize = Mth.lerp(data.scaleCurveEndEasing.ease(trinaryAge - 0.5f, 0, 1, 0.5f), data.scale2, data.scale3);
			} else {
				quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.scale1, data.scale2);
			}
		} else {
			quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(getCurve(data.scaleCoefficient), 0, 1, 1), data.scale1, data.scale2);
		}
		if (data.isTrinaryAlpha()) {
			float trinaryAge = getCurve(data.alphaCoefficient);
			if (trinaryAge >= 0.5f) {
				alpha = Mth.lerp(data.alphaCurveStartEasing.ease(trinaryAge - 0.5f, 0, 1, 0.5f), data.alpha2, data.alpha3);
			} else {
				alpha = Mth.lerp(data.alphaCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.alpha1, data.alpha2);
			}
		} else {
			alpha = Mth.lerp(data.alphaCurveStartEasing.ease(getCurve(data.alphaCoefficient), 0, 1, 1), data.alpha1, data.alpha2);
		}
		oRoll = roll;
		if (data.isTrinarySpin()) {
			float trinaryAge = getCurve(data.spinCoefficient);
			if (trinaryAge >= 0.5f) {
				roll += Mth.lerp(data.spinCurveEndEasing.ease(trinaryAge - 0.5f, 0, 1, 0.5f), data.spin2, data.spin3);
			} else {
				roll += Mth.lerp(data.spinCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.spin1, data.spin2);
			}
		} else {
			roll += Mth.lerp(data.spinCurveStartEasing.ease(getCurve(data.alphaCoefficient), 0, 1, 1), data.spin1, data.spin2);
		}
		if (data.forcedMotion) {
			float motionAge = getCurve(data.motionCoefficient);
			Vec3 currentMotion = data.motionStyle == SimpleParticleEffect.MotionStyle.START_TO_END ? startingVelocity : new Vec3((float) xd, (float) yd, (float) zd);
			xd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), currentMotion.x(), data.endingMotion.x());
			yd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), currentMotion.y(), data.endingMotion.y());
			zd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), currentMotion.z(), data.endingMotion.z());
		} else {
			xd *= data.motionCoefficient;
			yd *= data.motionCoefficient;
			zd *= data.motionCoefficient;
		}
    }
    @Override
    public void tick() {
        updateTraits();
        if (data.animator.equals(SimpleParticleEffect.Animator.WITH_AGE)) {
            setSpriteFromAge(spriteProvider);
        }
        super.tick();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		VertexConsumer consumer = vertexConsumer;
		if (ClientConfig.DELAYED_RENDERING) {
			if (getRenderType().equals(ParticleTextureSheets.ADDITIVE)) {
				consumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypes.ADDITIVE_PARTICLE);
			}
			if (getRenderType().equals(ParticleTextureSheets.TRANSPARENT)) {
				consumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypes.TRANSPARENT_PARTICLE);
			}
		}
        super.render(consumer, camera, tickDelta);
    }
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return textureSheet;
    }
}
