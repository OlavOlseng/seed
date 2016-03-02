package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

/**
 * Evaluates all functions serially, using one thread. Does not re-evaluate phenotypes that have already been reviewed. That means this evaluator should not be used for simulations, but for mathematical evaluations.
 * Created by Olav on 11.01.2016.
 */
public class StandardFitnessEvaluator extends FitnessEvaluator {

    @Override
    public void evaluate(Phenotype phenotype) {
        if (phenotype.isEvaluated) {
            return;
        }
        phenotype.initFitnessValues(objectiveEvaluators.size());

        for (int i = 0; i < objectiveEvaluators.size(); i++) {
            phenotype.setFitnessValue(i, objectiveEvaluators.get(i).evaluate(phenotype));
        }
        phenotype.isEvaluated = true;
    }
}
