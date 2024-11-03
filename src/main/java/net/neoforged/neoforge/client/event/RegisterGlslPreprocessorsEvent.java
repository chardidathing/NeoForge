package net.neoforged.neoforge.client.event;

import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.shaders.CompiledShader;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.client.renderer.INeoForgeGlslPreprocessor;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This event is fired when a shader is about to be processed by the shader preprocessor.
 * <p>
 *     This event is fired on the mod bus event, because the game bus has not been started yet.
 * </p>
 */
public class RegisterGlslPreprocessorsEvent extends Event implements IModBusEvent
{

    private final List<INeoForgeGlslPreprocessor> preprocessors = Lists.newArrayList();

    private final CompiledShader.Type type;

    @ApiStatus.Internal
    public RegisterGlslPreprocessorsEvent(CompiledShader.Type type) {
        this.type = type;
    }

    /**
     * The type of shader that is being processed.
     *
     * @return The shader type
     */
    public CompiledShader.Type getType() {
        return type;
    }

    /**
     * The list of preprocessors that will be applied to the shader.
     * The default preprocessor is not included in this list.
     *
     * @return The list of preprocessors
     */
    public List<INeoForgeGlslPreprocessor> getPreprocessors()
    {
        return List.copyOf(preprocessors);
    }

    /**
     * Add a new preprocessor to the list of preprocessors.
     *
     * @param preprocessor The preprocessor to add
     */
    public void newProcessor(INeoForgeGlslPreprocessor preprocessor)
    {
        preprocessors.add(preprocessor);
    }

    /**
     * Add a new import handler to the list of preprocessors.
     *
     * @param importPrefix The import prefix to use, the default preprocessor uses moj_import. Pick something unique, this is not validated.
     * @param importHandler The import handler to add
     */
    public void newImportHandler(final String importPrefix, final IShaderImportHandler importHandler) {
        preprocessors.add(new GlslPreprocessor(importPrefix)
        {
            @org.jetbrains.annotations.Nullable
            @Override
            public String applyImport(boolean isRelative, @NotNull String toImport)
            {
                return importHandler.apply(isRelative, toImport);
            }
        });
    }

    /**
     * A handler for imports, used by {@link #newImportHandler(String, IShaderImportHandler)}.
     */
    @FunctionalInterface
    public interface IShaderImportHandler {

        /**
         * Invoked to handle an import.
         * Returns a string matching the import, or null if the import should be ignored.
         *
         * @param isRelative Whether the import is relative to the current shader
         * @param toImport The import to handle
         * @return The string to replace the import with, or null to ignore the import
         */
        @Nullable
        String apply(boolean isRelative, String toImport);
    }
}