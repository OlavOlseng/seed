package olseng.ea.core;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.fitness.FitnessEvaluator;
import olseng.ea.fitness.RankingModule;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

import java.security.InvalidParameterException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by olavo on 2016-01-11.
 */
public class EA<G extends Genotype, P extends Phenotype> {

    public OperatorPool<G> operatorPool;
    public DevelopmentalMethod<G, P> developmentalMethod;
    public AdultSelector adultSelector;
    public FitnessEvaluator fitnessEvaluator;
    public RankingModule rankingModule;

    public Population<P> population;

    private int threadCount = 1;

    private ExecutorService threadPool;
    public EA() {
    }

    public void initialize(Population<P> population) {
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

        //development

        //evaluation
        
        //convergence check
    }

    public void setThreadCount(int threadCount) {
        if (threadCount < 1) {
            throw new InvalidParameterException("Thread count can only be set to greater than one.");
        }
        this.threadCount = threadCount;

    }

}
