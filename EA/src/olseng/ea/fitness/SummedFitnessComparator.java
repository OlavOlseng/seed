package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

import java.util.Comparator;

/**
 * Created by Olav on 25.01.2016.
 */
public class SummedFitnessComparator implements Comparator<Phenotype> {

    @Override
    public int compare(Phenotype o1, Phenotype o2) {
        double val = 0;
        for (int i = 0; i < o1.getFitnessCount(); i++) {
            val += o1.getFitnessValue(i) - o2.getFitnessValue(i);
        }
        if(val < 0) {
            return 1;
        }
        else if (val == 0) {
            return 0;
        }
        return -1;
    }
}
