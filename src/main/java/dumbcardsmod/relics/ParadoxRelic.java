package dumbcardsmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import dumbcardsmod.Characters.DumbCardsGuy;
import dumbcardsmod.DumbCardsMod;
import dumbcardsmod.cards.ParadoxStrike;
import dumbcardsmod.cards.Strike;
import dumbcardsmod.util.TextureLoader;

import static dumbcardsmod.DumbCardsMod.makeID;
import static dumbcardsmod.DumbCardsMod.relicPath;

public class ParadoxRelic extends CustomRelic {

    public static final String ID = DumbCardsMod.makeID("ParadoxRelic");
    public static final Texture image = TextureLoader.getTexture(relicPath("ParadoxRelic.png"));
    public static final Texture outline = TextureLoader.getTexture(relicPath("outlines/ParadoxRelic.png"));

    public ParadoxRelic() {
        super(ID, image, outline, RelicTier.SPECIAL, LandingSound.CLINK);
        counter = 0;
    }

    public ParadoxRelic(String id, String imageName) {
        super(id, imageName, RelicTier.SPECIAL, LandingSound.CLINK);
        counter = 0;
    }

    @Override
    public void atTurnStart(){
        this.flash();
        int rand = AbstractDungeon.cardRng.random(0, 20);
        System.out.println("Random roll: "+rand);
        if (rand == 10){
            playParadoxStrike();
        }
    }

    private void playParadoxStrike(){
        this.flash();
        addToBot(new VFXAction(new ShowCardBrieflyEffect(new ParadoxStrike())));
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.PURPLE)));
        AbstractCard toplay = new Strike();
        toplay.freeToPlayOnce = true;
        toplay.purgeOnUse = true;
        addToBot(new NewQueueCardAction(toplay, AbstractDungeon.getRandomMonster(), true));
        this.counter++;
    }

    @Override
    public String getUpdatedDescription() {
        if (counter >= 0){
            return DESCRIPTIONS[0] + counter;
        }
        return DESCRIPTIONS[1];
    }

    public void onUnequip(){
        ParadoxRelic newParadox = new ParadoxRelic();
        newParadox.counter = this.counter;
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), newParadox);
    }
}
