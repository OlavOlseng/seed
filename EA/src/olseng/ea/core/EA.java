package olseng.ea.core;

import olseng.ea.adultselection.AdultSelector;
import olseng.ea.fitness.FitnessEvaluator;
import olseng.ea.fitness.RankingModule;
import olseng.ea.genetics.DevelopmentalMethod;
import olseng.ea.genetics.Genotype;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;

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
    }
}
