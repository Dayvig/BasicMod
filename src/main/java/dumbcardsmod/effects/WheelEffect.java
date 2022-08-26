package dumbcardsmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class WheelEffect extends AbstractGameEffect {

    private Texture wheelImg;
    private Texture arrowImg;
    private Texture buttonImg;
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

    public WheelEffect(float angle){
        this.startSpin = true;
        this.finishSpin = false;
        this.doneSpinning = false;
        this.bounceIn = true;
        this.bounceTimer = 0.0F;
        this.animTimer = 2.0F;
        this.spinVelocity = 1500.0F;
        this.purgeResult = false;
        this.buttonPressed = false;
        this.imgX = (float)Settings.WIDTH / 2.0F;
        this.imgY = (float)Settings.HEIGHT / 2.0F;
        this.wheelAngle = 0.0F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hpLossPercent = 0.1F;
        this.wheelImg = ImageMaster.loadImage("images/events/wheel.png");
        this.arrowImg = ImageMaster.loadImage("images/events/wheelArrow.png");
        this.buttonImg = ImageMaster.loadImage("images/events/spinButton.png");
        resultAngle = angle;
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.wheelImg, this.imgX - 512.0F, this.imgY - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, this.wheelAngle, 0, 0, 1024, 1024, false, false);
        sb.draw(this.arrowImg, this.imgX - 256.0F + ARROW_OFFSET_X + 180.0F * Settings.scale, this.imgY - 256.0F, 256.0F, 256.0F, 512.0F, 512.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
    }


    private void updatePosition() {
        if (this.bounceTimer != 0.0F) {
            this.bounceTimer -= Gdx.graphics.getDeltaTime();
            if (this.bounceTimer < 0.0F) {
                this.bounceTimer = 0.0F;
            }
            if (this.bounceIn && this.startSpin) {
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.bounceTimer);
                this.imgY = Interpolation.bounceIn.apply(TARGET_Y, START_Y, this.bounceTimer);
            } else if (this.doneSpinning) {
                this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.bounceTimer);
                this.imgY = Interpolation.swingOut.apply(START_Y, TARGET_Y, this.bounceTimer);
            }
        }

    }

    @Override
    public void update(){
        updatePosition();
            if (this.startSpin && this.bounceTimer == 0.0F) {
                this.imgY = TARGET_Y;
                if (this.animTimer > 0.0F) {
                    this.animTimer -= Gdx.graphics.getDeltaTime();
                    this.wheelAngle += this.spinVelocity * Gdx.graphics.getDeltaTime();
                } else {
                    this.finishSpin = true;
                    this.animTimer = 3.0F;
                    this.startSpin = false;
                    this.tmpAngle = this.resultAngle;
                }
            } else if (this.finishSpin) {
                if (this.animTimer > 0.0F) {
                    this.animTimer -= Gdx.graphics.getDeltaTime();
                    if (this.animTimer < 0.0F) {
                        this.animTimer = 1.0F;
                        this.finishSpin = false;
                        this.doneSpinning = true;
                    }

                    this.wheelAngle = Interpolation.elasticIn.apply(this.tmpAngle, -180.0F, this.animTimer / 3.0F);
                }
            } else if (this.doneSpinning) {
                if (this.animTimer > 0.0F) {
                    this.animTimer -= Gdx.graphics.getDeltaTime();
                    if (this.animTimer <= 0.0F) {
                        this.bounceTimer = 1.0F;
                        this.bounceIn = false;
                    }
                } else if (this.bounceTimer == 0.0F) {
                    this.doneSpinning = false;
                    dispose();
                }
            }
        }

    static {
        START_Y = (float)Settings.HEIGHT/2;
        TARGET_Y = (float)Settings.HEIGHT/2;
        ARROW_OFFSET_X = 300.0F * Settings.scale;
    }

    @Override
    public void dispose() {

    }
}
