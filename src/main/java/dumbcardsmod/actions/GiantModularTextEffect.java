//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dumbcardsmod.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.purple.Judgement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GiantModularTextEffect extends AbstractGameEffect {
    private StringBuilder sBuilder = new StringBuilder("");
    private String targetString;
    private int index;
    private float x;
    private float y;

    public GiantModularTextEffect(float x, float y, String text) {
        this.targetString = text;
        this.index = 0;
        this.sBuilder.setLength(0);
        this.x = x;
        this.y = y + 100.0F * Settings.scale;
        this.color = Color.WHITE.cpy();
        this.duration = 1.0F;
    }

    public void update() {
        this.color.a = Interpolation.pow5Out.apply(0.0F, 0.8F, this.duration);
        Color var10000 = this.color;
        var10000.a += MathUtils.random(-0.05F, 0.05F);
        this.color.a = MathUtils.clamp(this.color.a, 0.0F, 1.0F);
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.index < this.targetString.length()) {
            this.sBuilder.append(this.targetString.charAt(this.index));
            ++this.index;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, targetString, this.x, this.y, this.color, 2.5F - this.duration / 4.0F + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 1);
        FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, targetString, this.x, this.y, this.color, 0.05F + (2.5F - this.duration / 4.0F) + MathUtils.random(0.05F));
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
