/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen;

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
import io.github.arctyll.veyzen.config.*;

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
    public static final String modVersion = "1.0-beta [1.8.9]";

    public Minecraft mc = Minecraft.getMinecraft();

    public ModManager modManager;
    public SettingManager settingManager;
    public OptionManager optionManager;
	public HudEditor hudEditor;
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
                fontHelper = new FontHelper(),
                messageHelper = new MessageHelper(),
				hudEditor = new HudEditor()
        );
		try {
			ConfigLoader.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
        fontHelper.init();
    }

    private void registerEvents(Object... events) {
        for (Object event : events) {
            MinecraftForge.EVENT_BUS.register(event);
        }
    }
}
