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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BlockinfoHud extends HudMod {

    public BlockinfoHud(String name, int x, int y) {
        super(name, x, y);
        setW(130);
        setH(30);
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled()) {
            if (isModern()) {
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, Style.getColor(50).getRGB(), 0);
                }
                setW(Veyzen.INSTANCE.fontHelper.size20.getStringWidth("Grass Block") + 42);
                Veyzen.INSTANCE.fontHelper.size20.drawString("Grass Block", getX() + 35, getY() + 10, getColor());

                ItemStack itemStack = new ItemStack(Blocks.grass);
                renderItem(itemStack);
            } else {
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), Style.getColor(50).getRGB());
                }
                setW(Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth("Grass Block") + 42);
                Veyzen.INSTANCE.mc.fontRendererObj.drawString("Grass Block", getX() + 35, getY() + 10, getColor());

                ItemStack itemStack = new ItemStack(Blocks.grass);
                renderItem(itemStack);
            }
            super.renderMod(mouseX, mouseY);
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Pre.Text e) {
        IBlockState blockState = getLookingAtBlockState();
        if (blockState == null) {
            return;
        }

        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled() && !(Veyzen.INSTANCE.mc.currentScreen instanceof HudEditor)) {

            World world = Veyzen.INSTANCE.mc.theWorld;
            BlockPos blockPos = getLookingAtBlockPos();
            int meta = blockState.getBlock().getDamageValue(world, blockPos);
            int id = Item.itemRegistry.getIDForObject(blockState.getBlock().getItem(world, blockPos));
            ItemStack finalItem = new ItemStack(Item.getItemById(id), 1, meta);
            if (finalItem.getItem() == null)
                finalItem = new ItemStack(blockState.getBlock());
            if (finalItem.getItem() == null) {
                GLHelper.endScale();
                return;
            }

            if (isModern()) {
                setW(Veyzen.INSTANCE.fontHelper.size20.getStringWidth(finalItem.getDisplayName()) + 42);
                if (isBackground()) {
                    Helper2D.drawRoundedRectangle(getX(), getY(), getW(), getH(), 2, 0x50000000, 0);
                }
                Veyzen.INSTANCE.fontHelper.size20.drawString(finalItem.getDisplayName(), getX() + 35, getY() + 10, getColor());
                renderItem(finalItem);
            } else {
                setW(Veyzen.INSTANCE.fontHelper.size20.getStringWidth(finalItem.getDisplayName()) + 42);
                if (isBackground()) {
                    Helper2D.drawRectangle(getX(), getY(), getW(), getH(), 0x50000000);
                }
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(finalItem.getDisplayName(), getX() + 35, getY() + 10, getColor());
                renderItem(finalItem);
            }
        }
        GLHelper.endScale();
    }

    private BlockPos getLookingAtBlockPos() {
        MovingObjectPosition objectMouseOver = Veyzen.INSTANCE.mc.objectMouseOver;
        if (objectMouseOver == null) {
            return null;
        }
        return objectMouseOver.getBlockPos();
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

    private void renderItem(ItemStack stack) {
        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        GL11.glTranslatef(getX() + 3, getY() + 3, 1);
        GL11.glScalef(1.5f, 1.5f, 1);
        GL11.glTranslatef(-(getX() + 3), -(getY() + 3), -1);
        Veyzen.INSTANCE.mc.getRenderItem().renderItemAndEffectIntoGUI(
                stack, getX() + 3, getY() + 3
        );

        GL11.glPopMatrix();
    }

    private IBlockState getLookingAtBlockState() {
        if (Veyzen.INSTANCE.mc.objectMouseOver != null && Veyzen.INSTANCE.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && Veyzen.INSTANCE.mc.objectMouseOver.getBlockPos() != null) {
            BlockPos blockpos = Veyzen.INSTANCE.mc.objectMouseOver.getBlockPos();
            return Veyzen.INSTANCE.mc.theWorld.getBlockState(blockpos);
        }
        return null;
    }
}
