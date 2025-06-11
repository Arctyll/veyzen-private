package io.github.arctyll.veyzen.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import io.github.arctyll.veyzen.helpers.render.*;

public class GuiSplashScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private Framebuffer framebuffer;

    public void draw() {
        if (framebuffer == null) {
            framebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        }

        ScaledResolution sr = new ScaledResolution(mc);
        int scaleFactor = sr.getScaleFactor();

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, sr.getScaledWidth(), sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);

        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Helper2D.drawRectangle(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0).getRGB());

        int logoWidth = 256;
        int logoHeight = 128;
        int x = (sr.getScaledWidth() - logoWidth) / 2;
        int y = (sr.getScaledHeight() - logoHeight) / 2;

        Helper2D.drawPicture(x, y, logoWidth, logoHeight, 0xffffffff, "veyzen.png");

        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(sr.getScaledWidth() * scaleFactor, sr.getScaledHeight() * scaleFactor);
        mc.updateDisplay();
    }
}
