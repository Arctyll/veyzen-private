package io.github.arctyll.veyzen.mixins;

import io.github.arctyll.veyzen.helpers.render.Helper2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Minecraft.class)
public class SplashScreenMixins {

    @Inject(method = "drawSplashScreen", at = @At("HEAD"), cancellable = true)
    private void drawCustomSplashScreen(ScaledResolution scaledResolution, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        Helper2D.drawRectangle(0, 0, width, height, new Color(49, 51, 56, 255).getRGB());

        int logoWidth = 512;
        int logoHeight = 256;
        int x = (width - logoWidth) / 2;
        int y = (height - logoHeight) / 2;
        Helper2D.drawPicture(x, y, logoWidth, logoHeight, 0xffffffff, "veyzenlogo.png");

        mc.getFramebuffer().bindFramebuffer(true);
        mc.getFramebuffer().framebufferRender(width, height);
        GL11.glFlush();

        ci.cancel();
    }
}
