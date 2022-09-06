package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static dumbcardsmod.DumbCardsMod.makeID;

public class SpeakersPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("SpeakersPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    private float Vol;

    public SpeakersPower(AbstractCreature owner, int amount, float volume) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        Vol = volume;
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (Settings.MASTER_VOLUME < Vol){
            addToBot(new ReducePowerAction(this.owner, this.owner, StrengthPower.POWER_ID, this.amount));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PanikPower(owner, amount);
    }
}
