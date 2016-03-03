package olseng.ea.genetics;

import java.security.InvalidParameterException;

/**
 * Created by Olav on 12.01.2016.
 */
public abstract class GeneticOperator<G extends Genotype> {

    private double weight = 1;
    private double normalizedWeight = 0;

    public GeneticOperator(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new InvalidParameterException();
        }
        this.weight = weight;
    }

    public void normalizeWeight(double divisor) {
        this.normalizedWeight = weight/divisor;
    }

    public boolean isApplicable(G genotype) {
        return true;
    }

    public double getNormalizedWeight() {
        return normalizedWeight;
    }

}
