package dumbcardsmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.*;
import dumbcardsmod.effects.WheelEffect;
import dumbcardsmod.interfaces.BannableCard;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.characterPath;
import static dumbcardsmod.DumbCardsMod.makeID;

public class ChaosEmperorDragon extends BannableCard {

    private final static CardInfo cardInfo = new CardInfo(
            "ChaosEmperorDragon", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            3, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    public static final int DAMAGE = 13;
    public static final int MAGIC = 10;

    public ChaosEmperorDragon() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE);
        setMagic(MAGIC);
        setBackgroundTexture(characterPath("yataBack.png"),characterPath("yataBack2.png"));
        banned = true;
        exhaust = true;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (limited){
            addToBot(new LimitAction(this));
        }
        addToBot(new LoseHPAction(p, p, magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot(new EmperorDragonAction(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        return !banned;
    }

    @Override
    public void upgrade(){
        super.upgrade();
        limited = true;
        UnBan();
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChaosEmperorDragon();
    }
}
