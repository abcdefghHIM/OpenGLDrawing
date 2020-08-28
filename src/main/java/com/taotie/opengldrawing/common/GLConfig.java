package com.taotie.opengldrawing.common;

import net.minecraft.world.World;

public class GLConfig {
	public static GLWorldSavedData data;
	public static GLConfig config;
	public static Object lock = new Object();

	public static void Changed(World world) {
		EventLoader.isChanged = true;
		synchronized (lock) {
			try {
				lock.wait(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		data = GLWorldSavedData.get(world);
		EventLoader.isChanged = false;
	}
}
