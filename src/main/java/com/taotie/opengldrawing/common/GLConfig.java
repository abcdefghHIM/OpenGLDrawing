package com.taotie.opengldrawing.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;

public class GLConfig {
	public GLWorldSavedData data;
	public static GLConfig config;
	public List<GLImage> glImages = new ArrayList<GLImage>();

	public synchronized GLWorldSavedData getOrSetData(World world, boolean set) {
		if (set)
			data = GLWorldSavedData.get(world);
		return data;
	}

	public synchronized List<GLImage> getOrSetData(boolean set, GLImage image) {
		if (set)
			glImages.add(image);
		return glImages;
	}
}
