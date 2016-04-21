package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

import java.util.Comparator;

/**
 * Created by Olav on 22.01.2016.
 */
public class AscendingSingleFitnessComparator implements Comparator<Phenotype>{

    private int fitnessIndex;

    public AscendingSingleFitnessComparator(int fitnessIndex) {
        this.fitnessIndex = fitnessIndex;
    }

    public void setFitnessIndex(int fitnessIndex) {
        this.fitnessIndex = fitnessIndex;
    }

    @Override
    public int compare(Phenotype o1, Phenotype o2) {
        float val = o1.getFitnessValue(fitnessIndex) - o2.getFitnessValue(fitnessIndex);
        if(val < 0) {
            return -1;
        }
        else if (val == 0) {
            return 0;
        }
        return 1;
    }
}
