package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Thonk extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Thonk", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;

    public Thonk() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC);
        setBlock(BLOCK, UPG_BLOCK);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ConfusionPower.POWER_ID)){
            addToBot(new GainBlockAction(p, block));
            addToBot(new RemoveSpecificPowerAction(p, p, ConfusionPower.POWER_ID));
        }
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Thonk();
    }
}
