package com.sammy.lodestone.systems.rendering;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public interface PositionTrackedEntity {
	public void trackPastPositions();
	public ArrayList<Vec3> getPastPositions();
}
