package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustTopCardsAction extends AbstractGameAction {

    int amnt;
    public ExhaustTopCardsAction(int i){
        amnt = i;
    }

    public ExhaustTopCardsAction(){
        amnt = 1;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() < amnt){
            System.out.println("Option 1: Drawpile and discardpile not large enough.");
            this.addToTop(new ExhaustTopCardsAction(AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size()));
            this.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        else if (AbstractDungeon.player.drawPile.isEmpty()) {
            System.out.println("Option 2: Drawpile empty.");
            this.addToTop(new ExhaustTopCardsAction(amnt));
            this.addToTop(new EmptyDeckShuffleAction());
            this.isDone = true;
            return;
        }
        else if (AbstractDungeon.player.drawPile.size() < amnt){
            System.out.println("Option 3: Drawpile not large enough");
            this.addToTop(new ExhaustTopCardsAction(amnt - AbstractDungeon.player.drawPile.size()));
            this.addToTop(new EmptyDeckShuffleAction());
            for (AbstractCard c : AbstractDungeon.player.drawPile.group){
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
            }
            this.isDone = true;
            return;
        }
        else {
            for (int i = 0; i < amnt; i++){
                System.out.println("Option 4: Enough cards");
                AbstractCard cardToGet = AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.group.size() - (1 + i));
                addToTop(new ExhaustSpecificCardAction(cardToGet, AbstractDungeon.player.drawPile, true));
            }
            this.isDone = true;
        }
        this.isDone = true;
    }
}
