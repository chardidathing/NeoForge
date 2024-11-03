package net.neoforged.neoforge.client.event;

import net.minecraft.client.renderer.CompiledShaderProgram;
import net.minecraft.client.renderer.ShaderProgram;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Defines events fired for shaders.
 */
public abstract class CompiledShaderEvent extends Event
{

    private final CompiledShaderProgram instance;

    @ApiStatus.Internal
    public CompiledShaderEvent(CompiledShaderProgram instance)
    {
        this.instance = instance;
    }

    public CompiledShaderProgram getShader()
    {
        return instance;
    }

    /**
     * Event fired when a shader programs uniforms have been initialized from disk,
     * but before uniform processing has started.
     * <p>
     *     This is fired on the mod bus event, because the game bus has not been started yet.
     * </p>
     */
    public static class RegisterUniforms extends CompiledShaderEvent implements IModBusEvent
    {

        @ApiStatus.Internal
        public RegisterUniforms(CompiledShaderProgram instance)
        {
            super(instance);
        }
    }
}
