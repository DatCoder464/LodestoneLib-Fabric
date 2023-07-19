package com.sammy.lodestone.systems.rendering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;

import java.io.IOException;
import java.util.Arrays;

public class ExtendedShader extends ShaderInstance {
	public ExtendedShader(ResourceProvider resourceFactory, String string, VertexFormat vertexFormat) throws IOException {
		super(resourceFactory, string, vertexFormat);
	}
	public static ExtendedShader createShaderInstance(ShaderHolder shaderHolder, ResourceProvider pResourceProvider, ResourceLocation id, VertexFormat pVertexFormat) throws IOException {
		return new ExtendedShader(pResourceProvider, id.toString(), pVertexFormat) {
			@Override
			public ShaderHolder getHolder() {
				return shaderHolder;
			}
		};
	}

	public void setUniformDefaults() {
		getHolder().setUniformDefaults();
	}

	public ShaderHolder getHolder() {
		return null;
	}

//	@Override
//	public void parseUniformNode(JsonElement pJson) throws ChainedJsonException {
//		if (getHolder().uniforms.isEmpty()) {
//			super.parseUniformNode(pJson);
//			return;
//		}
//		JsonObject jsonobject = GsonHelper.getAsJsonObject(pJson, "uniform");
//		String name = JsonHelper.getString(jsonobject, "name");
//		int i = GlUniform.getTypeIndex(JsonHelper.getString(jsonobject, "type"));
//		int j = JsonHelper.getInt(jsonobject, "count");
//		float[] afloat = new float[Math.max(j, 16)];
//		JsonArray jsonarray = JsonHelper.getArray(jsonobject, "values");
//		if (jsonarray.size() != j && jsonarray.size() > 1) {
//			throw new ChainedJsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
//		} else {
//			int k = 0;
//
//			for (JsonElement jsonelement : jsonarray) {
//				try {
//					afloat[k] = GsonHelper.getAsFloat(jsonelement, "value");
//				} catch (Exception exception) {
//					ChainedJsonException chainedjsonexception = ShaderParseException.wrap(exception);
//					chainedjsonexception.addFaultyElement("values[" + k + "]");
//					throw chainedjsonexception;
//				}
//
//				++k;
//			}
//
//			if (j > 1 && jsonarray.size() == 1) {
//				while (k < j) {
//					afloat[k] = afloat[0];
//					++k;
//				}
//			}
//
//			int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
//			Uniform uniform = new Uniform(name, i + l, j, this);
//			if (i <= 3) {
//				uniform.setForDataType((int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]);
//				if (getHolder().uniforms.contains(name)) {
//					getHolder().defaultUniformData.add(new UniformData.IntegerUniformData(name, i, new int[]{(int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]}));
//				}
//			} else if (i <= 7) {
//				uniform.setForDataType(afloat[0], afloat[1], afloat[2], afloat[3]);
//			} else {
//				uniform.setFloats(Arrays.copyOfRange(afloat, 0, j));
//			}
//			if (i > 3) {
//				if (getHolder().uniforms.contains(name)) {
//					getHolder().defaultUniformData.add(new UniformData.FloatUniformData(name, i, afloat));
//				}
//			}
//			this.uniforms.add(uniform);
//		}
//	}
}
