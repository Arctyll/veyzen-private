/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.hudeditor.impl.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.gui.hudeditor.HudEditor;
import io.github.arctyll.veyzen.gui.hudeditor.impl.HudMod;
import io.github.arctyll.veyzen.helpers.render.GLHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class ArmorHud extends HudMod {

    private final ItemStack[] emptyArmorInventory = new ItemStack[4];

    public ArmorHud(String name, int x, int y) {
        super(name, x, y);
        setW(25);
        setH(70);
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled()) {
            if (isBackground()) {
                if (isModern()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, Style.getColor(50).getRGB(), 0);
                } else {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
            }

            renderItem(new ItemStack(Items.diamond_helmet), getX() + 4, getY() + 2);
            renderItem(new ItemStack(Items.diamond_chestplate), getX() + 4, getY() + 16 + 2);
            renderItem(new ItemStack(Items.diamond_leggings), getX() + 4, getY() + 34 + 2);
            renderItem(new ItemStack(Items.diamond_boots), getX() + 4, getY() + 51 + 2);
            super.renderMod(mouseX, mouseY);
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Pre.Text e) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled() && !(Veyzen.INSTANCE.mc.currentScreen instanceof HudEditor)) {
            if (!Arrays.equals(Veyzen.INSTANCE.mc.thePlayer.inventory.armorInventory, emptyArmorInventory) ||
                    Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "No Armor Background").isCheckToggled()) {

                if (isBackground()) {
                    if (isModern()) {
                        Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, 0x50000000, 0);
                    } else {
                        Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                    }
                }

                renderItem(Veyzen.INSTANCE.mc.thePlayer.inventory.armorInventory[3], getX() + 4, getY() + 2);
                renderItem(Veyzen.INSTANCE.mc.thePlayer.inventory.armorInventory[2], getX() + 4, getY() + 16 + 2);
                renderItem(Veyzen.INSTANCE.mc.thePlayer.inventory.armorInventory[1], getX() + 4, getY() + 34 + 2);
                renderItem(Veyzen.INSTANCE.mc.thePlayer.inventory.armorInventory[0], getX() + 4, getY() + 51 + 2);
            }
        }
        GLHelper.endScale();
    }

    private void renderItem(ItemStack stack, int x, int y) {
        Veyzen.INSTANCE.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
    }

    private boolean isModern() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Mode").getCurrentMode().equalsIgnoreCase("Modern");
    }

    private boolean isBackground() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Background").isCheckToggled();
    }
}
