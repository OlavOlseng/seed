import java.util.Arrays;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicalContainer {

    public final byte NOTE_REST = -1;
    public final byte NOTE_HOLD = -2;

    public byte[] melody;
    public byte[] chords;
    public final int bars;

    public MusicalContainer(int bars) {
        this.bars = bars;
    }

    public void init() {
        this.melody = new byte[4 * 4 * bars];
        this.chords = new byte[4 * bars];
    }

    public MusicalContainer getCopy() {
        MusicalContainer copy = new MusicalContainer(bars);
        copy.melody = Arrays.copyOf(melody, melody.length);
        copy.chords = Arrays.copyOf(chords, chords.length);
        return copy;
    }
}
