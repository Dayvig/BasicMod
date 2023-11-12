package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class ThisIsFine extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "ThisIsFine", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private int handBurns;

    public ThisIsFine() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        setBlock(0);
        this.cardsToPreview = new Burn();
    }

    @Override
    public void applyPowers(){
        int fineCtr = 0;
        handBurns = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Burn.ID))
                fineCtr++;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Burn.ID)) {
                fineCtr++;
                handBurns++;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Burn.ID))
                fineCtr++;
        }
        setBlock(fineCtr * magicNumber);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (handBurns > 0){
            addToBot(new DrawCardAction(handBurns));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ThisIsFine();
    }
}
