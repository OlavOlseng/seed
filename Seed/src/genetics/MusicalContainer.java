package genetics;

import util.MusicalKey;

import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalContainer {

    public MelodyGenotype melodyGenotype;
    public HarmonyGenotype harmonyGenotype;
    public final int bars;
    public final MusicalKey key;

    public MusicalContainer(int bars, MusicalKey key) {
        this.bars = bars;
        this.key = key;
        this.melodyGenotype = new MelodyGenotype(bars, key);
        this.harmonyGenotype = new HarmonyGenotype(bars, key);
    }

    public MusicalContainer getCopy() {
        MusicalContainer ms = new MusicalContainer(bars, key);
        ms.melodyGenotype = this.melodyGenotype.getCopy();
        ms.harmonyGenotype = this.harmonyGenotype.getCopy();
        return ms;
    }

    public void randomize(Random rand) {
        this.melodyGenotype.randomize(rand);
        this.harmonyGenotype.randomize(rand);
    }

    public void init() {
        this.melodyGenotype.init();
        this.harmonyGenotype.init();
    }

    @Override
    public String toString() {
        return melodyGenotype.toString() + "\n" + harmonyGenotype.toString();
    }
}
