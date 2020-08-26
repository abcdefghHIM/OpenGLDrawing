package com.taotie.opengldrawing.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.opengl.GL11;

import com.taotie.opengldrawing.OpenGLDrawingMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class Drawing {
	public static final ResourceLocation no_image = new ResourceLocation(OpenGLDrawingMod.MODID,
			"textures/gui/no_image.png");

	public void DrawingB(double x, double y, double z, double width, double height, byte[] image, double x1, double y1,
			double z1, boolean isT, double xr, double yr, double zr) {
		GL11.glPushMatrix();
		if (image.length == 0) {

			Minecraft.getMinecraft().getTextureManager().bindTexture(no_image);
		} else {
			try {
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(image);
				BufferedImage bufferedImage = TextureUtil.readBufferedImage(arrayInputStream);
				int i = GlStateManager.generateTexture();
				TextureUtil.uploadTextureImageAllocate(i, bufferedImage, false, false);
				arrayInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		double rx = x1 - x;
		double ry = y1 - y;
		double rz = z1 - z;
		GL11.glTranslated(rx, ry, rz);
		GL11.glRotated(xr, 1, 0, 0);
		GL11.glRotated(yr, 0, 1, 0);
		GL11.glRotated(zr, 0, 0, 1);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		if (isT) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder builder = tessellator.getBuffer();
		builder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);
		builder.pos(width, height, 0).tex(0, 0).endVertex();
		builder.pos(0, height, 0).tex(1, 0).endVertex();
		builder.pos(width, 0, 0).tex(0, 1).endVertex();
		builder.pos(0, 0, 0).tex(1, 1).endVertex();
		tessellator.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		if (isT)
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void DrawingA(float Ticks, double width, double height, String path, double x, double y, double z,
			boolean isT, double xr, double yr, double zr, GLImage image) {
		EntityPlayerSP entityPlayerSP = FMLClientHandler.instance().getClientPlayerEntity();
		GLWorldSavedData data = GLWorldSavedData.get(entityPlayerSP.world);
		double d0 = entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * (double) Ticks;
		double d1 = entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * (double) Ticks;
		double d2 = entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * (double) Ticks;
		File file = new File(path);
		if (file.exists()) {
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				byte[] a = new byte[fileInputStream.available()];
				fileInputStream.read(a);
				data.get().remove(image);
				image.image = a;
				data.add(image);
				DrawingB(d0, d1, d2, width, height, a, x, y, z, isT, xr, y, zr);
				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void DrawingA(float Ticks, double width, double height, byte[] image, double x, double y, double z,
			boolean isT, double xr, double yr, double zr) {
		EntityPlayerSP entityPlayerSP = FMLClientHandler.instance().getClientPlayerEntity();
		double d0 = entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * (double) Ticks;
		double d1 = entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * (double) Ticks;
		double d2 = entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * (double) Ticks;
		DrawingB(d0, d1, d2, width, height, image, x, y, z, isT, xr, yr, zr);
	}

	public void DrawingA(float Ticks, GLImage image) {
		DrawingA(Ticks, image.w, image.h, image.image, image.x, image.y, image.z, image.t, image.xr, image.yr,
				image.zr);
	}
}
