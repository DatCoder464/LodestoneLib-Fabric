package com.sammy.lodestone.systems.rendering.particle.screen.base;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import static com.sammy.lodestone.helpers.RenderHelper.FULL_BRIGHT;

public abstract class BillboardScreenParticle extends ScreenParticle {
    protected float quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

    protected BillboardScreenParticle(Level clientWorld, double pX, double pY) {
        super(clientWorld, pX, pY);
    }

    protected BillboardScreenParticle(Level clientWorld, double pX, double pY, double pXSpeed, double pYSpeed) {
        super(clientWorld, pX, pY, pXSpeed, pYSpeed);
    }

    @Override
    public void render(BufferBuilder bufferBuilder) {
        final Minecraft client = Minecraft.getInstance();
        float tickDelta = client.getFrameTime();
        float size = getQuadSize(tickDelta) * 10;
        float u0 = getMinU();
        float u1 = getMaxU();
        float v0 = getMinV();
        float v1 = getMaxV();
        float roll = Mth.lerp(tickDelta, this.prevAngle, this.angle);
        Vector3f[] vectors = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        Quaternion rotation = Vector3f.ZP.rotation(roll);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vectors[i];
            vector3f.transform(rotation);
            vector3f.mul(size);
            vector3f.add((float) x, (float) y, 0);
        }
      /* todo, jei tooltips render at 400 z, while the held by mouse item stack renders at around 380, we need a value between to be above the stack, but below JEI tooltips.
         There is definitely a better way of doing this.
         We're not even using JEI but fuck if I care
       */
        int z = 390;
        bufferBuilder.vertex(vectors[0].x(), vectors[0].y(), z).uv(u1, v1).color(this.red, this.green, this.blue, this.alpha).uv2(FULL_BRIGHT).endVertex();
        bufferBuilder.vertex(vectors[1].x(), vectors[1].y(), z).uv(u1, v0).color(this.red, this.green, this.blue, this.alpha).uv2(FULL_BRIGHT).endVertex();
        bufferBuilder.vertex(vectors[2].x(), vectors[2].y(), z).uv(u0, v0).color(this.red, this.green, this.blue, this.alpha).uv2(FULL_BRIGHT).endVertex();
        bufferBuilder.vertex(vectors[3].x(), vectors[3].y(), z).uv(u0, v1).color(this.red, this.green, this.blue, this.alpha).uv2(FULL_BRIGHT).endVertex();
    }

    public float getQuadSize(float tickDelta) {
        return this.quadSize;
    }

    protected abstract float getMinU();

    protected abstract float getMaxU();

    protected abstract float getMinV();

    protected abstract float getMaxV();
}
