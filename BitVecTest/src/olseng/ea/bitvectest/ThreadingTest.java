package olseng.ea.bitvectest;

import olseng.ea.EAFactory;
import olseng.ea.adultselection.TournamentSelector;
import olseng.ea.core.EA;
import olseng.ea.core.Population;
import olseng.ea.fitness.SummedFitnessComparator;
import olseng.ea.fitness.ranking.FastNonDominatedSort;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olav on 22.01.2016.
 */
public class ThreadingTest {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press any key to start...");
        try {
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Running...");
        runTest(1, 5);
        System.gc();
        runTest(2, 5);
         System.gc();
         runTest(4, 5);
        System.gc();
        runTest(8, 5);
        System.gc();
        runTest(16, 5);
        System.gc();
        runTest(32, 5);
        System.gc();
        runTest(64, 5);
        /**
        System.gc();
        runTest(128, 5);
         System.gc();
        runTest(250, 5);
        System.gc();
        runTest(500, 5);
         **/
    }

    private static void runTest(int threadCount, int iterations) {
        long startTime = System.currentTimeMillis();

        for (int j = 0; j < iterations; j++) {

            OperatorPool<BinaryGenome> operatorPool = new OperatorPool<>();
            operatorPool.addOperator(new BinaryGenomeMutator(1));
            operatorPool.addOperator(new BinaryGenomeSinglePointCrossover(1));
            operatorPool.setCrossoverProbability(0.5);

            EAFactory<BinaryGenome, IntVec> factory = new EAFactory<>();
            factory.addFitnessObjective(new LeadingOnesObjective());
            factory.addFitnessObjective(new TrailingZeroesObjective());
            factory.developmentalMethod = new BitToIntVec();
            factory.operatorPool = operatorPool;
            factory.adultSelector = new TournamentSelector(2, 0.3);
            factory.rankingModule = new FastNonDominatedSort();
            factory.sortingModule = new SummedFitnessComparator();

            EA<BinaryGenome, IntVec> ea = factory.build();

            List<Phenotype> initialPhenotypes = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                BinaryGenome g = new BinaryGenome(20, 1);
                g.randomize();
                Phenotype p = ea.developmentalMethod.develop(g);
                initialPhenotypes.add(p);
            }

            ea.setThreadCount(threadCount);
            ea.populationMaxSize = 100;

            Population population = new Population(100);
            population.setPopulation(initialPhenotypes);

            ea.initialize(population);

            for (int i = 0; i < 1000; i++) {
                ea.step();
            }

            ea.terminateThreads();
            //System.out.println(ea.population.getIndividual(0));
            //System.out.println(ea.population.getIndividual(ea.population.getPopulationSize() - 1) + "\n");
        }
        System.out.println("Average time elapsed running the test " + iterations + " times, on " + threadCount + " threads: " + ((System.currentTimeMillis() - startTime)) / iterations + " ms");
    }
}
