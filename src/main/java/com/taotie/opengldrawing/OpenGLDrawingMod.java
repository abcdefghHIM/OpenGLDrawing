package com.taotie.opengldrawing;

import com.taotie.opengldrawing.common.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = OpenGLDrawingMod.MODID, name = OpenGLDrawingMod.NAME, version = OpenGLDrawingMod.VERSION, acceptedMinecraftVersions = "1.12.2")
public class OpenGLDrawingMod {
	public static final String MODID = "opengldrawing";
	public static final String NAME = "OpenGL Drawing";
	public static final String VERSION = "1.0";

	@Instance(OpenGLDrawingMod.MODID)
	public static OpenGLDrawingMod instance;

	@SidedProxy(clientSide = "com.taotie.opengldrawing.client.ClientProxy", serverSide = "com.taotie.opengldrawing.common.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}
}
