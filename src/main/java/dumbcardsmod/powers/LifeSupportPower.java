package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static dumbcardsmod.DumbCardsMod.makeID;

public class LifeSupportPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("LifeSupportPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public LifeSupportPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > owner.currentHealth){
            return owner.currentHealth-1;
        }
        else {
            return damageAmount;
        }
    }

    @Override
    public void onDeath(){
        AbstractDungeon.player.heal(1, true);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    }

    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LifeSupportPower(owner, amount);
    }
}
