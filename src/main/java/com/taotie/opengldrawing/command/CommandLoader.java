package com.taotie.opengldrawing.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader {
	public CommandLoader(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandAddGLImage());
		event.registerServerCommand(new CommandRemoveGLImage());
		event.registerServerCommand(new CommandGLDrawing());
		event.registerServerCommand(new CommandGLList());
		event.registerServerCommand(new CommandGLMove());
		event.registerServerCommand(new CommandGLRotate());
		event.registerServerCommand(new CommandGLSet());
		event.registerServerCommand(new CommandGLTransparent());
		event.registerServerCommand(new CommandSetGLImageSize());
		event.registerServerCommand(new CommandGLShow());
		event.registerServerCommand(new CommandReloadGLImage());
	}
}
