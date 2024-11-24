package net.neoforged.neoforge.client.renderer;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderProgramConfig;
import org.jetbrains.annotations.VisibleForTesting;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public interface IGraphicsShader extends AutoCloseable {
    void setupUniforms(List<ShaderProgramConfig.Uniform> p_366844_, List<ShaderProgramConfig.Sampler> p_366550_);

    void clear();

    void apply();

    @Nullable
    Uniform getUniform(String p_366894_);

    @Nullable
    ShaderProgramConfig.Uniform getUniformConfig(String p_381109_);

    AbstractUniform safeGetUniform(String p_366560_);

    void bindSampler(String p_366802_, int p_366632_);

    void registerCustomUniform(Uniform uniform, ShaderProgramConfig.Uniform config);

    void registerSampler(ShaderProgramConfig.Sampler p_366798_);

    void setupLights(Vector3f... shaderLightDirections);

    void setDefaultUniforms(VertexFormat.Mode mode, Matrix4f p254480, Matrix4f p254555, Window window);

    void setModelOffset(float x, float y, float z);

    void setModelViewMatrix(Matrix4f translation);

    void setProjectionMatrix(Matrix4f matrix4f);

}
