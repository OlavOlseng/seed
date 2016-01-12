package olseng.ea.core;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.core.tasks.DevelopmentTask;
import olseng.ea.core.tasks.EvaluationTask;
import olseng.ea.core.tasks.OffspringCreationTask;
import olseng.ea.fitness.FitnessEvaluator;
import olseng.ea.fitness.RankingModule;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by olavo on 2016-01-11.
 */
public class EA<G extends Genotype, P extends Phenotype> {

    public OperatorPool<G> operatorPool;
    public DevelopmentalMethod<G, P> developmentalMethod;
    public AdultSelector adultSelector;
    public FitnessEvaluator fitnessEvaluator;
    public RankingModule rankingModule;

    public Population population;

    private int threadCount = 1;

    private ExecutorService threadPool;

    public int populationMaxSize = 100;
    public int populationElitism = 10;
    public int populationOverpopulation = 0;


    public EA() {
    }

    public void initialize(Population population) {
        this.population = population;
        initialize();
    }

    public void initialize() {
        if (population == null) {
            throw new NullPointerException("Population is null.");
        }
        operatorPool.normalizeWeights();
        adultSelector.setPopulation(population);

        if(threadCount < 2) {
            threadPool = Executors.newSingleThreadExecutor();
        }
        else {
            threadPool = Executors.newFixedThreadPool(threadCount);
        }
    }

    public void step() {

        //generate offspring
        Future<List<Genotype>> task1 = threadPool.submit(new OffspringCreationTask(this, populationMaxSize - populationElitism + populationOverpopulation));
        List<Genotype> newGenes = null;
        try {
            newGenes = task1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //development
        Future<List<Phenotype>> task2 = threadPool.submit(new DevelopmentTask(this, 0, newGenes.size(), newGenes));
        List<Phenotype> newIndividuals = null;
        try {
            newIndividuals = task2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //evaluation
        Future<?> task3 = threadPool.submit(new EvaluationTask(this, 0, newIndividuals.size(), newIndividuals));
        try {
            task3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //##################END OF PARALLEL OPERATIONS!######################
        //merge new individuals into population.
        population.cullPopulation(populationElitism);
        population.merge(newIndividuals);

        //ranking and culling
        rankingModule.rankPopulation(population);
        population.sort();
        population.cullPopulation(populationMaxSize);
    }

    public void setThreadCount(int threadCount) {
        if (threadCount < 1) {
            throw new InvalidParameterException("Thread count can must be greater than one.");
        }
        this.threadCount = threadCount;

    }

}
