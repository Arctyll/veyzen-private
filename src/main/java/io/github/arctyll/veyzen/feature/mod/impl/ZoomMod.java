/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;
import io.github.arctyll.veyzen.helpers.hud.ScrollHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ZoomMod extends Mod {

    private static final Animate animate = new Animate();
    private static final ScrollHelper scrollHelper = new ScrollHelper(0, 0, 5, 50);
    private static boolean zoom = false;

    public ZoomMod() {
        super(
                "Zoom",
                "Allows you to zoom into the world.",
                Type.Mechanic
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_C));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Zoom Amount", this, 100, 30));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Smooth Zoom", this, true));

        animate.setEase(Easing.LINEAR).setSpeed(700);
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Text e) {
        animate.setMin(getAmount() / 2).setMax(Veyzen.INSTANCE.mc.gameSettings.fovSetting).update();
        scrollHelper.setMinScroll(isSmooth() ? animate.getValueI() - 5 : getAmount() - 5);
        scrollHelper.update();

        if (zoom && Veyzen.INSTANCE.mc.currentScreen == null) {
            int scrollInt = org.lwjgl.input.Mouse.getDWheel();
            scrollHelper.updateScroll(scrollInt);
        } else {
            scrollHelper.setScrollStep(0);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        zoom = Keyboard.isKeyDown(getKey());
        animate.setReversed(zoom);
    }

    public static float getFOV() {
        if (isSmooth()) {
            return animate.getValueI() - scrollHelper.getCalculatedScroll();
        }
        return zoom ? getAmount() - scrollHelper.getCalculatedScroll() : Veyzen.INSTANCE.mc.gameSettings.fovSetting;
    }

    public static boolean isZoom() {
        return zoom;
    }

    private static boolean isSmooth() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName("Zoom", "Smooth Zoom").isCheckToggled();
    }

    private static float getAmount() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName("Zoom", "Zoom Amount").getCurrentNumber();
    }

    private int getKey() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
