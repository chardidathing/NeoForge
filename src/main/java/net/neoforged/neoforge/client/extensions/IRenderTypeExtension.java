package net.neoforged.neoforge.client.extensions;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.RenderType;

import java.util.function.Consumer;

/**
 * Defines extension methods on {@link RenderType} for use in Forge.
 */
public interface IRenderTypeExtension {

    /**
     * The current render type.
     * @return The current render type (basically this)
     * @implNote This is not allowed to be default implemented and double cast. It must be implemented by the target, for performance reasons (The unchecked cast is expensive)!
     */
    RenderType me();

    /**
     * Renders the given CPU buffer with this render type without setting up sorting!
     *
     * @param renderedBuffer The CPU buffer representation.
     * @param close Whether to close the buffer after rendering.
     * @param afterSetupAction The callback invoked after the render state has been set up.
     * @param afterRenderAction The callback invoked after the render state has been cleared.
     */
    default void doRender(MeshData renderedBuffer, boolean close, Consumer<CompiledShaderProgram> afterSetupAction, Consumer<CompiledShaderProgram> afterRenderAction) {
        me().setupRenderState();
        afterSetupAction.accept(RenderSystem.getShader());
        BufferUploader.drawWithShader(renderedBuffer, close);
        afterRenderAction.accept(RenderSystem.getShader());
        me().clearRenderState();
    }
}
