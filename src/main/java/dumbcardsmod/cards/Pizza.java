package dumbcardsmod.cards;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import java.util.ArrayList;
import java.util.Iterator;

import static dumbcardsmod.DumbCardsMod.makeID;

public class Pizza extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "Pizza", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    private static final int MAGIC = 10;
    private static final float TIMER = 30.0f;
    private float pizzaTime;
    private boolean unsilenced = false;

    public Pizza() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC);
        tags.add(CardTags.HEALING);
        setExhaust(true);
        pizzaTime = DumbCardsGuy.combatClock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, magicNumber));
        resetBGM();
    }

    private void resetBGM(){
        boolean laga = false;
        boolean hexa = false;
        if (!unsilenced){
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m.id.equals("Lagavulin")){
                    laga = true;
                    break;
                }
                else if (m.id.equals("Hexaghost")){
                    hexa = true;
                    break;
                }
            }
            CardCrawlGame.music.unsilenceBGM();
            TempMusic mu;
            ArrayList<TempMusic> temp = ReflectionHacks.getPrivate(CardCrawlGame.music, MusicMaster.class, "tempTrack");
            for(Iterator var1 = temp.iterator(); var1.hasNext(); mu.isFadingOut = true) {
                mu = (TempMusic)var1.next();
                if (mu.key == "Pizza"){
                    mu.kill();
                }
                mu.isSilenced = false;
                mu.updateVolume();
            }
            unsilenced = true;

            if (laga){
                System.out.println("Lagavulin");
                CardCrawlGame.music.playTempBgmInstantly("ELITE");
            }
            else if (hexa){
                CardCrawlGame.music.playTempBgmInstantly("BOSS_BOTTOM");
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        float currentTime = DumbCardsGuy.combatClock-pizzaTime;
        if (currentTime >= TIMER){
            this.cantUseMessage = "Too Slow!";
            return false;
        }
        return true;
    }

    @Override
    public void update(){
        super.update();
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            float currentTime = DumbCardsGuy.combatClock - pizzaTime;
            if (currentTime < TIMER) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + Math.ceil(TIMER - currentTime) + cardStrings.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            } else {
                resetBGM();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Pizza();
    }
}
