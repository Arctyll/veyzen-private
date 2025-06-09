/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;

public class NickHiderMod extends Mod {

    public NickHiderMod() {
        super(
                "NickHider",
                "Hides your nickname in game by replacing it.",
                Type.Visual
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Nickname", this, "Name", "You", 3));
    }

    public static String replaceNickname(String nick) {
        if(Veyzen.INSTANCE != null) {
            if (Veyzen.INSTANCE.modManager != null) {
                if (Veyzen.INSTANCE.modManager.getMod("NickHider").isToggled()) {
                    return nick.replace(
                            Veyzen.INSTANCE.mc.getSession().getUsername(),
                            Veyzen.INSTANCE.settingManager.getSettingByModAndName("NickHider", "Nickname").getText()
                    );
                }
            }
        }
        return nick;
    }
}
