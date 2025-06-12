/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.mods.impl.type;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.setting.Setting;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.mods.Button;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.mods.impl.Settings;
import io.github.arctyll.veyzen.helpers.render.GLHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import io.github.arctyll.veyzen.helpers.MathHelper;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;
import java.awt.*;

public class ModePicker extends Settings {

    private final Animate animateSelect = new Animate();
    private int longestString;

    public ModePicker(Setting setting, Button button, int y) {
        super(setting, button, y);
        setSettingHeight(setting.getOptions().length * 15 + 5);
        animateSelect.setEase(Easing.CUBIC_OUT).setMin(0).setMax(setting.getOptions().length * 15 + 2).setReversed(false);
    }

    /**
     * Renders the ModePicker Setting
     *
     * @param mouseX The current X position of the mouse
     * @param mouseY The current Y position of the mouse
     */

    @Override
    public void renderSetting(int mouseX, int mouseY) {
        boolean roundedCorners = Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();
        int color = Veyzen.INSTANCE.optionManager.getOptionByName("Color").getColor().getRGB();

        animateSelect.setSpeed(getSettingHeight() * 2).update();

        Veyzen.INSTANCE.fontHelper.size30.drawString(
                setting.getName(),
                button.getPanel().getX() + 20,
                button.getPanel().getY() + button.getPanel().getH() + getY() + 6,
                color
        );
        Veyzen.INSTANCE.fontHelper.size20.drawString(
                setting.getCurrentMode(),
                button.getPanel().getX() + button.getPanel().getW() - 20 - Veyzen.INSTANCE.fontHelper.size20.getStringWidth(setting.getCurrentMode()),
                button.getPanel().getY() + button.getPanel().getH() + getY() + 9,
                color
        );

        if (isOpen()) {
            if (!animateSelect.hasFinished()) {
                GLHelper.startScissor(
                        button.getPanel().getX(),
                        button.getPanel().getY() + button.getPanel().getH() + getY() + 23,
                        button.getPanel().getW(),
                        setting.getOptions().length * 15 + 2
                );
            }
            Helper2D.drawRoundedRectangle(
                    button.getPanel().getX() + button.getPanel().getW() - 30 - longestString,
                    button.getPanel().getY() + button.getPanel().getH() + getY() - setting.getOptions().length * 15 + animateSelect.getValueI() + 20,
                    longestString + 10, setting.getOptions().length * 15 + 2,
				    2, new Color(49, 51, 56, 255).getRGB(),
                    roundedCorners ? 0 : -1
            );

            int offset = 0;
            for (int i = 0; i < setting.getOptions().length; i++) {
                boolean hovered = MathHelper.withinBox(
                        button.getPanel().getX() + button.getPanel().getW() - 30 - longestString,
                        button.getPanel().getY() + button.getPanel().getH() + getY() + offset * 15 + 25,
                        longestString + 10, 15, mouseX, mouseY
                );

                Veyzen.INSTANCE.fontHelper.size20.drawString(
                        setting.getOptions()[i],
                        button.getPanel().getX() + button.getPanel().getW() - 24 - longestString,
                        button.getPanel().getY() + button.getPanel().getH() + getY() + offset * 15 - setting.getOptions().length * 15 + animateSelect.getValueI() + 25,
                        color
                );

                if (hovered) {
                    Helper2D.drawRoundedRectangle(
                            button.getPanel().getX() + button.getPanel().getW() - 30 - longestString,
                            button.getPanel().getY() + button.getPanel().getH() + getY() + offset * 15 - setting.getOptions().length * 15 + animateSelect.getValueI() + 20,
						    longestString + 10, 17, 2, new Color(48, 129, 242, 123).getRGB(),
                            roundedCorners ? 0 : -1
                    );
                }
                if (longestString < Veyzen.INSTANCE.fontHelper.size20.getStringWidth(setting.getOptions()[i])) {
                    longestString = Veyzen.INSTANCE.fontHelper.size20.getStringWidth(setting.getOptions()[i]);
                }
                offset++;
            }
            if (!animateSelect.hasFinished()) {
                GLHelper.endScissor();
            }
        }
    }

    /**
     * Opens and closes the setting
     * Loops through every Mode in the Setting and checks if it is pressed
     *
     * @param mouseX The current X position of the mouse
     * @param mouseY The current Y position of the mouse
     * @param mouseButton The current mouse button which is pressed
     */

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MathHelper.withinBox(
                button.getPanel().getX() + 15,
                button.getPanel().getY() + button.getPanel().getH() + getY() - 2,
                button.getPanel().getW() - 30, 20, mouseX, mouseY)
        ) {
            if (mouseButton == 0) {
                if (isOpen()) {
                    setH(25);
                    setOpen(false);
                } else {
                    animateSelect.reset();
                    setH(getSettingHeight() + 25);
                    setOpen(true);
                }
            }
        }
        if (isOpen()) {
            int offset = 0;
            for (int i = 0; i < setting.getOptions().length; i++) {
                boolean hovered = MathHelper.withinBox(
                        button.getPanel().getX() + button.getPanel().getW() - 30 - longestString,
                        button.getPanel().getY() + button.getPanel().getH() + getY() + offset * 15 + 25,
                        longestString + 10, 15, mouseX, mouseY
                );

                if (hovered) {
                    setting.setCurrentMode(setting.getOptions()[i]);
                }
                offset++;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }
}
