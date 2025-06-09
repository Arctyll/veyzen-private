/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.mixins;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.gui.titlescreen.TitleScreen;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin {

    /**
     * Loads the custom Title Screen in dev.cloudmc.gui.titlescreen
     */

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci) {
        if (!Veyzen.INSTANCE.optionManager.getOptionByName("Disable Custom Title Screen").isCheckToggled()) {
            Veyzen.INSTANCE.mc.displayGuiScreen(new TitleScreen());
        }
    }
}
