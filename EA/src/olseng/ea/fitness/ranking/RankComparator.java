package olseng.ea.fitness.ranking;

import olseng.ea.genetics.Phenotype;

import java.util.Comparator;

/**
 * Created by Olav on 22.01.2016.
 */
public class RankComparator implements Comparator<Phenotype> {

    @Override
    public int compare(Phenotype o1, Phenotype o2) {
        if (o1.getRank() == o2.getRank()) {
            float val  = o1.crowdingDistance - o2.crowdingDistance;
            if (val > 0) {
                return -1;
            }
            else if (val == 0) {
                return 0;
            }
            return 1;
        }
        return o1.getRank() - o2.getRank();
    }
}
