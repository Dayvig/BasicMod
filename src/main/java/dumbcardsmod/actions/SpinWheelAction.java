package dumbcardsmod.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.shrines.GremlinWheelGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import dumbcardsmod.effects.WheelEffect;

import java.util.Objects;

public class SpinWheelAction extends AbstractGameAction {

    private int result;
    private float resultAngle;
    private float tmpAngle;
    private boolean startSpin;
    private boolean finishSpin;
    private boolean doneSpinning;
    private boolean bounceIn;
    private float bounceTimer;
    private float animTimer;
    private float spinVelocity;
    private int goldAmount;
    private boolean purgeResult;
    private boolean buttonPressed;
    private static float START_Y;
    private static float TARGET_Y;
    private float imgX;
    private float imgY;
    private float wheelAngle;
    private static final int WHEEL_W = 1024;
    private static final int ARROW_W = 512;
    private static float ARROW_OFFSET_X;
    private Color color;
    private float hpLossPercent;
    private static final float A_2_HP_LOSS = 0.15F;
    private Texture wheelImg;
    private Texture arrowImg;
    private Texture buttonImg;
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private float wheelWait = 0.0f;
    private boolean wheelActionTaken = false;
    private boolean hasPurged = false;

    public SpinWheelAction(int spinResult){
        this.startSpin = false;
        this.finishSpin = false;
        this.doneSpinning = false;
        this.bounceIn = true;
        this.bounceTimer = 1.0F;
        this.animTimer = 3.0F;
        this.spinVelocity = 200.0F;
        this.purgeResult = false;
        this.buttonPressed = false;
        this.imgX = (float)Settings.WIDTH / 2.0F;
        this.imgY = START_Y;
        this.wheelAngle = 0.0F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hpLossPercent = 0.1F;
        this.wheelImg = ImageMaster.loadImage("images/events/wheel.png");
        this.arrowImg = ImageMaster.loadImage("images/events/wheelArrow.png");
        this.buttonImg = ImageMaster.loadImage("images/events/spinButton.png");
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLossPercent = 0.15F;
        }
        setGold();
        wheelWait = 0.0f;
        this.startDuration = this.duration = 0.0f;
        wheelActionTaken = false;
        result = spinResult;
        hasPurged = false;
    }

    private void setGold() {
        if (Objects.equals(AbstractDungeon.id, "Exordium")) {
            this.goldAmount = 100;
        } else if (Objects.equals(AbstractDungeon.id, "TheCity")) {
            this.goldAmount = 200;
        } else if (Objects.equals(AbstractDungeon.id, "TheBeyond")) {
            this.goldAmount = 300;
        }

    }

    private void purgeLogic() {
        if (this.purgeResult && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.purgeResult = false;
            for (AbstractCard check : AbstractDungeon.player.hand.group){
                if (c.cardID.equals(check.cardID)){
                    AbstractDungeon.player.hand.group.remove(check);
                    AbstractDungeon.effectsQueue.add(new PurgeCardEffect(check));
                    return;
                }
            }
            for (AbstractCard check : AbstractDungeon.player.drawPile.group){
                if (c.cardID.equals(check.cardID)){
                    AbstractDungeon.player.drawPile.group.remove(check);
                    AbstractDungeon.effectsQueue.add(new PurgeCardEffect(check));
                    return;
                }
            }
            for (AbstractCard check : AbstractDungeon.player.discardPile.group){
                if (c.cardID.equals(check.cardID)){
                    AbstractDungeon.player.discardPile.group.remove(check);
                    AbstractDungeon.effectsQueue.add(new PurgeCardEffect(check));
                    return;
                }
            }
        }

    }


    public void update(){
        this.purgeLogic();
        wheelWait += Gdx.graphics.getDeltaTime();
        if (wheelWait > 3.0f){
            this.preApplyResult();
            this.isDone = true;
        }
    }

    private void preApplyResult() {
        switch(this.result) {
            case 0:
                addToBot(new VFXAction(new RainingGoldEffect(this.goldAmount, true)));
                AbstractDungeon.player.gainGold(this.goldAmount);
                addToBot(new TalkAction(true, DESCRIPTIONS[1], 1.0f, 4.0f));
                break;
            case 1:
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                addToBot(new TalkAction(true, DESCRIPTIONS[2], 1.0f, 4.0f));
                break;
            case 2:
                AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                addToBot(new TalkAction(true, DESCRIPTIONS[3], 1.0f, 4.0f));
                break;
            case 3:
                AbstractCard curse = new Decay();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                addToBot(new TalkAction(true, DESCRIPTIONS[4], 1.0f, 4.0f));
                break;
            case 4:
                if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                    AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, "Choose a card to remove.", false, false, false, true);
                    this.purgeResult = true;
                }
                addToBot(new TalkAction(true, DESCRIPTIONS[5], 1.0f, 4.0f));
                break;
            default:
                CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                CardCrawlGame.sound.play("BLOOD_SPLAT");
                int damageAmount = (int)((float)AbstractDungeon.player.maxHealth * this.hpLossPercent);
                AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, damageAmount, DamageInfo.DamageType.HP_LOSS));
                this.color = new Color(0.5F, 0.5F, 0.5F, 1.0F);
                addToBot(new TalkAction(true, DESCRIPTIONS[6], 1.0f, 4.0f));
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Wheel of Change");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

}
