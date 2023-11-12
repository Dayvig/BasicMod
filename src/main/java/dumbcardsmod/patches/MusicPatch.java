package dumbcardsmod.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong")
public class MusicPatch {

    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        switch (key) {
            case "Pizza": {
                return SpireReturn.Return(MainMusic.newMusic("dumbcardsmod/audio/music/Pizza.ogg"));
            }
            default: {
                return SpireReturn.Continue();
            }
        }
    }
}