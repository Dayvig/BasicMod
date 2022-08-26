package dumbcardsmod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class SlowBlockEffect extends FlashAtkImgEffect {

    private float thisY;
    private float thisTY;
    private float thisSY;
    private float thisX;

    public SlowBlockEffect(float x, float y, AbstractGameAction.AttackEffect effect, boolean mute) {
        super(x, y, effect, mute);
        this.startingDuration *= 10f;
        System.out.println("TIME:"+startingDuration);
        this.duration = startingDuration;
        this.thisY = y - (float)this.img.packedHeight / 2.0F;
        this.thisX = x - (float)this.img.packedWidth / 2.0F;
        this.thisY = thisY + 80.0F * Settings.scale;
        this.thisTY = thisY;
        this.thisSY = thisY;

    }

    @Override
    public void update() {
                this.duration -= Gdx.graphics.getDeltaTime();
                if (this.duration < 0.0F) {
                    this.isDone = true;
                    this.color.a = 0.0F;
                } else if (this.duration < startingDuration/3) {
                    this.color.a = this.duration * 50.0F;
                } else {
                    this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration * 0.05F / startingDuration);
                }

                this.thisY = Interpolation.exp10In.apply(this.thisTY, this.thisSY, this.duration / startingDuration);
        }

        @Override
    public void render(SpriteBatch sb) {
        if (this.img != null) {
            sb.setColor(this.color);
            sb.draw(this.img, this.thisX, this.thisY, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
        }

    }

}

