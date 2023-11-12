package dumbcardsmod.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import dumbcardsmod.powers.CheaterPower;
import dumbcardsmod.powers.PlayTheUnplayablePower;

import java.util.Iterator;

public class PlayUnplayablePatch {

    @SpirePatch(clz = NewQueueCardAction.class, method = "update")

    public static class PlayUnplayable {
        public static AbstractCard instanceCard;

        public static void Prefix(NewQueueCardAction __instance) {
            instanceCard = ReflectionHacks.getPrivate(__instance, NewQueueCardAction.class, "card");

            if (AbstractDungeon.player.hasPower(PlayTheUnplayablePower.POWER_ID)) {
                if (instanceCard != null) {
                    System.out.println(instanceCard.cost + "|"+instanceCard.cardID);
                    if (instanceCard.cost == -2){
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, AbstractDungeon.player.getPower(PlayTheUnplayablePower.POWER_ID).amount)));
                    }
                }
            }
        }
    }
}