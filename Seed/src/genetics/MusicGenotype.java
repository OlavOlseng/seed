package genetics;

import olseng.ea.genetics.Genotype;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicGenotype extends Genotype<MusicalContainer> {

    public MusicGenotype() {
        super();
    }

    public MusicGenotype(MusicalContainer container) {
        this();
        setData(container);
    }

    @Override
    public MusicalContainer getDeepCopy() {
        return getData().getCopy();
    }

    @Override
    public void parseData(MusicalContainer data) {

    }
}
