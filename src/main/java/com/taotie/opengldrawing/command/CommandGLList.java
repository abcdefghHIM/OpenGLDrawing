package com.taotie.opengldrawing.command;

import java.util.List;

import javax.annotation.Nullable;
import javax.naming.NameNotFoundException;

import com.taotie.opengldrawing.common.GLImage;
import com.taotie.opengldrawing.common.GLWorldSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandGLList extends CommandBase {

	@Override
	public String getName() {
		return "glimagelist";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.glimagelist.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
		GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
		StringBuffer stringBuffer = new StringBuffer();
		for (GLImage image : data.get()) {
			stringBuffer.append(image.n + " ");
		}
		sender.sendMessage(new TextComponentTranslation(stringBuffer.toString()));
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
