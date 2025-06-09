package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

public class TimeChangerMod extends Mod {
    public TimeChangerMod() {
        super(
                "TimeChanger",
                "Changes the time of the current World visually.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Offset", this, 12000, 0));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Speed", this, 50, 1));
    }
}
