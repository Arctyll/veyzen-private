/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen;

import io.github.arctyll.veyzen.config.ConfigLoader;
import io.github.arctyll.veyzen.config.ConfigSaver;
import io.github.arctyll.veyzen.feature.mod.ModManager;
import io.github.arctyll.veyzen.feature.option.OptionManager;
import io.github.arctyll.veyzen.feature.setting.SettingManager;
import io.github.arctyll.veyzen.gui.hudeditor.HudEditor;
import io.github.arctyll.veyzen.helpers.CpsHelper;
import io.github.arctyll.veyzen.helpers.MessageHelper;
import io.github.arctyll.veyzen.helpers.font.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;

import java.io.FileNotFoundException;
import java.io.IOException;
import net.minecraftforge.fml.client.*;

@Mod(
        modid = Veyzen.modID,
        name = Veyzen.modName,
        version = Veyzen.modVersion,
        acceptedMinecraftVersions = "[1.8.9]"
)
public class Veyzen {

    @Mod.Instance()
    public static Veyzen INSTANCE;

    public static final String modID = "veyzen";
    public static final String modName = "Veyzen";
    public static final String modVersion = "1.0 [1.8.9]";

    public Minecraft mc = Minecraft.getMinecraft();

    public ModManager modManager;
    public SettingManager settingManager;
    public HudEditor hudEditor;
    public OptionManager optionManager;
    public FontHelper fontHelper;
    public CpsHelper cpsHelper;
    public MessageHelper messageHelper;

    /**
     * Initializes the client
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle(Veyzen.modName + " Client " + Veyzen.modVersion);
        registerEvents(
                cpsHelper = new CpsHelper(),
                settingManager = new SettingManager(),
                modManager = new ModManager(),
                optionManager = new OptionManager(),
                hudEditor = new HudEditor(),
                fontHelper = new FontHelper(),
                messageHelper = new MessageHelper()
        );

        try {
            if (!ConfigSaver.configExists()) {
                ConfigSaver.saveConfig();
            }
            ConfigLoader.loadConfig();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fontHelper.init();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
			public void run() {
				try {
					ConfigSaver.saveConfig();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
        }));
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }
}
