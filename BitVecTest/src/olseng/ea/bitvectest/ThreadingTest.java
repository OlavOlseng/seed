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
public class ThreadingTest {

    public static void main(String[] args) {
        runTest(1);
        runTest(2);
        runTest(4);
        runTest(8);
        runTest(16);
        runTest(32);
        runTest(64);
        runTest(128);
        runTest(256);
        runTest(512);
    }

    private static void runTest(int threadCount) {
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
        for (int i = 0; i < 1000; i++) {
            BinaryGenome g = new BinaryGenome(20, 1);
            g.randomize();
            Phenotype p = ea.developmentalMethod.develop(g);
            initialPhenotypes.add(p);

        }

        ea.setThreadCount(threadCount);
        ea.populationMaxSize = 1000;

        Population population = new Population(1000);
        population.setPopulation(initialPhenotypes);

        ea.initialize(population);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ea.step();
        }
        System.out.println("Time elapsed on " + threadCount + " threads: " + (System.currentTimeMillis() - startTime) + "ms");

        ea.terminateThreads();
        //System.out.println(ea.population.getIndividual(0));
        //System.out.println(ea.population.getIndividual(ea.population.getPopulationSize() - 1));
    }
}
