/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.modmenu.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.option.Option;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.mods.Button;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.options.Options;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.options.type.*;
import io.github.arctyll.veyzen.gui.modmenu.impl.sidebar.TextBox;
import io.github.arctyll.veyzen.helpers.ResolutionHelper;
import io.github.arctyll.veyzen.helpers.render.GLHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import io.github.arctyll.veyzen.helpers.MathHelper;
import io.github.arctyll.veyzen.helpers.hud.ScrollHelper;
import io.github.arctyll.veyzen.helpers.animation.Animate;
import io.github.arctyll.veyzen.helpers.animation.Easing;

import java.util.ArrayList;
import java.io.*;
import org.lwjgl.input.*;
import java.awt.*;

public class Panel {

    private final ArrayList<Button> buttonList = new ArrayList<>();
    private final ArrayList<Options> optionsList = new ArrayList<>();
    private final String[] sideButtons = {"Mods", "Settings"};
    private final Animate animateSideBar = new Animate();
    private final Animate animateTransition = new Animate();
    private final ScrollHelper scrollHelperMods = new ScrollHelper(0, 270, 35, 300);
    private final ScrollHelper scrollHelperOptions = new ScrollHelper(0, 300, 35, 300);
    private final TextBox textBox = new TextBox("Search", 0, 0, 150, 20);
    private int x, y, w, h;
    private int offsetX, offsetY;
    private boolean dragging;
    private boolean anyButtonOpen;
    private int selected = 0;
    private Type selectedType = Type.All;

    public Panel() {
        this.x = ResolutionHelper.getWidth() / 2 - 250;
        this.y = ResolutionHelper.getHeight() / 2 - 150;
        this.w = 500;
        this.h = 30;
        this.offsetX = 0;
        this.offsetY = 0;
        this.dragging = false;

        initButtons();

        int addOptionY = 10;

        for (Option option : Veyzen.INSTANCE.optionManager.getOptions()) {
            switch (option.getMode()) {
                case "CheckBox":
                    CheckBox checkBox = new CheckBox(option, this, addOptionY);
                    optionsList.add(checkBox);
                    addOptionY += checkBox.getH();
                    break;
                case "Slider":
                    Slider slider = new Slider(option, this, addOptionY);
                    optionsList.add(slider);
                    addOptionY += slider.getH();
                    break;
                case "ModePicker":
                    ModePicker modePicker = new ModePicker(option, this, addOptionY);
                    optionsList.add(modePicker);
                    addOptionY += modePicker.getH();
                    break;
                case "ColorPicker":
                    ColorPicker colorPicker = new ColorPicker(option, this, addOptionY);
                    optionsList.add(colorPicker);
                    addOptionY += colorPicker.getH();
                    break;
                case "CellGrid":
                    CellGrid cellGrid = new CellGrid(option, this, addOptionY);
                    optionsList.add(cellGrid);
                    addOptionY += cellGrid.getH();
                    break;
                case "Keybinding":
                    Keybinding keybinding = new Keybinding(option, this, addOptionY);
                    optionsList.add(keybinding);
                    addOptionY += keybinding.getH();
                    break;
                case "Category":
                    Category category = new Category(option, this, addOptionY);
                    optionsList.add(category);
                    addOptionY += category.getH();
                    break;
            }
        }

        animateSideBar.setEase(Easing.CUBIC_IN_OUT).setMin(0).setMax(40).setSpeed(200);
        animateTransition.setEase(Easing.CUBIC_IN_OUT).setMin(0).setMax(300).setSpeed(500);
    }

    public void initButtons() {
        buttonList.clear();
        scrollHelperMods.setScrollStep(0);
        int addButtonX = 0;
        int addButtonY = 0;
        int buttonCounter = 0;
        for (Mod mod : Veyzen.INSTANCE.modManager.getMods()) {
            if (selectedType.equals(mod.getType()) || selectedType.equals(Type.All)) {
                if (textBox.getText().equals("") || mod.getName().toLowerCase().contains(textBox.getText().toLowerCase())) {
                    Button button = new Button(mod, this, addButtonX, addButtonY);
                    buttonList.add(button);
                    buttonCounter++;
                    addButtonX += button.getW() + 3;
                    if (buttonCounter % 4 == 0) {
                        addButtonX = 0;
                        addButtonY += button.getH() + 3;
                    }
                }
            }
        }
    }

    /**
	 * Renders the panel background, the sidebar and all the buttons
	 *
	 * @param mouseX The current X position of the mouse
	 * @param mouseY The current Y position of the mouse
	 */

