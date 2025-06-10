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
import net.minecraftforge.client.event.sound.*;
import org.lwjgl.input.*;
import net.minecraft.client.audio.*;
import net.minecraft.client.resources.*;  

public class IconButton {  

    private final Animate animate = new Animate();  
    private final String icon;  
    private int x, y;  
    private final int w, h;  
    private boolean isHoveredLast = false;  

    public IconButton(String icon, int x, int y) {  
        this.icon = icon;  
        this.x = x;  
        this.y = y;  
        this.w = 20;  
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
									  isHovered ? new Color(255, 59, 59, 255).getRGB() : new Color(139, 0, 0, 255).getRGB(),
									  Veyzen.INSTANCE.optionManager.getOptionByName("Rounded Corners").isCheckToggled() ? 0 : -1  
									  );  

        Helper2D.drawPicture(x, y, w, h, 0xffffffff, "icon/" + icon);  
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

    public String getIcon() {  
        return icon;  
    }  
}  

