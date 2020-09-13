package com.taotie.opengldrawing.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.naming.NameNotFoundException;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
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
		try {
			if (GLConfig.config.getOrSetData(null, false) == null)
				return;
			for (GLImage image : GLConfig.config.getOrSetData(null, false).get()) {
				if (image.o)
					dr.DrawingA(event.getPartialTicks(), image);
			}
			for (GLImage image : GLConfig.config.getOrSetData(false, null)) {
				if (image.o)
					dr.DrawingA(event.getPartialTicks(), image);
			}
		} catch (Exception e) {
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

	@SubscribeEvent
	public void onClientChatEvent(ClientChatEvent event) {
		String message = event.getMessage();
		if (message.indexOf("#") != -1) {
			event.setCanceled(true);
			String[] strs = message.split(" ");
			if (strs.length < 2)
				return;
			switch (strs[1]) {
			case "addglimage": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {

					String name = strs[2].trim();
					for (GLImage image : GLConfig.config.getOrSetData(false, null)) {
						if (name.equals(image.n)) {
							throw new NameNotFoundException();
						}
					}
					GLImage d = new GLImage();
					d.n = name;
					d.x = entityPlayerMP.posX;
					d.y = entityPlayerMP.posY;
					d.z = entityPlayerMP.posZ;
					GLConfig.config.getOrSetData(true, d);
					entityPlayerMP.sendMessage(new TextComponentTranslation("addglimage.info", name));
				} catch (NameNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("addglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.addglimage.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "removeglimage": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					boolean isRemove = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							it_b.remove();
							isRemove = true;
							break;
						}
					}
					if (!isRemove)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.info", name));
				} catch (NameNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.removeglimage.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "gldrawing": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					File file = new File(strs[3]);
					if (!file.exists())
						throw new FileNotFoundException();
					FileInputStream fileInputStream = new FileInputStream(file);
					boolean isFind = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage b = it_b.next();
						if (strs[2].equals(b.n)) {
							it_b.remove();
							GLImage im = b;
							byte[] a = new byte[fileInputStream.available()];
							fileInputStream.read(a);
							im.image = a;
							im.path = strs[3];
							GLConfig.config.getOrSetData(true, im);
							isFind = true;
							break;
						}
					}
					fileInputStream.close();
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("gldrawing.info", strs[2]));
				} catch (FileNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("gldrawing.error", strs[3])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (NameNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (IOException e) {
					e.printStackTrace();
					entityPlayerMP.sendMessage(new TextComponentTranslation("gldrawing.error1", strs[3])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					e.printStackTrace();
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.gldrawing.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "glimagelist": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				StringBuffer stringBuffer = new StringBuffer();
				for (GLImage image : GLConfig.config.getOrSetData(false, null)) {
					stringBuffer.append(image.n + " ");
				}
				entityPlayerMP.sendMessage(new TextComponentTranslation(stringBuffer.toString()));
			}
				break;
			case "glmove": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					double num = Double.parseDouble(strs[4]);
					boolean isFind = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							switch (strs[3]) {
							case "x":
								it_b.remove();
								a.x += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							case "y":
								it_b.remove();
								a.y += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							case "z":
								it_b.remove();
								a.z += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							default:
								entityPlayerMP.sendMessage(new TextComponentTranslation("glmove.error", strs[3])
										.setStyle(new Style().setColor(TextFormatting.RED)));
								return;
							}
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glmove.info", strs[2]));
				} catch (NumberFormatException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.glmove.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "glrotate": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					double num = Double.parseDouble(strs[4]);
					boolean isFind = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							switch (strs[3]) {
							case "x":
								it_b.remove();
								a.xr += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							case "y":
								it_b.remove();
								a.yr += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							case "z":
								it_b.remove();
								a.zr += num;
								GLConfig.config.getOrSetData(true, a);
								break;
							default:
								entityPlayerMP.sendMessage(new TextComponentTranslation("glmove.error", strs[3])
										.setStyle(new Style().setColor(TextFormatting.RED)));
								return;
							}
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glrotate.info", strs[2]));
				} catch (NumberFormatException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.glrotate.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "glset": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					double x = Double.parseDouble(strs[3]);
					double y = Double.parseDouble(strs[4]);
					double z = Double.parseDouble(strs[5]);
					boolean isFind = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							it_b.remove();
							a.x = x;
							a.y = y;
							a.z = z;
							GLConfig.config.getOrSetData(true, a);
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glset.info", strs[2]));
				} catch (NumberFormatException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.glset.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "gltransparent": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					boolean isFind = false;
					boolean tr = false;
					if (strs[3].toLowerCase().equals("true"))
						tr = true;
					else
						tr = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							it_b.remove();
							a.t = tr;
							GLConfig.config.getOrSetData(true, a);
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glset.info", name));
				} catch (NameNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.gltransparent.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "setglimagesize": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					double height = Double.parseDouble(strs[3]);
					double width = Double.parseDouble(strs[4]);
					boolean isFind = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							it_b.remove();
							a.h = height;
							a.w = width;
							GLConfig.config.getOrSetData(true, a);
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glset.info", strs[2]));
				} catch (NumberFormatException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.setglimagesize.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			case "glshow": {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				try {
					String name = strs[2].trim();
					boolean isFind = false;
					boolean tr = false;
					if (strs[3].toLowerCase().equals("true"))
						tr = true;
					else
						tr = false;
					Iterator<GLImage> it_b = GLConfig.config.getOrSetData(false, null).iterator();
					while (it_b.hasNext()) {
						GLImage a = it_b.next();
						if (name.equals(a.n)) {
							it_b.remove();
							a.o = tr;
							GLConfig.config.getOrSetData(true, a);
							isFind = true;
							break;
						}
					}
					if (!isFind)
						throw new NameNotFoundException();
					entityPlayerMP.sendMessage(new TextComponentTranslation("glset.info", name));
				} catch (NameNotFoundException e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("removeglimage.error", strs[2])
							.setStyle(new Style().setColor(TextFormatting.RED)));
				} catch (Exception e) {
					entityPlayerMP.sendMessage(new TextComponentTranslation("commands.glshow.usage")
							.setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
				break;
			default: {
				EntityPlayerSP entityPlayerMP = FMLClientHandler.instance().getClientPlayerEntity();
				entityPlayerMP.sendMessage(new TextComponentTranslation(
						"addglimage,removeglimage,gldrawing,glimagelist,glmove,glrotate,glset,gltransparent,glshow"));
			}
				break;
			}
		}
	}
}
