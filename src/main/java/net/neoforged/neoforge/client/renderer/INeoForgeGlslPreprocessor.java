package net.neoforged.neoforge.client.renderer;

import com.mojang.blaze3d.shaders.CompiledShader;

import java.util.List;

@FunctionalInterface
public interface INeoForgeGlslPreprocessor
{
    /**
     * Implements a default shader preprocessor for Forge compatible shaders.
     *
     * @param shaderSource The shader source to process
     * @param type The type of the shader
     * @return The lines of the processed shader
     */
    List<String> process(String shaderSource, CompiledShader.Type type);
}
