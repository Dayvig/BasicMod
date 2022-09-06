package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import dumbcardsmod.interfaces.BannableCard;

public class LimitAction extends AbstractGameAction {

    AbstractCard toTest;

    public LimitAction(AbstractCard onlyCard){
        toTest = onlyCard;
    }

    @Override
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group){
            if (card instanceof BannableCard && ((BannableCard) card).limited && !card.cardID.equals(toTest.cardID)){
                ((BannableCard) card).Ban();
            }
        }
        for (AbstractCard card : AbstractDungeon.player.hand.group){
            if (card instanceof BannableCard && ((BannableCard) card).limited && !card.cardID.equals(toTest.cardID)){
                ((BannableCard) card).Ban();
            }
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group){
            if (card instanceof BannableCard && ((BannableCard) card).limited && !card.cardID.equals(toTest.cardID)){
                ((BannableCard) card).Ban();
            }
        }
        this.isDone = true;
    }


}
