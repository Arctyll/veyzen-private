/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;
import io.github.arctyll.veyzen.feature.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FreelookMod extends Mod {

    public static boolean cameraToggled = false;
    public static float cameraYaw;
    public static float cameraPitch;

    public FreelookMod() {
        super(
                "Freelook",
                "Allows you to see a 360 view around your Player.",
                Type.Mechanic
        );

        Veyzen.INSTANCE.settingManager.addSetting(new Setting("Keybinding", this, Keyboard.KEY_R));
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if(Keyboard.isKeyDown(getKey()) && !cameraToggled){
            cameraYaw = Veyzen.INSTANCE.mc.thePlayer.rotationYaw + 180;
            cameraPitch = Veyzen.INSTANCE.mc.thePlayer.rotationPitch;
            cameraToggled = true;
            Veyzen.INSTANCE.mc.gameSettings.thirdPersonView = 1;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e){
        if(!Keyboard.isKeyDown(getKey()) && cameraToggled) {
            cameraToggled = false;
            Veyzen.INSTANCE.mc.gameSettings.thirdPersonView = 0;
        }
    }

    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup e) {
        if (cameraToggled) {
            e.yaw = cameraYaw;
            e.pitch = cameraPitch;
        }
    }

    private int getKey(){
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Keybinding").getKey();
    }
}
