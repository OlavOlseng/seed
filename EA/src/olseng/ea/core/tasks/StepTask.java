package olseng.ea.core.tasks;

import olseng.ea.core.EA;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.Phenotype;

import java.util.List;

/**
 * Created by Olav on 22.01.2016.
 */
public class StepTask extends BaseTask<List<Phenotype>> {

    private final int individualsToCreate;
    public final int taskID;

    public StepTask(EA ea, int individualsToCreate, int taskID) {
        super(ea);
        this.individualsToCreate = individualsToCreate;
        this.taskID = taskID;
    }

    @Override
    public List<Phenotype> call() throws Exception {

        //######This chunk of code is paralellizable. This implementation runs on one thread!##########################
        //System.out.println(taskID + ": Creating offspring.");
        //generate offspring
        List<Genotype> newGenes = new OffspringCreationTask(this.ea, individualsToCreate).call();

        //System.out.println(taskID + ": Developing offspring.");
        //development
        List<Phenotype> newIndividuals = new DevelopmentTask(this.ea, 0, newGenes.size(), newGenes).call();

        //System.out.println(taskID + ": Evaluating offspring.");
        //Evaluation
        new EvaluationTask(this.ea, 0, newIndividuals.size(), newIndividuals).call();

        //System.out.println("Task " + taskID + " finished!");
        return newIndividuals;
    }
}
