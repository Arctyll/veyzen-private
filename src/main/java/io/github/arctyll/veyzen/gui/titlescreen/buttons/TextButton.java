/*  
 * Copyright (c) 2022 DupliCAT  
 * GNU Lesser General Public License v3.0  
 */  

package io.github.arctyll.veyzen.gui.titlescreen.buttons;  

import io.github.arctyll.veyzen.Veyzen;  
import io.github.arctyll.veyzen.helpers.MathHelper;  
import io.github.arctyll.veyzen.helpers.render.Helper2D;  
import io.github.arctyll.veyzen.helpers.animation.Animate;  
import io.github.arctyll.veyzen.helpers.animation.Easing;  

import net.minecraft.client.Minecraft;    
import net.minecraft.util.ResourceLocation;  

import java.awt.*;
import org.lwjgl.input.*;
import net.minecraft.client.audio.*;  

public class TextButton {  

	private final Animate animate = new Animate();  
	private final String text;  
	private int x, y;  
	private final int w, h;  
	private boolean isHoveredLast = false;  

	public TextButton(String text, int x, int y) {  
		this.text = text;  
		this.x = x;  
		this.y = y;  
		this.w = 150;  
		this.h = 20;  
		animate.setEase(Easing.LINEAR).setMin(0).setMax(25).setSpeed(200);  
	}  

	/**  
	 * Renders the button with the position, width and height taken from the constructor  
	 *  
	 * @param mouseX The current X position of the mouse  
	 * @param mouseY The current Y position of the mouse  
	 */  

	public void renderButton(int x, int y, int mouseX, int mouseY) {  
		this.x = x;  
		this.y = y;  

		boolean isHovered = isHovered(mouseX, mouseY);  
		animate.update().setReversed(!isHovered);  

		if (isHovered && Mouse.isButtonDown(0)) {
			playSound(Minecraft.getMinecraft().getSoundHandler());
		}  

		isHoveredLast = isHovered;  

		Helper2D.drawRoundedRectangle(x, y, w, h, 2,  
									  isHovered ? new Color(55, 59, 69, 255).getRGB() : new Color(49, 51, 56, 255).getRGB(),  
									  Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled() ? 0 : -1  
									  );  

		Veyzen.INSTANCE.fontHelper.size20.drawString(  
			text,  
			x + w / 2f - Veyzen.INSTANCE.fontHelper.size20.getStringWidth(text) / 2f,  
			y + h / 2f - 4,  
			0xffffffff  
		);  
	}  

	public boolean isHovered(int mouseX, int mouseY) {  
		return MathHelper.withinBox(x, y, w, h, mouseX, mouseY);  
	}  

	public void playSound(SoundHandler soundHandler) {
        soundHandler.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

	public int getW() {  
		return w;  
	}  

	public int getH() {  
		return h;  
	}  

	public int getX() {  
		return x;  
	}  

	public int getY() {  
		return y;  
	}  

	public String getText() {  
		return text;  
	}  
}
