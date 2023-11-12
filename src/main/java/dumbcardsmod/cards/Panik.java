package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.PanicButton;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.powers.PanikPower;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Panik extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Panik", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Panik() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        this.cardsToPreview = new PanicButton();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PanikPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Panik();
    }
}
