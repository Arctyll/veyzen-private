/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiTweaksMod extends Mod {

    public GuiTweaksMod() {
        super(
                "Gui Tweaks",
                "Adds Tweaks to the Gui like blur and transparency.",
                Type.Tweaks
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Blur Background", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Darken Background", this, true));
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Blur Background").isCheckToggled()) {
            if (!(e.gui instanceof GuiChat)) {
                try {
                    Veyzen.INSTANCE.mc.entityRenderer.loadShader(
                            new ResourceLocation("shaders/post/blur.json"));
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            if (e.gui == null) {
                if (Veyzen.INSTANCE.mc.entityRenderer.getShaderGroup() != null) {
                    Veyzen.INSTANCE.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
            }
        }
    }
}
