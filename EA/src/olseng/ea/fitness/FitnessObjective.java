package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

/**
 * Created by Olav on 11.01.2016.
 */
public interface FitnessObjective<P extends Phenotype> {
    float evaluate(P phenotype);
}
