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
import io.github.arctyll.veyzen.gui.*;

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
}
