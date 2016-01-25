package olseng.ea.core;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.core.tasks.DevelopmentTask;
import olseng.ea.core.tasks.EvaluationTask;
import olseng.ea.core.tasks.OffspringCreationTask;
import olseng.ea.core.tasks.StepTask;
import olseng.ea.fitness.FitnessEvaluator;
import olseng.ea.fitness.RankComparator;
import olseng.ea.fitness.RankingModule;
import olseng.ea.fitness.SingleFitnessComparator;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Comparator;
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
    private int taskCounter = 1;

    private ExecutorService threadPool;

    public int populationMaxSize = 10000;
    public int populationElitism = 10;
    public int populationOverpopulation = 0;

    public boolean rankingMode = false;
    public Comparator<Phenotype> sortingModule;

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

        //evaluation
        for (int i = 0; i < population.getPopulationSize(); i++) {
            fitnessEvaluator.evaluate(population.getIndividual(i));
        }
        if (rankingMode) {
            rankingModule.rankPopulation(population);
        }
        population.sort(sortingModule);
        population.cullPopulation(populationMaxSize);
    }

    public void step() {
        //Send off tasks to run in background threads
        int toCreatePerThread = (populationMaxSize - populationElitism + populationOverpopulation) / threadCount;
        int remainder = (populationMaxSize - populationElitism) % toCreatePerThread;
        List<Future<List<Phenotype>>> taskList = new ArrayList<>(threadCount);
        if (remainder == 0) {
            for (int i = 0; i < threadCount; i++) {
                Future<List<Phenotype>> task = threadPool.submit(new StepTask(this, toCreatePerThread, taskCounter++));
                taskList.add(task);
            }
        }
        else {
            for (int i = 0; i < threadCount - 1; i++) {
                Future<List<Phenotype>> task = threadPool.submit(new StepTask(this, toCreatePerThread, taskCounter++));
                taskList.add(task);
            }
            Future<List<Phenotype>> task = threadPool.submit(new StepTask(this, toCreatePerThread + remainder, taskCounter++));
            taskList.add(task);
        }
        //Collect task results from threads
        List<Phenotype> newIndividuals = new ArrayList<>();
        for (Future<List<Phenotype>> task : taskList) {
            try {
                newIndividuals.addAll(task.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //This code is not needed with the NSGA-II algorithm, it happens naturally.
        if (!rankingMode) {
            population.cullPopulation(populationElitism);
        }

        //merge new individuals into population.
        population.merge(newIndividuals);

        //ranking and culling
        if (rankingMode) {
            rankingModule.rankPopulation(population);
        }
        //These two steps can be concatenated in the case that a custom sort is implemented.
        population.sort(sortingModule);
        population.cullPopulation(populationMaxSize);

    }

    public void setThreadCount(int threadCount) {
        if (threadCount < 1) {
            throw new InvalidParameterException("Thread count can must be greater than one.");
        }
        this.threadCount = threadCount;
    }

    public void terminateThreads() {
        threadPool.shutdown();
    }

}
