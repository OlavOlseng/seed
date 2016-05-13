package genetics;

import org.jfugue.player.Player;
import util.MusicParser;
import util.MusicalKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class ChordContainer {

    public static int BASE_OCTAVE = 4;
    public static final byte NO_PITCH = -1;

    public byte[][] chords;
    public final int bars;
    public final MusicalKey key;

    public ChordContainer(int bars, MusicalKey key) {
        this.bars = bars;
        this.key = key;
    }

    public void init() {
        this.chords = new byte[bars][4];
    }

    public ChordContainer getCopy() {
        ChordContainer hg = new ChordContainer(this.bars, new MusicalKey(0, MusicalKey.Mode.MINOR));
        hg.init();
        for (int i = 0; i < bars; i++) {
            for (int j  = 0; j < chords[0].length; j++) {
                hg.chords[i][j] = chords[i][j];
            }
        }
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
            ArrayList<Byte> pitches = new ArrayList<>();
            for (byte i = 0; i < 12; i++) {
                pitches.add(i);
            }
            for (int i = 0; i < chord.length - 1; i++) {
                chord[i] = pitches.remove(rand.nextInt(pitches.size()));
            }
            if (rand.nextDouble() < 0.5) {
                chord[chord.length - 1] = (byte) rand.nextInt(12);
            }
            else {
                chord[chord.length - 1] = NO_PITCH;
            }
        }
    }

    /**
     * @param chordIndex
     * @return Returns the index of the next chord. NB: WRAPS!
     */
    public int getNextChord(int chordIndex) {
        return chordIndex + 1 >= chords.length ? 0 : chordIndex + 1;
    }

    /**
     * @param chordIndex
     * @return Returns the index of the previous chord. NB: WRAPS!
     */
    public int getPreviousChord(int chordIndex) {
        return chordIndex - 1 < 0 ? chords.length - 1 : chordIndex - 1;
    }

    public boolean chordHasPitch(int chordIndex, byte pitch) {
        for (byte pitchValue : chords[chordIndex]) {
            if (pitchValue == pitch) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "| ";
        for (int i = 0; i < chords.length; i++) {
            s += Arrays.toString(chords[i]) + " | ";
        }
        return s;
    }

    public static void main(String[] args) {
        ChordContainer hg = new ChordContainer(4, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        hg.randomize(new Random());
        MusicParser parser = new MusicParser();

        String chords = " Rw | " + parser.parseChords(hg);

        System.out.println(chords);

        Player player = new Player();
        player.play(chords);
    }
}
