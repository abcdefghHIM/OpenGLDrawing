package com.taotie.opengldrawing.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class GLWorldSavedData extends WorldSavedData {
	private List<GLImage> images = new ArrayList<GLImage>();

	public GLWorldSavedData(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		images.clear();
		NBTTagList list = (NBTTagList) nbt.getTag("images");
		if (list == null) {
			list = new NBTTagList();
		}
		for (int i = list.tagCount() - 1; i >= 0; --i) {
			NBTTagCompound c = (NBTTagCompound) list.get(i);
			GLImage gl = new GLImage();
			gl.h = c.getDouble("h");
			gl.image = c.getByteArray("image");
			gl.o = c.getBoolean("o");
			gl.path = c.getString("path");
			gl.t = c.getBoolean("t");
			gl.w = c.getDouble("w");
			gl.x = c.getDouble("x");
			gl.xr = c.getDouble("xr");
			gl.y = c.getDouble("y");
			gl.yr = c.getDouble("yr");
			gl.z = c.getDouble("z");
			gl.zr = c.getDouble("zr");
			gl.n = c.getString("n");
			images.add(gl);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();
		for (int i = images.size() - 1; i >= 0; --i) {
			GLImage gl = images.get(i);
			NBTTagCompound c = new NBTTagCompound();
			c.setDouble("h", gl.h);
			c.setByteArray("image", gl.image);
			c.setBoolean("o", gl.o);
			c.setString("path", gl.path);
			c.setBoolean("t", gl.t);
			c.setDouble("w", gl.w);
			c.setDouble("x", gl.x);
			c.setDouble("xr", gl.xr);
			c.setDouble("y", gl.y);
			c.setDouble("yr", gl.yr);
			c.setDouble("z", gl.z);
			c.setDouble("zr", gl.zr);
			c.setString("n", gl.n);
			list.appendTag(c);
		}
		compound.setTag("images", list);
		return compound;
	}

	public static GLWorldSavedData get(World world) {
		WorldSavedData data = world.getPerWorldStorage().getOrLoadData(GLWorldSavedData.class,
				"taotie_gl_drawing_data");
		if (data == null) {
			data = new GLWorldSavedData("taotie_gl_drawing_data");
			world.getPerWorldStorage().setData("taotie_gl_drawing_data", data);
		}
		return (GLWorldSavedData) data;
	}

	public void add(GLImage i) {
		images.add(i);
		this.markDirty();
	}

	public void upDate() {
		this.markDirty();
	}

	public List<GLImage> get() {
		return images;
	}
}
