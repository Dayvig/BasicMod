package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.Hyperbeam;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;


public class LevelUpAction extends AbstractGameAction {

    AbstractCard toReplace;
    public LevelUpAction(AbstractCard toLevel){
        toReplace = toLevel;
    }

    AbstractCard located;
    AbstractCard mastertoRemove;
    CardGroup groupCheck;

    private void followUp(CardGroup g){
        AbstractDungeon.player.masterDeck.removeCard(mastertoRemove);
        AbstractDungeon.player.masterDeck.addToTop(new Hyperbeam());

        if (g.equals(AbstractDungeon.player.hand)) {
            AbstractDungeon.player.hand.removeCard(toReplace);
            addToBot(new MakeTempCardInHandAction(new Hyperbeam()));
        }
        else if (g.equals(AbstractDungeon.player.drawPile)) {
            AbstractDungeon.player.drawPile.removeCard(toReplace);
            addToBot(new MakeTempCardInDrawPileAction(new Hyperbeam(), 1, true, true, false));
        }
        else {
            AbstractDungeon.player.discardPile.removeCard(toReplace);
            addToBot(new MakeTempCardInDiscardAction(new Hyperbeam(), 1));
        }
    }

    private void followUp(){
        AbstractDungeon.player.masterDeck.removeCard(mastertoRemove);
        AbstractDungeon.player.masterDeck.addToTop(new Hyperbeam());
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if (c.cardID.equals(toReplace.cardID)){
                mastertoRemove = c;
                break;
            }
        }
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.equals(toReplace)) {
                    groupCheck = AbstractDungeon.player.hand;
                    break;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.equals(toReplace)) {
                    groupCheck = AbstractDungeon.player.hand;
                    break;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.equals(toReplace)) {
                    groupCheck = AbstractDungeon.player.discardPile;
                }
            }
        }

        if (groupCheck == null){
            followUp();
        }
        else {
            followUp(groupCheck);
        }
        this.isDone = true;
    }
}
