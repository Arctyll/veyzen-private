/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AnimationMod extends Mod {

    public AnimationMod() {
        super(
                "Animation",
                "1.7 Animations in 1.8.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Block Animation", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Eat/Drink Animation", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Bow Animation", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Fishing Rod", this, true));
    }

    @SubscribeEvent
    public void onAnimation(TickEvent.ClientTickEvent e) {
        if (Veyzen.INSTANCE.mc.theWorld == null || Veyzen.INSTANCE.mc.thePlayer == null) return;
        if (e.phase == TickEvent.Phase.START) return;
        ItemStack heldItem = Veyzen.INSTANCE.mc.thePlayer.getHeldItem();
        if (Veyzen.INSTANCE.modManager.getMod("Animation").isToggled() && heldItem != null) {
            if (Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Block Animation").isCheckToggled() && heldItem.getItemUseAction() == EnumAction.BLOCK) {
                attemptSwing();
            } else if (Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Eat/Drink Animation").isCheckToggled() && heldItem.getItemUseAction() == EnumAction.DRINK) {
                attemptSwing();
            } else if (Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Bow Animation").isCheckToggled() && heldItem.getItemUseAction() == EnumAction.BOW) {
                attemptSwing();
            }
        }
    }

    /**
     * Swings the player's arm if you're holding the attack and use item keys at the same time and looking at a block.
     */
    private void attemptSwing() {
        if (Veyzen.INSTANCE.mc.thePlayer.getItemInUseCount() > 0) {
            final boolean mouseDown = Veyzen.INSTANCE.mc.gameSettings.keyBindAttack.isKeyDown() &&
                    Veyzen.INSTANCE.mc.gameSettings.keyBindUseItem.isKeyDown();
            if (mouseDown && Veyzen.INSTANCE.mc.objectMouseOver != null && Veyzen.INSTANCE.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                forceSwingArm();
            }
        }
    }

    /**
     * Forces the player to swing their arm.
     */
    private void forceSwingArm() {
        EntityPlayerSP player = Veyzen.INSTANCE.mc.thePlayer;
        int swingEnd = player.isPotionActive(Potion.digSpeed) ?
                (6 - (1 + player.getActivePotionEffect(Potion.digSpeed).getAmplifier())) : (player.isPotionActive(Potion.digSlowdown) ?
                (6 + (1 + player.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2) : 6);
        if (!player.isSwingInProgress || player.swingProgressInt >= swingEnd / 2 || player.swingProgressInt < 0) {
            player.swingProgressInt = -1;
            player.isSwingInProgress = true;
        }
    }
}
