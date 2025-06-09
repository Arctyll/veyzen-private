/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.mixins;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.impl.FreelookMod;
import io.github.arctyll.veyzen.feature.mod.impl.ZoomMod;
import io.github.arctyll.veyzen.helpers.render.Helper3D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V"))
    private void lockPlayerLooking(EntityPlayerSP instance, float x, float y) {
        if (!FreelookMod.cameraToggled) {
            instance.setAngles(x, y);
        }
    }

    @Inject(
            method = "updateCameraAndRender",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setAngles(FF)V", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci, boolean flag, float f, float f1, float f2, float f3, int i) {
        if (FreelookMod.cameraToggled) {
            FreelookMod.cameraYaw += f2 / 8;
            FreelookMod.cameraPitch += f3 / 8;
            if (Math.abs(FreelookMod.cameraPitch) > 90.0F) {
                FreelookMod.cameraPitch = FreelookMod.cameraPitch > 0.0F ? 90.0F : -90.0F;
            }
        }
    }

    @Shadow private Minecraft mc;
    @Shadow private float thirdPersonDistanceTemp;
    @Shadow private float thirdPersonDistance;

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 2))
    public void orientCamera(float x, float y, float z, float partialTicks){
        double double0 = mc.thePlayer.prevPosX + (mc.thePlayer.posX - mc.thePlayer.prevPosX) * (double) partialTicks;
        double double1 = mc.thePlayer.prevPosY + (mc.thePlayer.posY - mc.thePlayer.prevPosY) * (double) partialTicks + (double) mc.thePlayer.getEyeHeight();
        double double2 = mc.thePlayer.prevPosZ + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (double) partialTicks;
        double double3 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks;
        if(FreelookMod.cameraToggled){
            GlStateManager.translate(0.0F, 0.0F, (float) (-Helper3D.calculateCameraDistance(double0, double1, double2, double3)));
        }
        else {
            GlStateManager.translate(x, y, z);
        }
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        if (Veyzen.INSTANCE.modManager.getMod("NoHurtCam").isToggled())
            ci.cancel();
    }

    @Redirect(method = "getFOVModifier", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;fovSetting:F"))
    public float modifyFOV(GameSettings gameSettings) {
        if (Veyzen.INSTANCE.modManager.getMod("Zoom").isToggled()) {
            return ZoomMod.getFOV();
        }
        return gameSettings.fovSetting;
    }

    @Redirect(method = "setupCameraTransform", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;viewBobbing:Z", ordinal = 0))
    public boolean setupCameraTransform(GameSettings instance) {
        return !Veyzen.INSTANCE.optionManager.getOptionByName("Minimal View Bobbing").isCheckToggled() && Veyzen.INSTANCE.mc.gameSettings.viewBobbing;
    }
}
