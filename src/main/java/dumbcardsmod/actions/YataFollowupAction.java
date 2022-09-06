package dumbcardsmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YataFollowupAction extends AbstractGameAction {

    AbstractMonster target;

    public YataFollowupAction(AbstractMonster m){
        target = m;
    }

    @Override
    public void update() {
        if (target.lastDamageTaken > 0){
            addToBot(new StunMonsterAction(target, AbstractDungeon.player));
        }
        this.isDone = true;
    }



}
