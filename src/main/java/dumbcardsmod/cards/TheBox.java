package dumbcardsmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.JackOfAllTrades;
import com.megacrit.cardcrawl.cards.green.Adrenaline;
import com.megacrit.cardcrawl.cards.red.Impervious;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.SpiritPoop;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.actions.AddCardToDeckAction2;
import dumbcardsmod.actions.RemoveCardAction;
import dumbcardsmod.interfaces.CardAddedToDeck;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class TheBox extends BaseCard implements CardAddedToDeck {

    private final static CardInfo cardInfo = new CardInfo(
            "TheBox", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    public int contents;

    public TheBox() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        tags.add(CardTags.STARTER_DEFEND);
        this.purgeOnUse = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        contents = AbstractDungeon.cardRng.random(0, 6);
        Open(contents, true);
        addToBot(new RemoveCardAction(this));
    }

    public boolean onAddedToMasterDeck(){
        contents = AbstractDungeon.cardRng.random(0, 6);
        Open(contents, false);
        addToBot(new RemoveCardAction(this));
        return false;
    }

    private void Open(int contents, boolean toHand){
        AbstractCard card;
        switch (contents){
            case 0:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GRAY));
                addToBot(new AddCardToDeckAction2(new Strike(), 2f, ((float)Settings.WIDTH / 3.0F) * 2, (float)Settings.HEIGHT / 2.0F));
                addToBot(new AddCardToDeckAction2(new Defend(), 2f, ((float)Settings.WIDTH / 3.0F), (float)Settings.HEIGHT / 2.0F));
                if (toHand){
                    addToBot(new MakeTempCardInHandAction(new Strike()));
                    addToBot(new MakeTempCardInHandAction(new Defend()));
                }
                break;
            case 1:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GOLD));
                addToBot(new GainGoldAction(100));
                addToBot(new VFXAction(new RainingGoldEffect(100, true)));
                break;
            case 2:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GREEN));
                card = new Adrenaline();
                card.upgrade();
                addToBot(new AddCardToDeckAction2(card.makeStatEquivalentCopy(), 2f, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                if (toHand){
                    addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
                }
                break;
            case 3:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.BLUE));
                card = new Impervious();
                card.upgrade();
                addToBot(new AddCardToDeckAction2(card.makeStatEquivalentCopy(), 2f, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                if (toHand){
                    addToBot(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
                }
                break;
            case 4:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.BROWN));
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new SpiritPoop());
                break;
            case 5:
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.WHITE));
                addToBot(new AddCardToDeckAction2(new JackOfAllTrades(), 2f, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                if (toHand){
                    addToBot(new MakeTempCardInHandAction(new JackOfAllTrades()));
                }
                break;
            default:
                System.out.println("Default");
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.GOLD));
                addToBot(new GainGoldAction(100));
                addToBot(new VFXAction(new RainingGoldEffect(100, true)));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        TheBox newBox = new TheBox();
        newBox.contents = contents;
        return newBox;
    }
}
