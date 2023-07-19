package com.sammy.lodestone.helpers;

import com.sammy.lodestone.network.screenshake.PositionedScreenshakePacket;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OrtTestItem extends Item {
	public OrtTestItem(Properties settings) {
		super(settings);
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		if(context.getLevel() instanceof ServerLevel s) {
			Player user = context.getPlayer();
			s.getPlayers(players -> players.getLevel().hasChunk(new ChunkPos(user.blockPosition()).x, new ChunkPos(user.blockPosition()).z)).forEach(players -> {
				FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
				new PositionedScreenshakePacket(70, Vec3.atCenterOf(context.getClickedPos()),20f, 0.3f, 25f, Easing.CIRC_IN).setIntensity(0f, 1f, 0f).setEasing(Easing.CIRC_OUT, Easing.CIRC_IN).write(buf);
				ServerPlayNetworking.send(players, PositionedScreenshakePacket.ID, buf);
			});
		}
		return super.useOn(context);
	}
}
