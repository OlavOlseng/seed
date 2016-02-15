package genetics;

import org.jfugue.player.Player;
import util.MusicParser;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class HarmonyGenotype {

    public final int BASE_OCTAVE = 4;

    public byte[][] chords;
    public final int bars;

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

    public void randomize(Random rand) {
        for (byte[] chord : chords) {
            for (int i = 0; i < chord.length - 1; i++) {
                chord[i] = (byte) rand.nextInt(12);
            }
            if (rand.nextDouble() < 0.5) {
                chord[chord.length - 1] = (byte) rand.nextInt(12);
            }
            else {
                chord[chord.length - 1] = -1;
            }
        }
    }

    public static void main(String[] args) {
        HarmonyGenotype hg = new HarmonyGenotype(4);
        hg.randomize(new Random());
        MusicParser parser = new MusicParser();

        String chords = " Rw | " + parser.parseChords(hg);

        System.out.println(chords);

        Player player = new Player();
        player.play(chords);
    }
}
