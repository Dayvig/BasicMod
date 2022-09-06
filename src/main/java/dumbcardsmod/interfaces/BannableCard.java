package dumbcardsmod.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.cards.BaseCard;
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
        super.canUse(p, m);
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
