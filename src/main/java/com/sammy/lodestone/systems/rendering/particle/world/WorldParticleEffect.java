package com.sammy.lodestone.systems.rendering.particle.world;

import com.mojang.brigadier.StringReader;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.sammy.lodestone.systems.rendering.particle.SimpleParticleEffect;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class WorldParticleEffect extends SimpleParticleEffect implements ParticleOptions {

    public ParticleType<?> type;
    public Vec3 startingVelocity = Vec3.ZERO, endingMotion = Vec3.ZERO;
    public WorldParticleEffect(ParticleType<?> type) {
        this.type = type;
    }

    public static Codec<WorldParticleEffect> codecFor(ParticleType<?> type) {
        return Codec.unit(() -> new WorldParticleEffect(type));
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {

    }

    public String writeToString() {
        return "";
    }

    public static final ParticleOptions.Deserializer<WorldParticleEffect> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public WorldParticleEffect fromCommand(ParticleType<WorldParticleEffect> type, StringReader reader) {
            return new WorldParticleEffect(type);
        }

        @Override
        public WorldParticleEffect fromNetwork(ParticleType<WorldParticleEffect> type, FriendlyByteBuf buf) {
            return new WorldParticleEffect(type);
        }
    };
}
