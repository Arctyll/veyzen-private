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
import io.github.arctyll.veyzen.helpers.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReachdisplayHud extends HudMod {

    private double range;

    public ReachdisplayHud(String name, int x, int y) {
        super(name, x, y);
        setW(80);
        setH(20);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (Veyzen.INSTANCE.mc.objectMouseOver != null &&
                Veyzen.INSTANCE.mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY)
        ) {
            Vec3 vec3 = Veyzen.INSTANCE.mc.getRenderViewEntity().getPositionEyes(1);
            range = Veyzen.INSTANCE.mc.objectMouseOver.hitVec.distanceTo(vec3);
        }
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled()) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, Style.getColor(50).getRGB(), 0);
                }
                Veyzen.INSTANCE.fontHelper.size20.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2f - (Veyzen.INSTANCE.fontHelper.size20.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2 - (Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2,
                        getY() + 6,
                        getColor()
                );
            }
            super.renderMod(mouseX, mouseY);
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Pre.Text e) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled() && !(Veyzen.INSTANCE.mc.currentScreen instanceof HudEditor)) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, 0x50000000, 0);
                }
                Veyzen.INSTANCE.fontHelper.size20.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2f - (Veyzen.INSTANCE.fontHelper.size20.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2f,
                        getY() + 6,
                        getColor()
                );
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(
                        MathHelper.round(range, 2) + " Blocks",
                        getX() + getW() / 2 - (Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(MathHelper.round(range, 2) + " Blocks")) / 2,
                        getY() + 6,
                        getColor()
                );
            }
        }
        GLHelper.endScale();
    }

    public int getColor() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Font Color").getColor().getRGB();
    }

    private boolean isModern() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Mode").getCurrentMode().equalsIgnoreCase("Modern");
    }

    private boolean isBackground() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Background").isCheckToggled();
    }
}
