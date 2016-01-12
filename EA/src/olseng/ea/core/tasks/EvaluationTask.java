package olseng.ea.core.tasks;

import olseng.ea.core.EA;
import olseng.ea.genetics.Phenotype;

import java.util.List;

/**
 * Created by Olav on 12.01.2016.
 */
public class EvaluationTask extends BaseTask<Void> {

    private final int start;
    private final int end;
    private final List<Phenotype> phenotypes;

    public EvaluationTask(EA ea, int start, int end, List<Phenotype> phenotypes) {
        super(ea);
        this.start = start;
        this.end = end;
        this.phenotypes = phenotypes;
    }

    @Override
    public Void call() throws Exception {
        for (int i = start; i < end; i++) {
            ea.fitnessEvaluator.evaluate(phenotypes.get(i));
        }
        return null;
    }
}
