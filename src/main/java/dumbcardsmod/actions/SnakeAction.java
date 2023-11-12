package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class SnakeAction extends AbstractGameAction {

    private AbstractPlayer p;
        private boolean forCombat = false;

        public SnakeAction() {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.p = AbstractDungeon.player;
            this.duration = Settings.ACTION_DUR_FAST;
        }

        public void update() {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                Iterator var1 = this.p.hand.group.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    int mod = 3 - c.costForTurn;
                    c.modifyCostForCombat(mod);
                    c.flash();
                }
            }

            this.tickDuration();
        }
    }
