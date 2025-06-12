package io.github.arctyll.veyzen.gui.modmenu;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.gui.modmenu.impl.Panel;
import io.github.arctyll.veyzen.helpers.TimeHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

public class ModMenu extends GuiScreen {

    private final Panel panel = new Panel();
    private final Animate animateClock = new Animate();
    private final Animate animateSnapping = new Animate();
    private int scale = -1;

    public ModMenu() {
        animateClock.setEase(Easing.CUBIC_OUT).setMin(0).setMax(50).setSpeed(100).setReversed(false);
        animateSnapping.setEase(Easing.CUBIC_IN).setMin(0).setMax(50).setSpeed(100).setReversed(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Helper2D.drawRectangle(0, 0, width, height, 0x70000000);
        boolean rounded = Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();

        panel.renderPanel(mouseX, mouseY);

        animateClock.update();

        Helper2D.drawRoundedRectangle(width - 130, animateClock.getValueI() - 60, 140, 60, 10,
									  new Color(25, 103, 255).getRGB(), rounded ? 0 : -1);
        Helper2D.drawPicture(width - 50, 5 - 50 + animateClock.getValueI(), 40, 40,
							 new Color(255, 255, 255).getRGB(), "icon/clock.png");
        Veyzen.INSTANCE.fontHelper.size40.drawString(TimeHelper.getFormattedTimeMinute(),
													 width - 120, 10 - 50 + animateClock.getValueI(), Color.WHITE.getRGB());
        Veyzen.INSTANCE.fontHelper.size20.drawString(TimeHelper.getFormattedDate(),
													 width - 120, 30 - 50 + animateClock.getValueI(), Color.WHITE.getRGB());

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
        scale = mc.gameSettings.guiScale;
        mc.gameSettings.guiScale = 1;
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        if (scale != -1) mc.gameSettings.guiScale = scale;
        super.onGuiClosed();
    }
}
