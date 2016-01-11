package olseng.ea.fitness;

import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 11.01.2016.
 */
public abstract class FitnessEvaluator {

    protected List<FitnessObjective> objectiveEvaluators;

    public FitnessEvaluator() {
        this.objectiveEvaluators = new ArrayList<>();
    }

    public void setObjectives(List<FitnessObjective> objectives) {
        this.objectiveEvaluators = objectives;
    }

    public abstract void evaluate(Phenotype phenotype);

}