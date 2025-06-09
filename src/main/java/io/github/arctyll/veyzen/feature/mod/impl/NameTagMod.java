package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

import java.awt.*;

public class NameTagMod extends Mod {

    public NameTagMod() {
        super(
                "NameTag",
                "Adds tweaks to NameTags.",
                Type.Tweaks
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("NameTag in 3rd Person", this, true));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Opacity", this, 255, 64));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Size", this, 3, 1));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Y Position", this, 5, 2.5f));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Disable Player NameTags", this, false));
        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Font Color", this, new Color(255, 255, 255), new Color(255, 0, 0), 0, new float[]{0, 0}));
    }
}
