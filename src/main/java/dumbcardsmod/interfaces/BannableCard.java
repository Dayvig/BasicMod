package dumbcardsmod.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.cards.BaseCard;
import dumbcardsmod.powers.CheaterPower;
import dumbcardsmod.util.CardInfo;

public class BannableCard extends BaseCard {

    public boolean banned;
    public boolean limited;

    public BannableCard(CardInfo cardInfo) {
        super(cardInfo);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        boolean canUse = super.canUse(p, m);
        if (AbstractDungeon.player.hasPower(CheaterPower.POWER_ID)){
            return true;
        }
        else if (!canUse){
            return false;
        }
        return !banned;
    }

    public void UnBan(){
        banned = false;
    }

    public void Ban(){
        banned = true;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void UnLimit(){
        limited = false;
    }
}
