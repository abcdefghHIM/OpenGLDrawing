package com.taotie.opengldrawing.common;

import net.minecraft.world.World;

public class GLConfig {
	public GLWorldSavedData data;
	public static GLConfig config;

	public synchronized GLWorldSavedData getOrSetData(World world, boolean set) {
		if (set)
			data = GLWorldSavedData.get(world);
		return data;
	}
}