	public void renderPanel(int mouseX, int mouseY) {
		final boolean roundedCorners = true;

		final Color BACKGROUND = new Color(21, 22, 23, 255);
		final Color PANEL = new Color(34, 35, 38, 255);
		final Color ACCENT = new Color(20, 82, 204, 255);
		final Color HOVERED = new Color(25, 103, 255, 255);
		final Color SIDEBAR = new Color(42, 44, 48, 255);
		final Color SELECTED = new Color(34, 35, 38, 255);
		final Color TEXT = new Color(255, 255, 255);

		final int HEADER_HEIGHT = 30;
		final int TAB_OFFSET_Y = 30;
		final int MODS_AREA_EXTRA_HEIGHT = 270;
		final int OPTIONS_AREA_EXTRA_HEIGHT = 240;
		final int TAB_BAR_HEIGHT = 30;
		final int TEXTBOX_OFFSET_Y = 5;
		final int CLOSE_BUTTON_SIZE = 20;
		final int CLOSE_BUTTON_MARGIN = 5;

		Helper2D.drawRoundedRectangle(x, y, w, h, roundedCorners ? 4 : -1, PANEL.getRGB(), 1);

		Helper2D.drawRoundedRectangle(
			x,
			selected == 1 ? y + TAB_OFFSET_Y : y + TAB_OFFSET_Y * 2,
			w,
			selected == 1 ? h + MODS_AREA_EXTRA_HEIGHT : h + OPTIONS_AREA_EXTRA_HEIGHT,
			roundedCorners ? 4 : -1,
			BACKGROUND.getRGB(),
			1
		);

		if (selected == 0) {
			Helper2D.drawRectangle(x, y + TAB_OFFSET_Y, w, TAB_BAR_HEIGHT, SIDEBAR.getRGB());
		}

		boolean hoveredClose = MathHelper.withinBox(x + w - CLOSE_BUTTON_SIZE - CLOSE_BUTTON_MARGIN, y + CLOSE_BUTTON_MARGIN, CLOSE_BUTTON_SIZE, CLOSE_BUTTON_SIZE, mouseX, mouseY);
		Helper2D.drawRoundedRectangle(
			x + w - CLOSE_BUTTON_SIZE - CLOSE_BUTTON_MARGIN,
			y + CLOSE_BUTTON_MARGIN,
			CLOSE_BUTTON_SIZE,
			CLOSE_BUTTON_SIZE,
			roundedCorners ? 3 : -1,
			(hoveredClose ? HOVERED : SIDEBAR).getRGB(),
			1
		);

		Helper2D.drawPicture(x + w - CLOSE_BUTTON_SIZE - CLOSE_BUTTON_MARGIN, y + CLOSE_BUTTON_MARGIN, CLOSE_BUTTON_SIZE, CLOSE_BUTTON_SIZE, TEXT.getRGB(), "icon/cross.png");

		Helper2D.drawPicture(x + 2, y - 1, 35, 35, TEXT.getRGB(), "veyzenlogo.png");
		Veyzen.INSTANCE.fontHelper.size40.drawString(Veyzen.modName, x + 37, y + 6, TEXT.getRGB());

		animateTransition.update();
		animateSideBar.update();
		Veyzen.INSTANCE.messageHelper.renderMessage();

		if (selected == 0) {
			int offset = 0;
			for (Type type : Type.values()) {
				String text = type.name();
				int length = Veyzen.INSTANCE.fontHelper.size20.getStringWidth(text);

				Helper2D.drawRoundedRectangle(
					x + offset + 5,
					y + h + TEXTBOX_OFFSET_Y,
					length + 25,
					20,
					roundedCorners ? 3 : -1,
					(selectedType.equals(type) ? ACCENT : SIDEBAR).getRGB(),
					1
				);

				Helper2D.drawPicture(x + offset + 8, y + h + 8, 15, 15, -1, "icon/" + type.getIcon());
				Veyzen.INSTANCE.fontHelper.size20.drawString(text, x + offset + 26, y + h + 11, TEXT.getRGB());

				offset += length + 30;
			}

			textBox.renderTextBox(x + w - textBox.getW() - 5, y + h + TEXTBOX_OFFSET_Y, mouseX, mouseY);

			if (MathHelper.withinBox(x, y + TAB_OFFSET_Y * 2, w, h + OPTIONS_AREA_EXTRA_HEIGHT, mouseX, mouseY)) {
				scrollHelperMods.updateScroll(Mouse.getDWheel());
			}
			scrollHelperMods.update();

			int index = 0;
			int count = 0;
			for (Button button : buttonList) {
				float position = scrollHelperMods.getCalculatedScroll() + count * (button.getH() + 3);
				button.setY((int) position);
				index++;
				if (index % 4 == 0) count++;
			}

			GLHelper.startScissor(x, y + TAB_OFFSET_Y * 2, w, h + OPTIONS_AREA_EXTRA_HEIGHT);
			for (Button button : buttonList) {
				button.renderButton(mouseX, mouseY);
			}
			GLHelper.endScissor();
		} else if (selected == 1) {
			if (MathHelper.withinBox(x, y + TAB_OFFSET_Y, w, h + MODS_AREA_EXTRA_HEIGHT, mouseX, mouseY)) {
				scrollHelperOptions.updateScroll(Mouse.getDWheel());
			}
			scrollHelperOptions.update();

			int height = 0;
			for (Options option : optionsList) {
				float position = height + scrollHelperOptions.getCalculatedScroll() + 10;
				option.setY((int) position);
				height += option.getH();
			}

			GLHelper.startScissor(x, y + TAB_OFFSET_Y, w, h + MODS_AREA_EXTRA_HEIGHT);
			for (Options option : optionsList) {
				option.renderOption(mouseX, mouseY);
			}
			GLHelper.endScissor();
		}

		Helper2D.drawRoundedRectangle(x - 50, y, 40, h + 300, roundedCorners ? 4 : -1, SIDEBAR.getRGB(), 1);

		int selectorOffset = selected == 1 ? animateSideBar.getValueI() : 40 - animateSideBar.getValueI();
		Helper2D.drawRoundedRectangle(x - 50, y + selectorOffset, 40, 40, roundedCorners ? 4 : -1, SELECTED.getRGB(), 1);

		int buttonIndex = 0;
		for (String button : sideButtons) {
			Veyzen.INSTANCE.fontHelper.size15.drawString(button, x - 30 - Veyzen.INSTANCE.fontHelper.size15.getStringWidth(button) / 2f, y + 30 + buttonIndex * 40, TEXT.getRGB());
			Helper2D.drawPicture(x - 40, y + 5 + buttonIndex * 40, 20, 20, TEXT.getRGB(), "icon/button/sidebar/" + button.toLowerCase() + ".png");
			buttonIndex++;
		}
	}

