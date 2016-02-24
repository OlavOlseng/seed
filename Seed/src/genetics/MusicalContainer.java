package genetics;

import util.MusicalKey;

import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalContainer {

    public MelodyContainer melodyContainer;
    public ChordContainer chordContainer;
    public final int bars;
    public final MusicalKey key;

    public MusicalContainer(int bars, MusicalKey key) {
        this.bars = bars;
        this.key = key;
        this.melodyContainer = new MelodyContainer(bars, key);
        this.chordContainer = new ChordContainer(bars, key);
    }

    public MusicalContainer getCopy() {
        MusicalContainer ms = new MusicalContainer(bars, key);
        ms.melodyContainer = this.melodyContainer.getCopy();
        ms.chordContainer = this.chordContainer.getCopy();
        return ms;
    }

    public void randomize(Random rand) {
        this.melodyContainer.randomize(rand);
        this.chordContainer.randomize(rand);
    }

    public void init() {
        this.melodyContainer.init();
        this.chordContainer.init();
    }

    @Override
    public String toString() {
        return melodyContainer.toString() + "\n" + chordContainer.toString();
    }
}
