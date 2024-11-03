package net.neoforged.neoforge.client.renderer.transparency;

import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.*;

public class OITPreDepthTarget extends RenderTarget {

    public OITPreDepthTarget(int width, int height)
    {
        super(false);
        createBuffers(width, height);
    }

    @Override
    public void createBuffers(int p_83951_, int p_83952_) {
        RenderSystem.assertOnRenderThreadOrInit();
        this.frameBufferId = GlStateManager.glGenFramebuffers();
        MainTarget.Dimension maintarget$dimension = this.allocateAttachments(width, height);
        GlStateManager._glBindFramebuffer(GL_FRAMEBUFFER, this.frameBufferId);
        GlStateManager._glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, getDepthTextureId(), 0); // opaque framebuffer's depth texture
        this.viewWidth = maintarget$dimension.width;
        this.viewHeight = maintarget$dimension.height;
        this.width = maintarget$dimension.width;
        this.height = maintarget$dimension.height;
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        this.checkStatus();
        GlStateManager._glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private MainTarget.Dimension allocateAttachments(int width, int height) {
        RenderSystem.assertOnRenderThreadOrInit();
        this.depthBufferId = TextureUtil.generateTextureId();
        AttachmentState maintarget$attachmentstate = AttachmentState.NONE;

        for(MainTarget.Dimension maintarget$dimension : MainTarget.Dimension.listWithFallback(width, height)) {
            maintarget$attachmentstate = AttachmentState.NONE;
            if (this.allocateDepthAttachment(maintarget$dimension)) {
                maintarget$attachmentstate = maintarget$attachmentstate.with(AttachmentState.DEPTH_BUFFER);
            }

            if (maintarget$attachmentstate == AttachmentState.DEPTH_BUFFER) {
                return maintarget$dimension;
            }
        }

        throw new RuntimeException("Unrecoverable GL_OUT_OF_MEMORY (allocated attachments = " + maintarget$attachmentstate.name() + ")");
    }

    private boolean allocateDepthAttachment(MainTarget.Dimension dimensions) {
        RenderSystem.assertOnRenderThreadOrInit();
        GlStateManager._getError();
        GlStateManager._bindTexture(this.depthBufferId);
        GlStateManager._texImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, dimensions.width, dimensions.height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (IntBuffer)null);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GlStateManager._texParameter(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        GlStateManager._bindTexture(0);
        return GlStateManager._getError() != 1285;
    }

    enum AttachmentState {
        NONE,
        DEPTH_BUFFER;

        private static final AttachmentState[] VALUES = values();

        AttachmentState with(AttachmentState p_166164_) {
            return VALUES[this.ordinal() | p_166164_.ordinal()];
        }
    }
}
