package com.taotie.opengldrawing.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;
import javax.naming.NameNotFoundException;

import com.taotie.opengldrawing.common.GLConfig;
import com.taotie.opengldrawing.common.GLImage;
import com.taotie.opengldrawing.common.GLWorldSavedData;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandGLDrawing extends CommandBase {

	@Override
	public String getName() {
		return "gldrawing";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.gldrawing.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		try {
			File file = new File(args[1]);
			if (!file.exists())
				throw new FileNotFoundException();
			FileInputStream fileInputStream = new FileInputStream(file);
			EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
			GLWorldSavedData data = GLWorldSavedData.get(sender.getEntityWorld());
			boolean isFind = false;
			Iterator<GLImage> it_b = data.get().iterator();
			while (it_b.hasNext()) {
				GLImage b = it_b.next();
				if (args[0].equals(b.n)) {
					it_b.remove();
					GLImage im = b;
					byte[] a = new byte[fileInputStream.available()];
					fileInputStream.read(a);
					im.image = a;
					System.out.println(im.image.length);
					im.path = args[1];
					data.get().add(im);
					data.upDate();
					isFind = true;
					break;
				}
			}
			fileInputStream.close();
			if (!isFind)
				throw new NameNotFoundException();
			sender.sendMessage(new TextComponentTranslation("gldrawing.info", args[0]));
		} catch (FileNotFoundException e) {
			sender.sendMessage(new TextComponentTranslation("gldrawing.error", args[1])
					.setStyle(new Style().setColor(TextFormatting.RED)));
		} catch (NameNotFoundException e) {
			sender.sendMessage(new TextComponentTranslation("removeglimage.error", args[0])
					.setStyle(new Style().setColor(TextFormatting.RED)));
		} catch (IOException e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponentTranslation("gldrawing.error1", args[1])
					.setStyle(new Style().setColor(TextFormatting.RED)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new WrongUsageException("commands.gldrawing.usage");
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
