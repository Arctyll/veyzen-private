/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.hudeditor.impl.impl.keystrokes.keys;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyboardKey {

    private Animate animate = new Animate();

    public KeyboardKey() {
        animate.setEase(Easing.CUBIC_IN).setMin(0).setMax(12).setSpeed(100);
    }

    public void renderKey(int x, int y, int width, int height, boolean modern, KeyBinding keyBinding, int color, int fontColor, boolean background) {
        boolean keyDown;
        if (Veyzen.INSTANCE.mc.currentScreen == null) {
            keyDown = Keyboard.isKeyDown(keyBinding.getKeyCode());
        } else {
            keyDown = false;
        }

        animate.setReversed(keyDown).update();

        if (modern) {
            if (background) {
                Helper2D.drawRoundedRectangle(x, y, width, height, 2, color, 0);
            }

            if (!animate.hasFinished() || keyDown) {
                Helper2D.drawRoundedRectangle(
                        x + animate.getValueI(),
                        y + animate.getValueI(),
                        width - animate.getValueI() * 2,
                        height - animate.getValueI() * 2,
                        2,
                        0x70ffffff,
                        0
                );
            }

            Veyzen.INSTANCE.fontHelper.size20.drawString(
                    Keyboard.getKeyName(keyBinding.getKeyCode()),
                    x - Veyzen.INSTANCE.fontHelper.size20.getStringWidth(Keyboard.getKeyName(keyBinding.getKeyCode())) / 2f + width / 2f,
                    y + height / 2f - 4,
                    fontColor
            );
        } else {
            if (background) {
                Helper2D.drawRectangle(x, y, width, height, color);
            }

            if (!animate.hasFinished() || keyDown) {
                Helper2D.drawRectangle(
                        x + animate.getValueI(),
                        y + animate.getValueI(),
                        width - animate.getValueI() * 2,
                        height - animate.getValueI() * 2,
                        0x70ffffff
                );
            }

            Veyzen.INSTANCE.mc.fontRendererObj.drawString(
                    Keyboard.getKeyName(keyBinding.getKeyCode()),
                    x - Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(Keyboard.getKeyName(keyBinding.getKeyCode())) / 2 + width / 2,
                    y + height / 2 - 4,
                    fontColor
            );
        }
    }
}
