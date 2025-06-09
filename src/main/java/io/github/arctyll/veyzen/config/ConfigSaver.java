/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.config;

import com.google.gson.Gson;
import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.option.Option;
import io.github.arctyll.veyzen.gui.Style;
import io.github.arctyll.veyzen.helpers.OSHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigSaver {

    /**
     * Creates and saves a configuration in .minecraft/veyzen/config.json
     */

    public static void saveConfig() throws IOException {
        createDir();

        FileWriter writer = new FileWriter(OSHelper.getVeyzenDirectory() + "config.json");

        Config config = new Config();

        for (Mod mod : Veyzen.INSTANCE.modManager.getMods()) {
            ModConfig modConfig = new ModConfig(
                    mod.getName(),
                    mod.isToggled(),
                    Veyzen.INSTANCE.settingManager.getSettingsByMod(mod),
                    Veyzen.INSTANCE.hudEditor.getHudMod(mod.getName()) != null ?
                            new int[] { Veyzen.INSTANCE.hudEditor.getHudMod(mod.getName()).getX(), Veyzen.INSTANCE.hudEditor.getHudMod(mod.getName()).getY() } :
                            new int[] { 0, 0 },
                    Veyzen.INSTANCE.hudEditor.getHudMod(mod.getName()) != null ?
                            Veyzen.INSTANCE.hudEditor.getHudMod(mod.getName()).getSize() : 1
            );
            config.addConfig(modConfig);
        }

        for(Option option : Veyzen.INSTANCE.optionManager.getOptions()){
            config.addConfigOption(option);
        }

        config.setDarkMode(Style.isDarkMode());
        config.setSnapping(Style.isSnapping());

        String json = new Gson().toJson(config);
        writer.write(json);
        writer.close();
    }

    /**
     * Creates the .minecraft/veyzen directory if it cannot be found
     */

    private static void createDir() {
        File file = new File(OSHelper.getVeyzenDirectory());
        if (!file.exists()) {
            try {
                Files.createDirectory(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        createFile();
    }

    /**
     * Creates the .minecraft/veyzen/config.json file if it cannot be found
     */

    private static void createFile() {
        File file = new File(OSHelper.getVeyzenDirectory() + "config.json");
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checks if the config .minecraft/veyzen/config.json file exists
     *
     * @return Config can be found
     */

    public static boolean configExists() {
        File file = new File(OSHelper.getVeyzenDirectory() + "config.json");
        return file.exists();
    }
}
