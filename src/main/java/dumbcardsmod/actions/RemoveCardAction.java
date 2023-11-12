package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import dumbcardsmod.interfaces.BannableCard;

public class RemoveCardAction extends AbstractGameAction {

    AbstractCard test;

    public RemoveCardAction(AbstractCard c){
        test = c;
    }

    @Override
    public void update() {
        AbstractCard toRemove = null;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group){
            if (card.equals(test)){
                toRemove = test;
                break;
            }
        }
        if (toRemove != null){
            AbstractDungeon.player.masterDeck.removeCard(toRemove);
        }
        this.isDone = true;
    }


}