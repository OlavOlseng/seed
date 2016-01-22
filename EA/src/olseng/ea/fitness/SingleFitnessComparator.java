package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

import java.util.Comparator;

/**
 * Created by Olav on 22.01.2016.
 */
public class SingleFitnessComparator implements Comparator<Phenotype>{

    @Override
    public int compare(Phenotype o1, Phenotype o2) {
        float val = o1.getFitnessValue(0) - o2.getFitnessValue(0);
        if(val < 0) {
            return -1;
        }
        else if (val == 0) {
            return 0;
        }
        return 1;
    }
}
