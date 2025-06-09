/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

import java.awt.*;

public class CpsMod extends Mod {

    public CpsMod() {
        super(
                "CPS",
                "Shows your CPS on the HUD.",
                Type.Hud
        );

        String[] mode = {"Modern", "Legacy"};
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Mode", this, "Modern", 0, mode));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Background", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Right click", this, false));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }
}
