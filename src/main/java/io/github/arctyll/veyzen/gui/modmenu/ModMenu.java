/*

 Copyright (c) 2022 DupliCAT

 GNU Lesser General Public License v3.0
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

	/**  
	 * Draws the panel of the modmenu used to toggle mods and change settings  
	 *  
	 * @param mouseX The current X position of the mouse  
	 * @param mouseY The current Y position of the mouse  
	 * @param partialTicks The partial ticks used for rendering  
	 */  

	@Override  
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {  
		Helper2D.drawRectangle(0, 0, width, height, 0x70000000);  
		boolean roundedCorners = Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled();  
		int color = Veyzen.INSTANCE.optionManager.getOptionByName("Color").getColor().getRGB();  

		panel.renderPanel(mouseX, mouseY);  

		/*  
		 Draws the time at the top right  
		 */  

		animateClock.update();  

		Helper2D.drawRoundedRectangle(width - 130, animateClock.getValueI() - 60, 140, 60, 10, new Color(25, 103, 255, 255).getRGB(), roundedCorners ? 0 : -1);  
		Helper2D.drawPicture(width - 50, 5 - 50 + animateClock.getValueI(), 40, 40, new Color(255, 255, 255, 255).getRGB(), "icon/clock.png");  

		Veyzen.INSTANCE.fontHelper.size40.drawString(TimeHelper.getFormattedTimeMinute(), width - 120, 10 - 50 + animateClock.getValueI(), Color.WHITE.getRGB());  
		Veyzen.INSTANCE.fontHelper.size20.drawString(TimeHelper.getFormattedDate(), width - 120, 30 - 50 + animateClock.getValueI(), Color.WHITE.getRGB());  

		animateSnapping.update();  
	}  

	/**  
	 * Sets different values of the panel when any mouse button is clicked  
	 * Changes the darkMode boolean if the button in the bottom left is pressed  
	 *  
	 * @param mouseX The current X position of the mouse  
	 * @param mouseY The current Y position of the mouse  
	 * @param mouseButton The current mouse button which is pressed  
	 */  

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

	/**  
	 * Loads a shader to blur the screen when the gui is opened  
	 */  

	@Override  
	public void initGui() {  
		panel.initGui();  
		mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));  
		super.initGui();  
	}  

	/**  
	 * Deleted all shaderGroups in order to remove the screen blur when the gui is closed  
	 */  

	@Override  
	public void onGuiClosed() {  
		if (mc.entityRenderer.getShaderGroup() != null) {  
			mc.entityRenderer.getShaderGroup().deleteShaderGroup();  
		}  
		super.onGuiClosed();  
	}
}
