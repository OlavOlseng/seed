package olseng.ea.core.tasks;

import olseng.ea.core.EA;
import olseng.ea.genetics.GeneticCrossoverOperator;
import olseng.ea.genetics.GeneticMutationOperator;
import olseng.ea.genetics.GeneticOperator;
import olseng.ea.genetics.Genotype;

import java.util.ArrayList;
import java.util.List;

/**
 * This task will perform adult selection on the provided population, then do a mutation. The result will be a specified amount of genotypes.
 * Created by Olav on 12.01.2016.
 */
public class OffspringCreationTask extends BaseTask<List<Genotype>> {

    private final int toProcuce;

    public OffspringCreationTask(EA ea, int toProduce) {
        super(ea);
        this.toProcuce = toProduce;
    }

    @Override
    public List<Genotype> call() throws Exception {
        List<Genotype> genotypes = new ArrayList<>(toProcuce);
        int produced = 0;

        while(produced < toProcuce) {
            GeneticOperator operator = ea.operatorPool.getOperator();

            if(operator instanceof GeneticCrossoverOperator) {
                Genotype parent1 = ea.adultSelector.getIndividual().getGenotype();
                Genotype parent2 = ea.adultSelector.getIndividual().getGenotype();
                Genotype child = ((GeneticCrossoverOperator) operator).crossover(parent1, parent2);
                genotypes.add(child);
            }
            else if(operator instanceof GeneticMutationOperator){
                Genotype parent = ea.adultSelector.getIndividual().getGenotype();
                Genotype child = ((GeneticMutationOperator) operator).mutate(parent);
                genotypes.add(child);
            }
            produced++;
        }

        return genotypes;
    }
}
