package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.unique.CalculatedGambleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Mulligan extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Mulligan", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    public Mulligan() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setInnate(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean allUnplayable = true;
        for (AbstractCard c: AbstractDungeon.player.hand.group){
            if (!c.cardID.equals(this.cardID)){
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (c.canUse(p, mo)){
                        allUnplayable = false;
                    }
                }
            }
        }
        if (allUnplayable){
            addToBot(new CalculatedGambleAction(true));
        }
    }

    @Override
    public void upgrade(){
        super.upgrade();
        setExhaust(false);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Mulligan();
    }
}
