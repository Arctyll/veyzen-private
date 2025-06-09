/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ParticleMultiplierMod extends Mod {

    public ParticleMultiplierMod() {
        super(
                "ParticleMultiplier",
                "Multiplies or adds Particles by a given amount.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Particle Amount", this, 15, 5));
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (e.target instanceof EntityLivingBase) {
            boolean doCriticalDamage =
                    Veyzen.INSTANCE.mc.thePlayer.fallDistance > 0.0F &&
                    !Veyzen.INSTANCE.mc.thePlayer.onGround &&
                    !Veyzen.INSTANCE.mc.thePlayer.isOnLadder() &&
                    !Veyzen.INSTANCE.mc.thePlayer.isInWater() &&
                    !Veyzen.INSTANCE.mc.thePlayer.isPotionActive(Potion.blindness) &&
                            Veyzen.INSTANCE.mc.thePlayer.ridingEntity == null;

            for (int i = 0; i < getAmount(); i++) {
                Veyzen.INSTANCE.mc.thePlayer.onEnchantmentCritical(e.target);
                if (doCriticalDamage) {
                    Veyzen.INSTANCE.mc.thePlayer.onCriticalHit(e.target);
                }
            }

        }
    }

    private float getAmount() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Particle Amount").getCurrentNumber();
    }
}
