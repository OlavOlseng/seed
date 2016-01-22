package olseng.ea.bitvectest;

import olseng.ea.genetics.Phenotype;

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
        return getRepresentation().toString() + " -> " + this.getFitnessValue(0);
    }
}
