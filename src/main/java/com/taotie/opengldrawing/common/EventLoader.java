package com.taotie.opengldrawing.common;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventLoader {
	public Drawing dr = new Drawing();

	public EventLoader() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
		try
		{
			if (GLConfig.config.getOrSetData(null, false) == null)
				return;
			for (GLImage image : GLConfig.config.getOrSetData(null, false).get()) {
				if (image.o)
					dr.DrawingA(event.getPartialTicks(), image);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
		GLConfig.config.getOrSetData(event.player.getEntityWorld(), true);
	}

	@SubscribeEvent
	public void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
		GLConfig.config.getOrSetData(event.player.getEntityWorld(), true);
	}
}
