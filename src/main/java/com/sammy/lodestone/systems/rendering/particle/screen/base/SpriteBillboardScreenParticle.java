package com.sammy.lodestone.systems.rendering.particle.screen.base;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.Level;

import java.util.Random;

public abstract class SpriteBillboardScreenParticle extends BillboardScreenParticle {
    protected TextureAtlasSprite sprite;

    protected SpriteBillboardScreenParticle(Level clientWorld, double pX, double pY) {
        super(clientWorld, pX, pY);
    }

    protected SpriteBillboardScreenParticle(Level clientWorld, double pX, double pY, double pXSpeed, double pYSpeed) {
        super(clientWorld, pX, pY, pXSpeed, pYSpeed);
    }

    protected void setSprite(TextureAtlasSprite pSprite) {
        this.sprite = pSprite;
    }

    protected float getMinU() {
        return this.sprite.getU0();
    }

    protected float getMaxU() {
        return this.sprite.getU1();
    }

    protected float getMinV() {
        return this.sprite.getV0();
    }

    protected float getMaxV() {
        return this.sprite.getV1();
    }

    public void setSprite(SpriteSet pSprite) {
        this.setSprite(pSprite.get(new Random()));
    }

    public void setSpriteForAge(SpriteSet pSprite) {
        if (!this.removed) {
            this.setSprite(pSprite.get(this.age, this.maxAge));
        }
    }
}
