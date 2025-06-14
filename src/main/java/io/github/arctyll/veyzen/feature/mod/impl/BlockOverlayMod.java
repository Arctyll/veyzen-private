/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import io.github.arctyll.veyzen.helpers.render.Helper3D;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockOverlayMod extends Mod {

    public BlockOverlayMod() {
        super(
                "BlockOverlay",
                "Adds an customizable overlay to blocks.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Outline", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Filling", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Thickness", this, 20, 3));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Outline Color", this, new Color(0, 0, 0), new Color(255, 0, 0), 0, new float[]{0, 65}));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Filling Color", this, new Color(0, 0, 0), new Color(255, 0, 0), 0, new float[]{0, 65}));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Alpha", this, 255, 100));
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent e) {
        e.setCanceled(true);
        drawSelectionBox(e.player, e.target, e.subID, e.partialTicks);
    }

    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(getThickness());
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = Veyzen.INSTANCE.mc.theWorld.getBlockState(blockpos).getBlock();

            if (block.getMaterial() != Material.air && Veyzen.INSTANCE.mc.theWorld.getWorldBorder().contains(blockpos)) {
                block.setBlockBoundsBasedOnState(Veyzen.INSTANCE.mc.theWorld, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;

                double var = 0.002F;
                AxisAlignedBB box =
                        block.getSelectedBoundingBox(Veyzen.INSTANCE.mc.theWorld, blockpos)
                                .expand(var, var, var)
                                .offset(-d0, -d1, -d2);

                if (isFilling()) {
                    GlStateManager.color(
                            getFillColor().getRed() / 255f,
                            getFillColor().getGreen() / 255f,
                            getFillColor().getBlue() / 255f,
                            getAlpha() / 255f
                    );
                    Helper3D.drawFilledBoundingBox(box);
                }
                if (isOutline()) {
                    GlStateManager.color(
                            getOutlineColor().getRed() / 255f,
                            getOutlineColor().getGreen() / 255f,
                            getOutlineColor().getBlue() / 255f,
                            getAlpha() / 255f
                    );
                    RenderGlobal.drawSelectionBoundingBox(box);
                }
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    private float getThickness() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Thickness").getCurrentNumber();
    }

    private boolean isOutline() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Outline").isCheckToggled();
    }

    private boolean isFilling() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Filling").isCheckToggled();
    }

    private Color getFillColor() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Filling Color").getColor();
    }

    private Color getOutlineColor() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Outline Color").getColor();
    }

    private int getAlpha() {
        return (int) Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Alpha").getCurrentNumber();
    }
}
