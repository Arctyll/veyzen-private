package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

import java.awt.*;

public class HitColorMod extends Mod {
    public HitColorMod() {
        super(
                "Hit Color",
                "Changes the color of damaged entities.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Damage Color", this, new Color(255, 0, 0), new Color(255, 0, 0), 0, new float[]{145, 0}));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Alpha", this, 255, 80));
    }
}
