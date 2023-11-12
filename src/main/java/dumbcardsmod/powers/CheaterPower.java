package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import dumbcardsmod.cards.Cheater;

import static dumbcardsmod.DumbCardsMod.makeID;

public class CheaterPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("CheaterPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public CheaterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!card.cardID.equals(Cheater.ID)) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CheaterPower(owner, amount);
    }
}
