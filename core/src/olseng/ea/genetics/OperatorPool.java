package olseng.ea.genetics;

import java.util.ArrayList;

/**
 * Created by olavo on 2016-01-11.
 */
public class OperatorPool<G extends Genotype> {

    public ArrayList<GeneticMutator<G>> mutators;
    public ArrayList<GeneticCrossoverOperator<G>> crossovers;

    public OperatorPool() {
        this.mutators = new ArrayList<>();
        this.crossovers = new ArrayList<>();
    }

}
