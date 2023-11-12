package dumbcardsmod.patches;


import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.colorless.SecretTechnique;
import com.megacrit.cardcrawl.cards.colorless.SecretWeapon;
import com.megacrit.cardcrawl.cards.green.GrandFinale;
import com.megacrit.cardcrawl.cards.green.Reflex;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.cards.purple.DeusExMachina;
import com.megacrit.cardcrawl.cards.purple.SignatureMove;
import com.megacrit.cardcrawl.cards.red.Clash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import dumbcardsmod.powers.CheaterPower;
import dumbcardsmod.powers.UnlimitedPower;

import java.security.Signature;
import java.util.Iterator;

public class CheaterPatch {
    @SpirePatch(clz = AbstractCard.class, method = "canUse", paramtypez = {AbstractPlayer.class, AbstractMonster.class})

    public static class Cheater1 {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                System.out.println("Test - has Power");
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "hasEnoughEnergy")

    public static class Cheater2 {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = GrandFinale.class, method = "canUse")

    public static class CheaterGF {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Clash.class, method = "canUse")

    public static class CheaterC {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SignatureMove.class, method = "canUse")

    public static class CheaterSM {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SecretTechnique.class, method = "canUse")

    public static class CheaterST {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SecretWeapon.class, method = "canUse")

    public static class CheaterSW {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Reflex.class, method = "canUse")

    public static class CheaterRF {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID) || AbstractDungeon.player.hasPower(UnlimitedPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = Tactician.class, method = "canUse")

    public static class CheaterTT {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID) || AbstractDungeon.player.hasPower(UnlimitedPower.POWER_ID)) {
                __instance.triggerOnManualDiscard();
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DeusExMachina.class, method = "canUse")

    public static class CheaterDX {
        public static SpireReturn Prefix(AbstractCard __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID) || AbstractDungeon.player.hasPower(UnlimitedPower.POWER_ID)) {
                return SpireReturn.Return(true);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = NewQueueCardAction.class, method = "update")

    public static class Cheater3 {
        public static void Prefix(NewQueueCardAction __instance) {
            if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)) {
                AbstractCard instanceCard = ReflectionHacks.getPrivate(NewQueueCardAction.class, AbstractCard.class, "card");
                boolean instanceRandomTarget = ReflectionHacks.getPrivate(NewQueueCardAction.class, boolean.class, "randomTarget");
                boolean instanceAutoplayCard = ReflectionHacks.getPrivate(NewQueueCardAction.class, boolean.class, "autoplayCard");
                boolean instanceImmediateCard = ReflectionHacks.getPrivate(NewQueueCardAction.class, boolean.class, "immediateCard");
                if (instanceCard == null) {
                    if (!instanceQueueContainsEndTurnCard()) {
                        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem());
                    }
                } else if (!instanceQueueContains(instanceCard)) {
                    if (instanceRandomTarget) {
                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(instanceCard, true, EnergyPanel.getCurrentEnergy(), true, instanceAutoplayCard), instanceImmediateCard);
                    } else {
                        if (!(__instance.target instanceof AbstractMonster) && __instance.target != null) {
                            __instance.isDone = true;
                            return;
                        }

                        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(instanceCard, (AbstractMonster)__instance.target, EnergyPanel.getCurrentEnergy(), true, instanceAutoplayCard), instanceImmediateCard);
                    }
                }

                __instance.isDone = true;
        }
    }

    private static boolean instanceQueueContainsEndTurnCard(){
        Iterator var1 = AbstractDungeon.actionManager.cardQueue.iterator();

        CardQueueItem i;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            i = (CardQueueItem)var1.next();
        } while(i.card != null);

        return true;
        }
        private static boolean instanceQueueContains(AbstractCard card) {
            Iterator var2 = AbstractDungeon.actionManager.cardQueue.iterator();

            CardQueueItem i;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                i = (CardQueueItem)var2.next();
            } while(i.card != card);

            return true;
        }
    }
}