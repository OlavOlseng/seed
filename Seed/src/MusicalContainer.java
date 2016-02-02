import java.util.Arrays;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicalContainer {

    public static final byte MELODY_REST = 1;
    public static final byte MELODY_HOLD = 0;
    public static final int MELODY_RANGE_MAX = 76;
    public static final int MELODY_RANGE_MIN = 55;
    public static final int MELODY_RANGE = MELODY_RANGE_MAX - MELODY_RANGE_MIN;

    public byte[] melody;
    public byte[][] chords;
    public final int bars;

    public MusicalContainer(int bars) {
        this.bars = bars;
    }

    /**
     * Instantiates a melody and chord struct. Also sets the melody to a single pause. Chords are empty.
     */
    public void init() {
        this.melody = new byte[4 * 4 * bars];
        this.chords = new byte[bars][4];
        this.melody[0] = MELODY_REST;
    }

    public MusicalContainer getCopy() {
        MusicalContainer copy = new MusicalContainer(bars);
        copy.melody = Arrays.copyOf(melody, melody.length);
        copy.chords = Arrays.copyOf(chords, chords.length);
        return copy;
    }

    public byte[] getChord(int bar) {
        return chords[bar];
    }

    public void setChord(int bar, byte[] chord) {
        this.chords[bar] = chord;
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

    public static void main(String[] args) {
        MusicalContainer ms = new MusicalContainer(4);
        ms.init();
        System.out.println(ms.melody.length);
        ms.setChord(4, new byte[]{0,4,7,10});
        System.out.println(Arrays.toString(ms.getChord(4)));
    }

}
