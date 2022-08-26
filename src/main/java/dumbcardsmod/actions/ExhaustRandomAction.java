package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ExhaustRandomAction extends AbstractGameAction {

    private ArrayList toCheck = new ArrayList();
    private ArrayList drawGroup = new ArrayList();
    private ArrayList discardGroup = new ArrayList();
    private ArrayList handGroup = new ArrayList();

    public ExhaustRandomAction(int num){
        duration = Settings.FAST_MODE ? Settings.ACTION_DUR_FASTER : Settings.ACTION_DUR_FAST;
        amount = num;
    }
    public ExhaustRandomAction(){
        duration = Settings.FAST_MODE ? Settings.ACTION_DUR_FASTER : Settings.ACTION_DUR_FAST;
        amount = 1;
    }


    @Override
    public void update() {
        toCheck.clear();
        AbstractPlayer p = AbstractDungeon.player;
        int dpSize = 0;
        int handSize = 0;
        for (AbstractCard c : p.drawPile.group){
            toCheck.add(c);
            dpSize++;
        }
        for (AbstractCard c : p.hand.group){
            toCheck.add(c);
            handSize++;
        }
        for (AbstractCard c : p.discardPile.group){
            toCheck.add(c);
        }
        int Random = AbstractDungeon.cardRandomRng.random(0, toCheck.size()-1);
        AbstractCard toExhaust = (AbstractCard) toCheck.get(Random);
        if (Random > dpSize + handSize){
            addToBot(new ExhaustSpecificCardAction(toExhaust, p.discardPile));
        }
        else if (Random > dpSize){
            addToBot(new ExhaustSpecificCardAction(toExhaust, p.hand));
        }
        else {
            addToBot(new ExhaustSpecificCardAction(toExhaust, p.drawPile));
        }
        this.isDone = true;
    }
}
