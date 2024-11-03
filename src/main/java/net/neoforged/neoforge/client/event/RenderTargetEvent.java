package net.neoforged.neoforge.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Super event for all render target events.
 */
public abstract class RenderTargetEvent extends Event
{
    private final int width;
    private final int height;

    @ApiStatus.Internal
    protected RenderTargetEvent(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    /**
     * The width of the screen in pixels.
     *
     * @return The screen width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * The height of the screen in pixels.
     *
     * @return The screen height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Event fired when render target should be created
     * <p>
     *     Is fired on the mod bus event, because the game bus has not been started yet.
     * </p>
     */
    public static class Create extends RenderTargetEvent implements IModBusEvent
    {
        @ApiStatus.Internal
        public Create(int width, int height)
        {
            super(width, height);
        }
    }

    /**
     * Event fired when your custom render targets created during {@link Create} should be resized
     */
    public static class Resize extends RenderTargetEvent
    {
        @ApiStatus.Internal
        public Resize(int width, int height)
        {
            super(width, height);
        }
    }
}
