package olseng.ea.genetics;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Created by olavo on 2016-01-11.
 */
public class OperatorPool<G extends Genotype> {

    private double crossoverProbability = 0.5;

    public ArrayList<GeneticMutationOperator<G>> mutators;
    public ArrayList<GeneticCrossoverOperator<G>> crossovers;

    public OperatorPool() {
        this.mutators = new ArrayList<>();
        this.crossovers = new ArrayList<>();
    }

    /**
     * @param probability - clamped double [0, 1.0]
     */
    public void setCrossoverProbability(double probability) {
        if (probability > 1.0) throw new InvalidParameterException("Crossover probability cannot exceed 1.0");
        this.crossoverProbability = probability;
    }
}
