/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package io.github.arctyll.veyzen.gui.hudeditor.impl.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.github.arctyll.veyzen.Veyzen;
import io.github.arctyll.veyzen.gui.hudeditor.HudEditor;
import io.github.arctyll.veyzen.gui.hudeditor.impl.HudMod;
import io.github.arctyll.veyzen.helpers.render.GLHelper;
import io.github.arctyll.veyzen.helpers.render.Helper2D;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class ScoreboardHud extends HudMod {

    public ScoreboardHud(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public void renderMod(int mouseX, int mouseY) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled()) {
            drawScoreboardPlaceHolder(isBackground(), isRemoveNumbers());
            super.renderMod(mouseX, mouseY);
        }
        GLHelper.endScale();
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Pre.Text e) {
        GLHelper.startScale(getX(), getY(), getSize());
        if (Veyzen.INSTANCE.modManager.getMod(getName()).isToggled() && !(Veyzen.INSTANCE.mc.currentScreen instanceof HudEditor)) {
            ScoreObjective scoreobjective = Veyzen.INSTANCE.mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreobjective != null) {
                drawScoreboard(scoreobjective, isBackground(), isRemoveNumbers());
            }
        }
        GLHelper.endScale();
    }

    private void drawScoreboard(ScoreObjective objective, boolean background, boolean numbers) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.getSortedScores(objective);
        List<Score> list = new ArrayList<Score>();
		for (Score score : collection) {
			String playerName = score.getPlayerName();
			if (playerName != null && !playerName.startsWith("#")) {
				list.add(score);
			}
		}

        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        } else {
            collection = list;
        }

        int displayText = Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(objective.getDisplayName()) + 2;

        for (Score score : collection) {
            ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            String text = ScorePlayerTeam.formatPlayerName(scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            displayText = Math.max(displayText, Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(text));
        }

        int y = getY();
        int x = getX();

        int textHeight = Veyzen.INSTANCE.mc.fontRendererObj.FONT_HEIGHT;

        int index = collection.size() - 1;
        for (Score score1 : collection) {
            ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(score1.getPlayerName());
            String mainText = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, score1.getPlayerName());
            String redNumbers = EnumChatFormatting.RED + "" + score1.getScorePoints();
            int calculatedY = y + index * textHeight;

            if (index == 0) {
                String topText = objective.getDisplayName();
                if (background) {
                    Helper2D.drawRectangle(x, calculatedY, displayText + 4, textHeight, 1610612736);
                    Helper2D.drawRectangle(x, calculatedY + textHeight, displayText + 4, 1, 1342177280);
                }
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(topText, x + 2 + displayText / 2 - Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(topText) / 2, calculatedY + 1, -1);
            }

            if (background) {
                Helper2D.drawRectangle(x, calculatedY + textHeight + 1, displayText + 4, textHeight, 1342177280);
            }
            Veyzen.INSTANCE.mc.fontRendererObj.drawString(mainText, x + 2, calculatedY + textHeight + 1, -1);
            if (!numbers) {
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(redNumbers, x + 4 + displayText - Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(redNumbers), calculatedY + textHeight + 1, -1);
            }

            index--;
        }

        setW(displayText + 4);
        setH((collection.size() + 1) * textHeight);
    }

    private void drawScoreboardPlaceHolder(boolean background, boolean numbers) {
        String objective = "Scoreboard";
        int displayText = Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(objective) + 2;

        String[] names = {"Steve", "Alex", "Zuri", "Sunny", "Noor", "Makena", "Kai", "Efe", "Ari"};
        int collectionSize = names.length;

        for (int i = 0; i < collectionSize; i++) {
            String text = names[i] + ": " + EnumChatFormatting.RED + i;
            displayText = Math.max(displayText, Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(text));
        }

        int y = getY();
        int x = getX();

        int textHeight = Veyzen.INSTANCE.mc.fontRendererObj.FONT_HEIGHT;

        int index = collectionSize - 1;
        for (int i = 0; i < collectionSize; i++) {
            String mainText = names[i];
            String redNumbers = EnumChatFormatting.RED + "" + i;
            int calculatedY = y + index * textHeight;

            if (index == 0) {
                if (background) {
                    Helper2D.drawRectangle(x, calculatedY, displayText + 4, textHeight, 1610612736);
                    Helper2D.drawRectangle(x, calculatedY + textHeight, displayText + 4, 1, 1342177280);
                }
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(objective, x + 2 + displayText / 2 - Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(objective) / 2, calculatedY + 1, -1);
            }

            if (background) {
                Helper2D.drawRectangle(x, calculatedY + textHeight + 1, displayText + 4, textHeight, 1342177280);
            }
            Veyzen.INSTANCE.mc.fontRendererObj.drawString(mainText, x + 2, calculatedY + textHeight + 1, -1);
            if (!numbers) {
                Veyzen.INSTANCE.mc.fontRendererObj.drawString(redNumbers, x + 4 + displayText - Veyzen.INSTANCE.mc.fontRendererObj.getStringWidth(redNumbers), calculatedY + textHeight + 1, -1);
            }

            index--;
        }

        setW(displayText + 4);
        setH((collectionSize + 1) * textHeight);
    }

    private boolean isBackground() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Background").isCheckToggled();
    }

    private boolean isRemoveNumbers() {
        return Veyzen.INSTANCE.settingManager.getSettingByModAndName(getName(), "Remove Red Numbers").isCheckToggled();
    }
}
