import olseng.ea.genetics.Genotype;

/**
 * Created by Olav on 01.02.2016.
 */
public class MusicGenotype extends Genotype<MusicalContainer> {

    @Override
    public MusicalContainer getDeepCopy() {
        return getData().getCopy();
    }

    @Override
    public void parseData(MusicalContainer data) {

    }
}
