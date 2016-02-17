package genetics;

import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalContainer {

    public MelodyGenotype melodyGenotype;
    public HarmonyGenotype harmonyGenotype;
    public final int bars;

    public MusicalContainer(int bars) {
        this.bars = bars;
        this.melodyGenotype = new MelodyGenotype(bars);
        this.harmonyGenotype = new HarmonyGenotype(bars);
    }

    public MusicalContainer getCopy() {
        MusicalContainer ms = new MusicalContainer(bars);
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
}
