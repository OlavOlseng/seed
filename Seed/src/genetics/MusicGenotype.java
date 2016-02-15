package genetics;

import olseng.ea.genetics.Genotype;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicGenotype extends Genotype<MusicalStruct> {

    @Override
    public MusicalStruct getDeepCopy() {
        return getData().getCopy();
    }

    @Override
    public void parseData(MusicalStruct data) {

    }
}
