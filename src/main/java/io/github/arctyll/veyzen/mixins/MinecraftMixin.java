/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.mixins;

import io.github.arctyll.veyzen.helpers.animation.*;
import io.github.arctyll.veyzen.helpers.render.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    
    long lastFrame = Sys.getTime();

    /**
     Calculates the delta time which is used for Animations
     */
    @Inject(method = "runGameLoop", at = @At("RETURN"))
    public void runGameLoop(CallbackInfo ci){
        long currentTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
        int deltaTime = (int) (currentTime - lastFrame);
        lastFrame = currentTime;
        DeltaTime.setDeltaTime(deltaTime);
    }
	
	@Inject(method = "drawSplashScreen", at = @At("HEAD"), cancellable = true)
    private void drawSplashScreen(TextureManager tm, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
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
