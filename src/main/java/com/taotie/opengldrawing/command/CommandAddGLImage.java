package com.taotie.opengldrawing.command;

import java.util.List;

import javax.annotation.Nullable;
import javax.naming.NameNotFoundException;

import com.taotie.opengldrawing.common.GLConfig;
import com.taotie.opengldrawing.common.GLImage;
import com.taotie.opengldrawing.common.GLWorldSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandAddGLImage extends CommandBase {

	@Override
	public String getName() {
		return "addglimage";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.addglimage.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
			GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
			String name = args[0].trim();
			for (GLImage image : data.get()) {
				if (name.equals(image.n)) {
					throw new NameNotFoundException();
				}
			}
			GLImage d = new GLImage();
			d.n = name;
			d.x = entityPlayerMP.posX;
			d.y = entityPlayerMP.posY;
			d.z = entityPlayerMP.posZ;
			data.add(d);
			GLConfig.Changed(sender.getEntityWorld());
			sender.sendMessage(new TextComponentTranslation("addglimage.info", name));
		} catch (NameNotFoundException e) {
			sender.sendMessage(new TextComponentTranslation("addglimage.error", args[0])
					.setStyle(new Style().setColor(TextFormatting.RED)));
		} catch (Exception e) {
			throw new WrongUsageException("commands.addglimage.usage");
		}
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
