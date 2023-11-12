package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class StingerDeathAction extends AbstractGameAction {

    AbstractCreature t;

    public StingerDeathAction(AbstractCreature target){
        t = target;
    }

    public void update(){
        if (!AbstractDungeon.getCurrRoom().isBattleEnding() && !AbstractDungeon.getCurrRoom().isBattleOver) {
            addToBot(new VFXAction(new FlashAtkImgEffect(t.hb.cX, t.hb.cY, AbstractGameAction.AttackEffect.POISON)));
            this.addToBot(new LoseHPAction(t, t, 99999));
        }
        this.isDone = true;
    }
}
