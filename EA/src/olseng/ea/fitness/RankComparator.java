package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

import java.util.Comparator;

/**
 * Created by Olav on 22.01.2016.
 */
public class RankComparator implements Comparator<Phenotype> {

    @Override
    public int compare(Phenotype o1, Phenotype o2) {
        return o1.getRank() - o2.getRank();
    }
}
