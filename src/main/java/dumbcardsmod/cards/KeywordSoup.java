package dumbcardsmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class KeywordSoup extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "KeywordSoup", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    public static final int MANTRA = 3;
    public static final int STRDEX = 1;
    public static final int DEBUFF = 1;

    public KeywordSoup() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setEthereal(true);
        setExhaust(true);
        this.selfRetain = true;
        setInnate(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int rand1 = AbstractDungeon.cardRng.random(0, 4);
        switch (rand1){
            case 0:
                addToBot(new MakeTempCardInHandAction(new Shiv()));
                break;
            case 1:
                addToBot(new MakeTempCardInHandAction(new Miracle()));
                break;
            case 2:
                addToBot(new MakeTempCardInHandAction(new Insight()));
                break;
            case 3:
                addToBot(new MakeTempCardInHandAction(new Smite()));
        }
        if (p.orbs.size() < 1){
            addToTop(new IncreaseMaxOrbAction(1));
        };
        addToBot(new ChannelAction(new Lightning()));
        addToBot(new EvokeOrbAction(1));
        addToBot(new ChangeStanceAction("Calm"));
        addToBot(new ApplyPowerAction(p, p, new MantraPower(p, MANTRA), MANTRA));
        if (upgraded){
            addToBot(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.CYAN, ShockWaveEffect.ShockWaveType.NORMAL)));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (!mo.isDying && !mo.isDead && !mo.halfDead){
                    this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, DEBUFF, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, DEBUFF, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRDEX)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, STRDEX)));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KeywordSoup();
    }
}
