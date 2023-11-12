package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import jdk.tools.jlink.internal.plugins.StripNativeCommandsPlugin;

import static dumbcardsmod.DumbCardsMod.makeID;

public class PlayTheUnplayablePower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("PlayTheUnplayablePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PlayTheUnplayablePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        System.out.println(card.cost);
        //todo: Make it affect cards played with chaos potion, etc.
        if (card.cost == -2){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount)));
        }
    }

    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PlayTheUnplayablePower(owner, amount);
    }
}
