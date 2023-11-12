package dumbcardsmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.SpinWheelAction;
import dumbcardsmod.effects.WheelEffect;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class RareAttack extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "RareAttack", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            3, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int UPGRADED_COST = 2;
    private int result;
    private float resultAngle;

    public RareAttack() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(UPGRADED_COST);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.result = AbstractDungeon.cardRandomRng.random(0, 5);
        this.resultAngle = (float)this.result * 60.0F + MathUtils.random(-10.0F, 10.0F);
        System.out.println(result);
        addToBot(new SpinWheelAction(result));
        addToTop(new VFXAction(AbstractDungeon.player, new WheelEffect(resultAngle), 4.0f, true));
        CardCrawlGame.sound.play("WHEEL");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RareAttack();
    }
}
