package dumbcardsmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.GiantModularTextEffect;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Paralysis extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Paralysis", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    public Paralysis() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.type == AbstractMonster.EnemyType.BOSS){
            this.addToBot(new VFXAction(new GiantModularTextEffect(m.hb.cX, m.hb.cY, cardStrings.EXTENDED_DESCRIPTION[0])));
        }
        else if (m.type == AbstractMonster.EnemyType.ELITE){
            int rand = AbstractDungeon.cardRng.random(0, 3);
            if (rand == 0){
                addToBot(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
                addToBot(new StunMonsterAction(m, p, 1));
            }
            else {
                this.addToBot(new VFXAction(new GiantModularTextEffect(m.hb.cX, m.hb.cY, cardStrings.EXTENDED_DESCRIPTION[1])));
            }
        }
        else {
            addToBot(new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY)));
            addToBot(new StunMonsterAction(m, p, 1));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Paralysis();
    }
}
