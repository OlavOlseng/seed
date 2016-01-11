package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

/**
 * Evaluates all functions serially, using one thread.
 * Created by Olav on 11.01.2016.
 */
public class StandardFitnessEvaluator extends FitnessEvaluator {

    @Override
    public void evaluate(Phenotype phenotype) {
        phenotype.initFitnessValues(objectiveEvaluators.size());

        for (int i = 0; i < objectiveEvaluators.size(); i++) {
            phenotype.setFitnessValue(i, objectiveEvaluators.get(i).evaluate(phenotype));
        }
    }
}
