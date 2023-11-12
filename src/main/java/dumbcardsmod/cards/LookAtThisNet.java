package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class LookAtThisNet extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "LookAtThisNet", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;

    public LookAtThisNet() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC, UPGRADE_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new EntanglePower(p)));
        addToBot(new VFXAction(new EntangleEffect(this.hb.cX - 70.0F * Settings.scale, this.hb.cY + 10.0F * Settings.scale, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LookAtThisNet();
    }
}
