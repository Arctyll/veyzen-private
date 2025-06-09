/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.feature.mod.impl;

import io.github.arctyll.veyzen.feature.mod.Mod;
import io.github.arctyll.veyzen.feature.mod.Type;

public class ScrollTooltipsMod extends Mod {

    public ScrollTooltipsMod() {
        super(
                "ScrollTooltips",
                "Makes long tooltips which go offscreen, scrollable.",
                Type.Tweaks
        );
    }
}