    /**
     * Closes the modmenu and opens the editor if the close button is pressed
     * Sets the "selected" variable to whatever tab is pressed in the sidebar
     *
     * @param mouseX      The current X position of the mouse
     * @param mouseY      The current Y position of the mouse
     * @param mouseButton The current mouse button which is pressed
     */

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            int index = 0;
            for (String button : sideButtons) {
                if (MathHelper.withinBox(x - 50, y + index * 40, 40, 39, mouseX, mouseY)) {
                    if (selected != index) {
                        animateSideBar.reset();
                        animateTransition.reset();
                    }
                    selected = index;
                }
                index++;
            }

            int offset = 0;
            for (Type type : Type.values()) {
                String text = type.name();
                int length = Veyzen.INSTANCE.fontHelper.size20.getStringWidth(text);
                if (MathHelper.withinBox(x + offset + 5, y + h + 5, length + 25, 20, mouseX, mouseY)) {
                    selectedType = type;
                    scrollHelperMods.setScrollStep(0);
                    initButtons();
                }
                offset += length + 30;
            }


            if (MathHelper.withinBox(x + w - 25, y + 5, 20, 20, mouseX, mouseY)) {
                Veyzen.INSTANCE.mc.displayGuiScreen(Veyzen.INSTANCE.hudEditor);
            }
			
			if (textBox.isHovered(mouseX, mouseY)) {
				textBox.setFocused(true);
			} else {
				textBox.setFocused(false);
			}
        }

        if (selected == 0) {
            for (Button button : buttonList) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        } else {
            for (Options option : optionsList) {
                option.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (selected == 0) {
            for (Button button : buttonList) {
                button.mouseReleased(mouseX, mouseY, state);
            }
        } else {
            for (Options option : optionsList) {
                option.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if(isAnyButtonOpen()) {
            for (Button button : buttonList) {
                button.keyTyped(typedChar, keyCode);
            }
            for (Options option : optionsList) {
                option.keyTyped(typedChar, keyCode);
            }
        } else {
            textBox.keyTyped(typedChar, keyCode);
            initButtons();
        }
    }

    public void initGui() {
        setX(ResolutionHelper.getWidth() / 2 - (getW() / 2));
		setY(10);
    }

    /**
     * Updates the position of the panel
     *
     * @param mouseX The current X position of the mouse
     * @param mouseY The current Y position of the mouse
     */

//    public void updatePosition(int mouseX, int mouseY) {
//        if (isDragging()) {
//            setX(mouseX - offsetX);
//            setY(mouseY - offsetY);
//        }
//    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isAnyButtonOpen() {
        return anyButtonOpen;
    }

    public void setAnyButtonOpen(boolean anyButtonOpen) {
        this.anyButtonOpen = anyButtonOpen;
    }
}
