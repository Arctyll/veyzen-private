/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.options.type;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.option.Option;
import io.github.arctyll.veyzen.gui.modmenu.impl.Panel;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.options.Options;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import java.awt.*;

public class Category extends Options {

    public Category(Option option, Panel panel, int y) {
        super(option, panel, y);
    }

    @Override
    public void renderOption(int mouseX, int mouseY) {
        Veyzen.INSTANCE.fontHelper.size20.drawString(
                option.getName(),
                panel.getX() + 20,
                panel.getY() + panel.getH() + getY() + 6,
                0xffffffff
        );
        Helper2D.drawRectangle(panel.getX() + 20, panel.getY() + panel.getH() + getY() + 20, panel.getW() - 40, 1, new Color(34, 35, 38, 230).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }
}
