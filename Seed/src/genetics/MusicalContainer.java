package genetics;

import java.util.Random;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalContainer {

    public MelodyGenotype mg;
    public HarmonyGenotype hg;
    public final int bars;

    public MusicalContainer(int bars) {
        this.bars = bars;
        this.mg = new MelodyGenotype(bars);
        this.hg = new HarmonyGenotype(bars);
    }

    public MusicalContainer getCopy() {
        MusicalContainer ms = new MusicalContainer(bars);
        ms.mg = this.mg.getCopy();
        ms.hg = this.hg.getCopy();
        return ms;
    }

    public void randomize(Random rand) {
        this.mg.randomize(rand);
        this.hg.randomize(rand);
    }

}
