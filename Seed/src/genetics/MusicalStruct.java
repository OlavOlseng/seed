package genetics;

/**
 * Created by Olav on 15.02.2016.
 */
public class MusicalStruct {

    public MelodyGenotype mg;
    public HarmonyGenotype hg;
    public final int bars;

    public MusicalStruct(int bars) {
        this.bars = bars;
        this.mg = new MelodyGenotype(bars);
        this.hg = new HarmonyGenotype(bars);
    }

    public MusicalStruct getCopy() {
        MusicalStruct ms = new MusicalStruct(bars);
        ms.mg = this.mg.getCopy();
        ms.hg = this.hg.getCopy();
        return ms;
    }

}
