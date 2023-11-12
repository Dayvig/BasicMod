//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dumbcardsmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class NeedleAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard theCard = null;
    private int gold;

    public NeedleAction(AbstractCreature target, DamageInfo info, int goldAmnt) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        gold = goldAmnt;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                addToBot(new GainGoldAction(gold));
                addToBot(new VFXAction(new RainingGoldEffect(this.gold, true)));
            }
        }
        this.tickDuration();
    }
}
