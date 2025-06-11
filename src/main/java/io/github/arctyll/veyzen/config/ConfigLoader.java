package io.github.arctyll.veyzen.config;

import com.google.gson.Gson;
import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.option.Option;
import io.github.arctyll.veyzen.feature.setting.Setting;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.helpers.OSHelper;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ConfigLoader {

    public static void loadConfig() throws FileNotFoundException {
        FileReader reader = new FileReader(OSHelper.getVeyzenDirectory() + "config.json");

        Config config = new Gson().fromJson(reader, Config.class);

        for (ModConfig modConfig : config.getConfig()) {
            Mod mod = Veyzen.INSTANCE.modManager.getMod(modConfig.getName());
            if (mod == null) continue;

            mod.setToggled(modConfig.isToggled());

            for (Setting configSetting : modConfig.getSettings()) {
                Setting clientSetting = Veyzen.INSTANCE.settingManager.getSettingByModAndName(mod.getName(), configSetting.getName());
                if (clientSetting == null) continue;

                switch (configSetting.getMode()) {
                    case "CheckBox":
                        clientSetting.setCheckToggled(configSetting.isCheckToggled());
                        break;
                    case "Slider":
                        clientSetting.setCurrentNumber(configSetting.getCurrentNumber());
                        break;
                    case "ModePicker":
                        clientSetting.setCurrentMode(configSetting.getCurrentMode());
                        clientSetting.setModeIndex(configSetting.getModeIndex());
                        break;
                    case "ColorPicker":
                        clientSetting.setColor(configSetting.getColor());
                        clientSetting.setSideColor(configSetting.getSideColor());
                        clientSetting.setSideSlider(configSetting.getSideSlider());
                        clientSetting.setMainSlider(configSetting.getMainSlider());
                        break;
                    case "CellGrid":
                        clientSetting.setCells(configSetting.getCells());
                        break;
                    case "Keybinding":
                        clientSetting.setKey(configSetting.getKey());
                        break;
                }
            }

            if (Veyzen.INSTANCE.hudEditor.getHudMod(modConfig.getName()) != null) {
                Veyzen.INSTANCE.hudEditor.getHudMod(modConfig.getName()).setX(modConfig.getPositions()[0]);
                Veyzen.INSTANCE.hudEditor.getHudMod(modConfig.getName()).setY(modConfig.getPositions()[1]);
                Veyzen.INSTANCE.hudEditor.getHudMod(modConfig.getName()).setSize(modConfig.getSize());
            }
        }

        for (Option configOption : config.getOptionsConfigList()) {
            Option clientOption = Veyzen.INSTANCE.optionManager.getOptionByName(configOption.getName());
            if (clientOption == null) continue;

            switch (configOption.getMode()) {
                case "CheckBox":
                    clientOption.setCheckToggled(configOption.isCheckToggled());
                    break;
                case "Slider":
                    clientOption.setCurrentNumber(configOption.getCurrentNumber());
                    break;
                case "ModePicker":
                    clientOption.setCurrentMode(configOption.getCurrentMode());
                    clientOption.setModeIndex(configOption.getModeIndex());
                    break;
                case "ColorPicker":
                    clientOption.setColor(configOption.getColor());
                    clientOption.setSideColor(configOption.getSideColor());
                    clientOption.setSideSlider(configOption.getSideSlider());
                    clientOption.setMainSlider(configOption.getMainSlider());
                    break;
                case "CellGrid":
                    clientOption.setCells(configOption.getCells());
                    break;
                case "Keybinding":
                    clientOption.setKey(configOption.getKey());
                    break;
            }
        }

        Style.setDarkMode(config.isDarkMode());
        Style.setSnapping(config.isSnapping());
    }
}
