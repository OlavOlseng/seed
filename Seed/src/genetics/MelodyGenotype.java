package genetics;

import util.MusicalKey;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 01.02.2016.
 */
public class MelodyGenotype {

    public static final byte MELODY_REST = 1;
    public static final byte MELODY_HOLD = 0;
    public static final int MELODY_RANGE_MAX = 80;
    public static final int MELODY_RANGE_MIN = 55;
    public static final int MELODY_RANGE = MELODY_RANGE_MAX - MELODY_RANGE_MIN;

    public final int bars;
    public final MusicalKey key;
    public byte[] melody;

    public MelodyGenotype(int bars, MusicalKey key) {
        this.bars = bars;
        this.key = key;
    }

    /**
     * Instantiates a melody struct. Also sets the melody to a single pause.
     */
    public void init() {
        this.melody = new byte[4 * 4 * bars];
        for (int i = 0; i < melody.length; i += 16) {
            this.melody[i] = MELODY_REST;
        }
    }

    public MelodyGenotype getCopy() {
        MelodyGenotype copy = new MelodyGenotype(bars, key);
        copy.melody = Arrays.copyOf(melody, melody.length);
        return copy;
    }

    /**
     * This method returns the index of the pause or note that is being held from the current index.
     * @param index - starting index to check.
     * @return int - index of the note that is being played or held @param index.
     */
    public int getNoteStartIndex(int index) {
        while (index > 0) {
            if (melody[index] > 0) {
                break;
            }
            index--;
        }
        return index;
    }

    /**
     * Returns the index of the next note or rest in relation to index.
     * @param index
     * @return The index of the next note in the melody. Returns -1 if there is no notes after provided index.
     */
    public int getNextNoteIndex(int index) {
        while (++index < melody.length) {
            if (melody[index] > 0) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the next note or rest in relation to index.
     * @param index
     * @return The index of the next note in the melody. Returns -1 if there is no notes after provided index.
     */
    public int getPreviousNoteIndex(int index) {
        index = getNoteStartIndex(index);
        while(--index >= 0) {
            if (melody[index] > 0) {
                return index;
            }
        }
        return -1;
    }

    /**
     * This function iterates through the melody and concatenates consecutive rests.
     */
    public void concatenateRests() {
        int previousRest = -1;
        int previousNote = -2;
        int index = 0;
        while (index < melody.length) {
            byte value = melody[index];
            if (value > 0) {
                if (value == 1) {
                    if (previousNote == previousRest) {
                        melody[index] = 0;
                    }
                    else {
                        previousNote = index;
                        previousRest = index;
                    }
                }
                else {
                    previousNote = index;
                }
            }
            index++;
        }
    }

    public boolean melodyContainsPitch() {
        for (int i = 0; i < melody.length; i++) {
            if (melody[i] >= MelodyGenotype.MELODY_RANGE_MIN) {
                return true;
            }
        }
        return false;
    }

    public void randomize(Random rand) {
        
    }

    public static void main(String[] args) {
        MelodyGenotype ms = new MelodyGenotype(4, new MusicalKey(0, MusicalKey.Mode.MINOR));
        ms.init();
        System.out.println(ms.melody.length);
    }

}
