package dumbcardsmod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import java.util.ArrayList;
import java.util.Iterator;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Equation extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Equation", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] DESCRIPTIONS = cardStrings.EXTENDED_DESCRIPTION;

    private static final int DAMAGEMIN = -10;
    private static final int DAMAGEMAX = 30;
    private int minDamage = DAMAGEMIN;
    private int maxDamage = DAMAGEMAX;
    private ArrayList<Integer> operations = new ArrayList<Integer>();
    private ArrayList<Integer> values = new ArrayList<Integer>();

    int equationOffset = 0;
    int initialMultiplier = 1;
    int finalMultiplier = 1;
    int initialExponent = 1;

    int realDamage;
    boolean healEnemy = false;

    public Equation() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(MathUtils.random(DAMAGEMIN, DAMAGEMAX));
        resetEquation();
        constructEquation();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!healEnemy){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        else {
            addToBot(new HealAction(m, p, (int)Math.abs(magicNumber)));
        }
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        constructEquation();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m){
        minDamage = calculateDamageMinMax(m, DAMAGEMIN);
        maxDamage = calculateDamageMinMax(m, DAMAGEMAX);
        super.calculateCardDamage(m);
        constructEquation();
    }

    public int calculateDamageMinMax(AbstractMonster m, int val){
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float)val;
        Iterator var9 = player.relics.iterator();

        while(var9.hasNext()) {
            AbstractRelic r = (AbstractRelic)var9.next();
            tmp = r.atDamageModify(tmp, this);
        }

        AbstractPower p;
        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);

        for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }

        for(var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }

        for(var9 = m.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower)var9.next();
        }

        return MathUtils.floor(tmp);
    }

    @Override
    public void triggerWhenDrawn() {
        resetEquation();
    }

    void resetEquation(){
        operations.clear();
        values.clear();
        realDamage = MathUtils.random(DAMAGEMIN, DAMAGEMAX);
        setMagic(realDamage);
        setDamage(realDamage);
        float chanceForNewStep = MathUtils.random(0.6f, 1f);
        float currentRoll = chanceForNewStep;
        while (currentRoll <= chanceForNewStep){
            //Addition, subtraction, multiplication, division, exponents, square root
            operations.add(0);
            values.add(MathUtils.random(-20, 20));
            chanceForNewStep -= 0.3f;
            currentRoll = MathUtils.random(0f, 1f);
        }
        currentRoll = MathUtils.random(0f, 1f);
        if (currentRoll > 0.95f){
            initialExponent = (int)MathUtils.random(2f, 3f);
        }
        if (currentRoll > 0.75f){
            initialMultiplier = (int)MathUtils.random(2f, 4f);
            if (MathUtils.random(0f, 1f) < 0.5f){
                initialMultiplier *= -1;
            }
            finalMultiplier = (int)MathUtils.random(2f, 6f);
        }
        else if (currentRoll > 0.6f) {
            initialMultiplier = (int) MathUtils.random(2f, 4f);
            if (MathUtils.random(0f, 1f) < 0.5f) {
                initialMultiplier *= -1;
            }
        }
        else {
            initialExponent = 1;
            initialMultiplier = 1;
            finalMultiplier = 1;
        }
        constructEquation();
    }

    public static String superscript(String str) {
        str = str.replaceAll("0", "⁰");
        str = str.replaceAll("1", "¹");
        str = str.replaceAll("2", "²");
        str = str.replaceAll("3", "³");
        str = str.replaceAll("4", "⁴");
        str = str.replaceAll("5", "⁵");
        str = str.replaceAll("6", "⁶");
        str = str.replaceAll("7", "⁷");
        str = str.replaceAll("8", "⁸");
        str = str.replaceAll("9", "⁹");
        return str;
    }

    void constructEquation(){
        String newDesc = DESCRIPTIONS[0] + minDamage + DESCRIPTIONS[1]+maxDamage+DESCRIPTIONS[2];
        int fakeDamage;
        System.out.println("Magic "+magicNumber);
        System.out.println("Damage "+damage);
        if (magicNumber < 0 && ((damage - (int)Math.abs(magicNumber)) < 0)){
            fakeDamage = magicNumber;
            healEnemy = true;
        }
        else {
            fakeDamage = damage;
            healEnemy = false;
        }
        if (initialExponent != 1){
            fakeDamage = (int)Math.pow(fakeDamage, initialExponent);
        }
        if (initialMultiplier != 1){
            if (initialMultiplier > 0){
                fakeDamage *= initialMultiplier;
            }
            else {
                if (fakeDamage % initialMultiplier != 0){
                    fakeDamage += (fakeDamage%initialMultiplier);
                    values.set(0, values.get(0)+fakeDamage%initialMultiplier);
                }
                fakeDamage /= initialMultiplier;
            }
        }
        for (int i = 0; i < operations.size(); i++){
            if (i == 0) {
                if (finalMultiplier != 1){
                    newDesc += "(";
                }
                if (initialMultiplier != 1){
                    if (initialMultiplier > 0){
                        newDesc += ""+initialMultiplier;
                    }
                }
                newDesc += "X";
                if (initialExponent != 1){
                    newDesc += superscript(""+initialExponent);
                }
            }
            if (values.get(i) < 0) {
                newDesc += (" - " + Math.abs(values.get(i)));
            } else {
                newDesc += (" + " + values.get(i));
            }
            fakeDamage += values.get(i);
        }
        if (finalMultiplier != 1){
            fakeDamage *= finalMultiplier;
            newDesc += ") X "+finalMultiplier;
        }

        newDesc += (" = "+fakeDamage);
        this.rawDescription = newDesc;
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Equation();
    }
}
