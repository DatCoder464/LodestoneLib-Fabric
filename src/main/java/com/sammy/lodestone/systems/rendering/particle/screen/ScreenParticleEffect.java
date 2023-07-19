package com.sammy.lodestone.systems.rendering.particle.screen;
import com.sammy.lodestone.systems.rendering.particle.SimpleParticleEffect;
import com.sammy.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public class ScreenParticleEffect extends SimpleParticleEffect {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ItemStack stack;
    public float xOrigin;
    public float yOrigin;
    public float xOffset;
    public float yOffset;

	public Vec2 startingVelocity = Vec2.ZERO, endingMotion = Vec2.ZERO;

    public ScreenParticleEffect(ScreenParticleType<?> type) {
        this.type = type;
    }
}
