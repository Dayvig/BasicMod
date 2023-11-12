package dumbcardsmod.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.util.CardInfo;

import static dumbcardsmod.DumbCardsMod.makeID;

public class RunAway extends BaseCard {

    private final static CardInfo cardInfo = new CardInfo(
            "RunAway", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with whatever your mod's ID is.
            4, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            DumbCardsGuy.Enums.DUMB_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    private static final int UPGRADED_COST = 3;

    public RunAway() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(UPGRADED_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)) {
            AbstractDungeon.getCurrRoom().smoked = true;
            this.addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
            AbstractDungeon.player.hideHealthBar();
            AbstractDungeon.player.isEscaping = true;
            AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
            AbstractDungeon.overlayMenu.endTurnButton.disable();
            AbstractDungeon.player.escapeTimer = 2.5F;
        }
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss){
            this.addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
            this.addToBot(new TalkAction(p, "Cannot run from a trainer battle!", 1.5f, 2.0f));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RunAway();
    }
}
