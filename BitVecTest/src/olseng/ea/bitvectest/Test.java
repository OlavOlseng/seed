package olseng.ea.bitvectest;

import olseng.ea.EAFactory;
import olseng.ea.adultselection.RankTournamentSelector;
import olseng.ea.core.EA;
import olseng.ea.core.Population;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 22.01.2016.
 */
public class Test {

    public static void main(String[] args) {

        OperatorPool<BinaryGenome> operatorPool = new OperatorPool<>();
        operatorPool.addOperator(new BinaryGenomeMutator(1));
        operatorPool.addOperator(new BinaryGenomeSinglePointCrossover(1));
        operatorPool.setCrossoverProbability(0.5);

        EAFactory<BinaryGenome, IntVec> factory = new EAFactory<>();
        factory.addFitnessObjective(new LeadingOnesObjective());
        factory.developmentalMethod = new BitToIntVec();
        factory.operatorPool = operatorPool;
        factory.adultSelector = new RankTournamentSelector(10, 0.2);

        EA<BinaryGenome, IntVec> ea = factory.build();

        List<Phenotype> initialPhenotypes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            BinaryGenome g = new BinaryGenome(20, 1);
            g.randomize();
            Phenotype p = ea.developmentalMethod.develop(g);
            initialPhenotypes.add(p);

        }
        Population population = new Population(100);
        population.setPopulation(initialPhenotypes);

        ea.initialize(population);

        ea.step();
        ea.terminateThreads();
        System.out.println(ea.population.getIndividual(0));
        System.out.println(ea.population.getIndividual(99));
    }

}
