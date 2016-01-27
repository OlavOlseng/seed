package olseng.ea.bitvectest;

import olseng.ea.genetics.Phenotype;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Olav on 05.03.2015.
 */
public class IntVec extends Phenotype<List<Integer>, BinaryGenome>{

    public IntVec(BinaryGenome genotype) {
        super(genotype);
    }

    @Override
    public String toString() {
        return /**getRepresentation().toString() +**/ " -> " + Arrays.toString(this.fitnessValues) + ", Rank: " + getRank() + ", CD: " + crowdingDistance;
    }
}
