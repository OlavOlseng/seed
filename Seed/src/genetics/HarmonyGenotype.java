package genetics;

import java.util.Arrays;

/**
 * Created by Olav on 15.02.2016.
 */
public class HarmonyGenotype {

    public final int bars;

    public byte[][] chords;

    public HarmonyGenotype(int bars) {
        this.chords = new byte[bars][4];

        this.bars = bars;
    }

    public void init() {
        this.chords = new byte[bars][4];
    }

    public HarmonyGenotype getCopy() {
        HarmonyGenotype hg = new HarmonyGenotype(this.bars);
        hg.chords = Arrays.copyOf(chords, chords.length);
        return hg;
    }

    public byte[] getChord(int bar) {
        return chords[bar];
    }

    public void setChord(int bar, byte[] chord) {
        this.chords[bar] = chord;
    }

}
