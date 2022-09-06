package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.ExhaustRandomAction;
import dumbcardsmod.actions.ExhaustTopCardsAction;
import dumbcardsmod.actions.GainTurtleBlockAction;
import dumbcardsmod.actions.LimitAction;
import dumbcardsmod.interfaces.BannableCard;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.*;

public class PotOfGreed extends BannableCard {

    private final static CardInfo cardInfo = new CardInfo(
            "PotOfGreed", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    public PotOfGreed() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setBackgroundTexture(characterPath("SpellBack.png"),characterPath("SpellBack2.png"));
        banned = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded){
            addToBot(new LimitAction(this));
            addToBot(new ExhaustTopCardsAction(10));
        }
        addToBot(new DrawCardAction(2));
    }

    @Override
    public void upgrade(){
        super.upgrade();
        UnBan();
        limited = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[1];
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        loadCardImage(cardPath("/skill/PotOfDesires.png"));
        initializeTitle();
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PotOfGreed();
    }
}
