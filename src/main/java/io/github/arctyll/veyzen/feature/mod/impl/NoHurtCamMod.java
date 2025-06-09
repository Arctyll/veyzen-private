/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;

public class NoHurtCamMod extends Mod {

    public NoHurtCamMod() {
        super(
                "NoHurtCam",
                "Removes the camera shake effect when you take damage.",
                Type.Visual
        );
    }
}
