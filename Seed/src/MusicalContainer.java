import java.util.Arrays;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicalContainer {

    public static final byte NOTE_REST = 1;
    public static final byte NOTE_HOLD = 0;
    public static final int NOTE_MAX_RANGE = 76;
    public static final int NOTE_MIN_RANGE = 55;

    public byte[] melody;
    public byte[][] chords;
    public final int bars;

    public MusicalContainer(int bars) {
        this.bars = bars;
    }

    public void init() {
        this.melody = new byte[4 * 4 * bars];
        this.chords = new byte[bars][4];
        this.melody[0] = NOTE_REST;
    }

    public MusicalContainer getCopy() {
        MusicalContainer copy = new MusicalContainer(bars);
        copy.melody = Arrays.copyOf(melody, melody.length);
        copy.chords = Arrays.copyOf(chords, chords.length);
        return copy;
    }

    public byte[] getChord(int bar) {
        return chords[bar - 1];
    }

    public void setChord(int bar, byte[] chord) {
        this.chords[bar - 1] = chord;
    }

    public byte getNote(int bar, int eight) {
        return this.melody[(bar - 1) * 4 * 4 + eight - 1];
    }

    public void setNote(int bar, int eight, byte value) {
        this.melody[(bar - 1) * 4 * 4 + eight - 1] = value;
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

    public static void main(String[] args) {
        MusicalContainer ms = new MusicalContainer(4);
        ms.init();
        System.out.println(ms.melody.length);
        ms.setNote(4, 16, (byte) 250);
        ms.setNote(4, 5, (byte) -1);
        System.out.println(ms.getNote(4,8));
        System.out.println(ms.getNote(4,5));
        ms.setChord(4, new byte[]{0,4,7,10});
        System.out.println(Arrays.toString(ms.getChord(4)));
    }

}
