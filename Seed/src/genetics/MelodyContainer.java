package genetics;

import util.MusicalKey;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 01.02.2016.
 */
public class MelodyContainer {

    public static final byte MELODY_REST = 1;
    public static final byte MELODY_HOLD = 0;
    public static final int MELODY_RANGE_MAX = 85;
    public static final int MELODY_RANGE_MIN = 67;
    public static final int MELODY_RANGE = MELODY_RANGE_MAX - MELODY_RANGE_MIN;
    public static final int MELODY_BAR_SUBDIVISION = 4;
    public static final int MELODY_FOURTH_SUBDIVISION = 4;

    public final int bars;
    public final MusicalKey key;
    public byte[] melody;

    public MelodyContainer(int bars, MusicalKey key) {
        this.bars = bars;
        this.key = key;
    }

    /**
     * Instantiates a melody struct. Also sets the melody to a single pause.
     */
    public void init() {
        this.melody = new byte[MELODY_BAR_SUBDIVISION * MELODY_FOURTH_SUBDIVISION * bars];
        melody[0] = MELODY_HOLD;
    }

    public MelodyContainer getCopy() {
        MelodyContainer copy = new MelodyContainer(bars, key);
        copy.init();
        for (int i = 0; i < copy.melody.length; i++) {
            copy.melody[i] = this.melody[i];
        }
        return copy;
    }

    /**
     * This method returns the index of the pause or note that is being held from the current index.
     * @param index - starting index to check.
     * @return int - index of the note that is being played or held @param index. -1 if no note is obtained.
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
                    if (previousNote == previousRest && (index - previousRest < 4 || index % 4 != 0)) {
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
            if (melody[i] >= MelodyContainer.MELODY_RANGE_MIN && melody[i] <= MELODY_RANGE_MAX) {
                return true;
            }
        }
        return false;
    }

    public void randomize(Random rand) {
        for (int i = 0; i < melody.length; i++) {
            double value = rand.nextDouble();
            if (value < 0.125) {
                byte pitch = (byte) (rand.nextInt(MELODY_RANGE - 1) + MELODY_RANGE_MIN);
                melody[i] = pitch;
            }
            else if (value < 0.125) {
                melody[i] = MELODY_REST;
            }
        }
        if (melody[0] == MELODY_HOLD) {
            byte pitch = (byte) (rand.nextInt(MELODY_RANGE - 1) + MELODY_RANGE_MIN);
            melody[0] = pitch;
        }
        concatenateRests();
    }

    public boolean containsInvalidPitches() {
        for (int i = 0; i  < melody.length; i++) {
            if(melody[i] == MELODY_HOLD || melody[i] == MELODY_REST) {
                continue;
            }
            if (melody[i] < MELODY_RANGE_MIN || melody[i] > MELODY_RANGE_MAX) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MelodyContainer ms = new MelodyContainer(4, new MusicalKey(0, MusicalKey.Mode.MINOR));
        ms.init();
        System.out.println(ms.melody.length);
    }

    @Override
    public String toString() {
        return Arrays.toString(melody);
    }
}
