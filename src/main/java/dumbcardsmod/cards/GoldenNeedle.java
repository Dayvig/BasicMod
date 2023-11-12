package dumbcardsmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.NeedleAction;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class GoldenNeedle extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "GoldenNeedle", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR  //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 1;
    private static final int MAGIC = 100;
    private static final int UPG_MAGIC = 50;

    public GoldenNeedle() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NeedleAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GoldenNeedle();
    }
}
