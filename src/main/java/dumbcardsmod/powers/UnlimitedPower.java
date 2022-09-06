package dumbcardsmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import dumbcardsmod.interfaces.BannableCard;

import static dumbcardsmod.DumbCardsMod.makeID;

public class UnlimitedPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID("UnlimitedPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public UnlimitedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        UnBanAll();
    }

    @Override
    public void onInitialApplication(){
        UnBanAll();
    }

    void UnBanAll(){
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c instanceof BannableCard){
                ((BannableCard) c).UnBan();
                ((BannableCard) c).UnLimit();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c instanceof BannableCard){
                ((BannableCard) c).UnBan();
                ((BannableCard) c).UnLimit();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c instanceof BannableCard){
                ((BannableCard) c).UnBan();
                ((BannableCard) c).UnLimit();
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new UnlimitedPower(owner, amount);
    }
}
