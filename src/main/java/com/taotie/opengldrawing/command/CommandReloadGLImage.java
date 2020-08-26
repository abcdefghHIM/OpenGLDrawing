package com.taotie.opengldrawing.command;

import java.util.List;

import javax.annotation.Nullable;

import com.taotie.opengldrawing.common.GLConfig;
import com.taotie.opengldrawing.common.GLImage;
import com.taotie.opengldrawing.common.GLWorldSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandReloadGLImage extends CommandBase{

	@Override
	public String getName() {
		return "reloadglimage";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.reloadglimage.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
		GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
		GLConfig.data=data;
		sender.sendMessage(new TextComponentTranslation("reloadglimage.info"));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 1;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		return getListOfStringsMatchingLastWord(args, new String[] {});
	}
}
