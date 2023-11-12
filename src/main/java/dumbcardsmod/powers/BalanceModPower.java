package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

import static dumbcardsmod.DumbCardsMod.makeID;

public class BalanceModPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("BalanceModPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BalanceModPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            Iterator var2 = AbstractDungeon.player.hand.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (!c.isEthereal) {
                    c.retain = true;
                }
            }
        }
    }


    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BalanceModPower(owner, amount);
    }
}
