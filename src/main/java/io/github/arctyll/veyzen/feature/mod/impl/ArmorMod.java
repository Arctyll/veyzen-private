/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

public class ArmorMod extends Mod {

    public ArmorMod() {
        super(
                "Armor Status",
                "Displays your Armor on the HUD.",
                Type.Hud
        );

        String[] mode = {"Modern", "Legacy"};
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Mode", this, "Modern", 0, mode));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("No Armor Background", this, true));
    }
}
