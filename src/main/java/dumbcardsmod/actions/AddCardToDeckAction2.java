//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class AddCardToDeckAction2 extends AbstractGameAction {
    AbstractCard cardToObtain;
    float x;
    float y;

    public AddCardToDeckAction2(AbstractCard card, float duration, float xpos, float ypos) {
        this.cardToObtain = card;
        this.duration = duration;
        if (!Settings.FAST_MODE){
            this.duration *= 2;
        }
        this.x = xpos;
        this.y = ypos;
    }

    public void update() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.cardToObtain, x, y));
        this.isDone = true;
    }
}
