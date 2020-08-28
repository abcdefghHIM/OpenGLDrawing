package com.taotie.opengldrawing.command;

import java.util.ArrayList;
import java.util.Iterator;
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
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandRemoveGLImage extends CommandBase {
	@Override
	public String getName() {
		return "removeglimage";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.removeglimage.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
			String name = args[0].trim();
			boolean isRemove = false;
			Iterator<GLImage> it_b=data.get().iterator();
			while(it_b.hasNext())
			{
				GLImage a=it_b.next();
				if(name.equals(a.n))
				{
					it_b.remove();
					data.upDate();
					isRemove = true;
					break;
				}
			}
			if (!isRemove)
				throw new NameNotFoundException();
			GLConfig.Changed(sender.getEntityWorld());
			sender.sendMessage(new TextComponentTranslation("removeglimage.info", name));
		} catch (NameNotFoundException e) {
			sender.sendMessage(new TextComponentTranslation("removeglimage.error", args[0])
					.setStyle(new Style().setColor(TextFormatting.RED)));
		} catch (Exception e) {
			throw new WrongUsageException("commands.removeglimage.usage");
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 1;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		if (args.length == 1) {
			GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
			List<GLImage> d = data.get();
			String[] ll = new String[d.size()];
			List<String> lll = new ArrayList<String>();
			for (GLImage image : d) {
				lll.add(image.n);
			}
			lll.toArray(ll);
			return getListOfStringsMatchingLastWord(args, ll);
		}
		return getListOfStringsMatchingLastWord(args, new String[] {});
	}
}
