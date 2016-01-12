package olseng.ea.genetics;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olavo on 2016-01-11.
 */
public class OperatorPool<G extends Genotype> {

    private double crossoverProbability = 0.5;

    private List<GeneticMutationOperator<G>> mutators;
    private List<GeneticCrossoverOperator<G>> crossovers;

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

    public void addOperator(GeneticOperator<G> operator) {
        if (operator instanceof GeneticCrossoverOperator) {
            crossovers.add((GeneticCrossoverOperator<G>) operator);
        }
        else if (operator instanceof GeneticMutationOperator) {
            mutators.add((GeneticMutationOperator<G>) operator);
        }
        else {
            throw new IllegalArgumentException("Operator didn't match. This might be to unfitting genome, or not a recognized operator.");
        }
    }

    public void normalizeWeights() {
        if(mutators.size() > 0) {
            double sum = 0;
            for (GeneticOperator operator : mutators) {
                sum += operator.getWeight();
            }

            for (GeneticOperator operator : mutators) {
                operator.normalizeWeight(sum);
            }
        }

        if(crossovers.size() > 0) {
            double sum = 0;
            for (GeneticOperator operator : crossovers) {
                sum += operator.getWeight();
            }

            for (GeneticOperator operator : crossovers) {
                operator.normalizeWeight(sum);
            }
        }
    }

    public GeneticOperator<G> getOperator() {
        if (Math.random() < crossoverProbability) {
            return crossovers.get((int)(Math.random() * crossovers.size()));
        }
        return crossovers.get((int)(Math.random() * mutators.size()));
    }
}
