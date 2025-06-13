/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.modmenu;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.gui.modmenu.impl.Panel;
import io.github.arctyll.veyzen.helpers.ResolutionHelper;
import io.github.arctyll.veyzen.helpers.TimeHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import io.github.arctyll.veyzen.helpers.MathHelper;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.awt.*;

public class ModMenu extends GuiScreen {

    private final Panel panel = new Panel();
    private final Animate animateClock = new Animate();
    private final Animate animateSnapping = new Animate();

    public ModMenu() {
        animateClock.setEase(Easing.CUBIC_OUT).setMin(0).setMax(50).setSpeed(100).setReversed(false);
        animateSnapping.setEase(Easing.CUBIC_IN).setMin(0).setMax(50).setSpeed(100).setReversed(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Helper2D.drawRectangle(0, 0, width, height, 0x70000000);
        boolean roundedCorners = Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();
        int color = Veyzen.INSTANCE.optionManager.getOptionByName("Color").getColor().getRGB();

        panel.renderPanel(mouseX, mouseY);

        animateClock.update();

        ScaledResolution scaled = new ScaledResolution(mc);
        float scale = scaled.getScaleFactor();

        int boxWidth = (int) (140 * scale);
        int boxHeight = (int) (60 * scale);
        int iconSize = (int) (40 * scale);
        int cornerRadius = (int) (10 * scale);
        int padding = (int) (10 * scale);

        int x = width - boxWidth - padding;
        int y = animateClock.getValueI() - boxHeight;

        Helper2D.drawRoundedRectangle(
            x,
            y,
            boxWidth,
            boxHeight,
            cornerRadius,
            new Color(25, 103, 255, 255).getRGB(),
            roundedCorners ? 0 : -1
        );

        Helper2D.drawPicture(
            x + boxWidth - iconSize - padding,
            y + padding,
            iconSize,
            iconSize,
            Color.WHITE.getRGB(),
            "icon/clock.png"
        );

        Veyzen.INSTANCE.fontHelper.size40.drawString(
            TimeHelper.getFormattedTimeMinute(),
            x + padding,
            y + (int) (8 * scale),
            Color.WHITE.getRGB()
        );

        Veyzen.INSTANCE.fontHelper.size20.drawString(
            TimeHelper.getFormattedDate(),
            x + padding,
            y + (int) (30 * scale),
            Color.WHITE.getRGB()
        );

        animateSnapping.update();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        panel.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        panel.setDragging(false);
        panel.mouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        panel.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        panel.initGui();
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        super.onGuiClosed();
    }
}
