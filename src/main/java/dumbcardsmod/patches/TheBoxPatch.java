package dumbcardsmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import dumbcardsmod.interfaces.CardAddedToDeck;

public class TheBoxPatch {
    @SpirePatch(clz = SoulGroup.class, method = "obtain")
    public static class ShowCardAndObtainEffectPatch {
        public static SpireReturn Prefix(SoulGroup __instance, AbstractCard card, boolean obtainCard) {
            if (card instanceof CardAddedToDeck) {
                boolean skipAddingToDeck = ((CardAddedToDeck) card).onAddedToMasterDeck();
                if (skipAddingToDeck) {
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }
}