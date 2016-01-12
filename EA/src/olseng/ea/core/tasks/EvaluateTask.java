package olseng.ea.core.tasks;

import olseng.ea.core.EA;

/**
 * Created by Olav on 12.01.2016.
 */
public class EvaluateTask extends BaseTask<Void> {

    private final int start;
    private final int end;

    public EvaluateTask(EA ea, int start, int end) {
        super(ea);
        this.start = start;
        this.end = end;
    }

    @Override
    public Void call() throws Exception {
        for (int i = start; i < end; i++) {
            ea.fitnessEvaluator.evaluate(ea.population.getIndividual(i));
        }
        return null;
    }
}
